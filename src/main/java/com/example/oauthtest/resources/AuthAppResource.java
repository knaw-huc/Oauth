package com.example.oauthtest.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.api.Saying;
import org.apache.commons.codec.binary.Base64;
import org.glassfish.jersey.client.ClientProperties;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Path("/redirect")
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
  @Timed
  public Response authenticate(@QueryParam("code") String code) {
    //String auth_url = "https://authz.proxy.clariah.nl/oauth/token";
    String auth_url = "http://localhost:8080/oauth2/token";

    //Bearer token for OAuth2 needs to be in base64 format
    //and formed by concatanating clientId with clientSecret
    String bearer_token = clientId + ":" + clientSecret;
    byte[] encodedBytes = Base64.encodeBase64(bearer_token.getBytes());
    String encoded_bearer_token = new String(encodedBytes);

    //Redirect URI needs to match one registered with the OAuth server
    String redirect_uri = "http://localhost:8084/redirect/";

    Client client = ClientBuilder.newClient();
    WebTarget target = client.target(auth_url);
    MultivaluedHashMap<String, String> formData = new MultivaluedHashMap<String, String>();
    formData.add("code", code);
    formData.add("redirect_uri", redirect_uri);
    formData.add("grant_type", "authorization_code");

    Response response = target.request()
      .property(ClientProperties.FOLLOW_REDIRECTS, true)
      .header(HttpHeaders.AUTHORIZATION, "Basic " + encoded_bearer_token)
      .post(Entity.form(formData));

    Map<String, Object> jsonResponse = response.readEntity(Map.class);

    String auth_code = jsonResponse.get("access_token").toString();
    System.out.println("Auth code received: " + auth_code);

    String hello_uri = "http://localhost:8084/redirect/hello";

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
  @Path("/hello")
  public Saying sayHello() {
    return new Saying(counter.incrementAndGet(), "hello");
  }
}
