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

import java.util.UUID;

public class ExampleOAuthAuthenticator implements Authenticator<String, User> {

  @Override
  public Optional<User> authenticate(String credentials) throws AuthenticationException {
    System.out.println("Authentication token found! Credentials: " + credentials);

    return Optional.of(new User(credentials));
  }
}
