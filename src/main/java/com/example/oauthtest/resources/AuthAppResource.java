package com.example.oauthtest.resources;

import com.example.helloworld.api.Saying;
import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;

import com.example.oauthtest.ExampleOAuthAuthenticator;
//import io.dropwizard.auth.Auth;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

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
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
	    final String value = String.format(template, name.or(defaultName));
	    //System.out.println("testing!");
	    return new Saying(counter.incrementAndGet(), value);
    }
}
