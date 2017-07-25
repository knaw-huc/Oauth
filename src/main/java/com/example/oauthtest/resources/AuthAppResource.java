package com.example.oauthtest.resources;

import com.example.helloworld.api.Saying;
import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;

import com.example.oauthtest.ExampleOAuthAuthenticator;
import org.glassfish.jersey.client.ClientProperties;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import sun.net.www.http.HttpClient;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;
import org.apache.commons.codec.binary.Base64;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonProperty;


@Path("/auth/example")
@Produces(MediaType.APPLICATION_JSON)
public class AuthAppResource {
    private final String template;
    private final String defaultName;
    private final String clientId;
    private final String clientSecret;
    private final AtomicLong counter;


    public AuthAppResource(String template, String defaultName, String clientId, String clientSecret) {
	    this.template = template;
	    this.defaultName = defaultName;
	    this.clientId = clientId;
	    this.clientSecret = clientSecret;
	    this.counter = new AtomicLong();
    }

    @GET
    @Path("/callback")
    @Timed
    public Response authenticate(@QueryParam("code") String code) {
        String auth_url = "https://authz.proxy.clariah.nl/oauth/token";


        //Bearer token for OAuth2 needs to be in base64 format
        //and formed by concatanating clientId with clientSecret
        String bearer_token = clientId + ":" + clientSecret;
        byte[] encodedBytes = Base64.encodeBase64(bearer_token.getBytes());
        String encoded_bearer_token = new String(encodedBytes);

        //Redirect URI needs to match one registered with the OAuth server
        String redirect_uri = "http://localhost:3000/auth/example/callback";

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(auth_url);
        MultivaluedHashMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("code", code);
        formData.add("redirect_uri", redirect_uri);
        formData.add("grant_type", "authorization_code");

        Response response = target.request()
                .property(ClientProperties.FOLLOW_REDIRECTS, true)
                .header(HttpHeaders.AUTHORIZATION, "Basic " +  encoded_bearer_token)
                .post(Entity.form(formData));

        URI uri = UriBuilder.fromUri("auth/example/callback/hello").build();
        Map<String, Object> jsonResponse = response.readEntity(Map.class);

        String auth_code = jsonResponse.get("access_token").toString();
        System.out.println("Auth code received: " + auth_code);

        String hello_uri = "http://localhost:3000/auth/example/callback/hello";

        WebTarget redirect_target = client.target(hello_uri);

        Response redirect_response = redirect_target.request()
                .property(ClientProperties.FOLLOW_REDIRECTS, true)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + auth_code)
                .get();

        return redirect_response;
    }

    //@PermitAll allows all users who are6 authenticated via OAuth to reach
    //the resource
    @PermitAll
    @GET
    @Path("/callback/hello")
    public Saying sayHello() {
        return new Saying(counter.incrementAndGet(), "hello");
    }
}
