package com.example.oauthtest.resources;

import com.example.helloworld.api.Saying;
import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;

import com.example.oauthtest.ExampleOAuthAuthenticator;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
//import io.dropwizard.auth.Auth;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicLong;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;

import twitter4j.conf.ConfigurationBuilder;


@Path("/auth-test")
@Produces(MediaType.APPLICATION_JSON)
public class AuthAppResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public AuthAppResource(String template, String defaultName) {
	    this.template = template;
	    this.defaultName = defaultName;
	    this.counter = new AtomicLong();
    }

    @PermitAll
    @GET
    @Timed
    public Saying sayHello(@QueryParam("auth-test#state") Optional<String> name) {
        System.out.println("hello!!");
        /*
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer("bzY4pW3bdU3UbcDmsotJE5S54", "9Dp2IfbDPpS8mMhB8CA9eBgOQP1fGTLguE0oAzrY98m02kBkmp");
        RequestToken requestToken = null;


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("bzY4pW3bdU3UbcDmsotJE5S54")
                .setOAuthConsumerSecret("9Dp2IfbDPpS8mMhB8CA9eBgOQP1fGTLguE0oAzrY98m02kBkmp")
                .setOAuthAccessToken("882309794111082496-ycUbJ1jRUKb6P3NGjdIdB0Z2q0jRSpP")
                .setOAuthAccessTokenSecret("ocIp1bNtUiUBBfQ4bjAXvNxB1OaEpSmcfn1WuGamerhJV");

        TwitterFactory factory = new TwitterFactory(cb.build());
        Twitter twitter = factory.getInstance();



        try {
            requestToken = twitter.getOAuthRequestToken();
            String authURL = requestToken.getAuthorizationURL();
            System.out.println(authURL);

            UriBuilder builder = UriBuilder
                    .fromUri(authURL);

            URI uri = builder.build();

            return Response.seeOther(uri).build();

        } catch (TwitterException e){

        }
        return Response.noContent().build();
        */

        return new Saying(counter.incrementAndGet(), "hello");
    }
}
