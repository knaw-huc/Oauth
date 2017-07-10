
package com.example.oauthtest;

import com.github.scribejava.core.model.Response;
import io.dropwizard.Configuration;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.base.Optional;


import org.jvnet.hk2.internal.SystemDescriptor;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.UUID;

public class ExampleOAuthAuthenticator implements Authenticator<String, User> {

    @Override
    public Optional<User> authenticate(String credentials) throws AuthenticationException {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer("bzY4pW3bdU3UbcDmsotJE5S54", "9Dp2IfbDPpS8mMhB8CA9eBgOQP1fGTLguE0oAzrY98m02kBkmp");
        RequestToken requestToken = null;

        try {
            requestToken = twitter.getOAuthRequestToken();
            String authURL = requestToken.getAuthorizationURL();
            System.out.println(authURL);
        } catch (TwitterException e){

        }


        return Optional.of(new User("testuser"));
    }
}
