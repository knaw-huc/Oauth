package com.example.oauthtest;

import io.dropwizard.Application;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import com.example.oauthtest.resources.AuthAppResource;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.example.oauthtest.ExampleOAuthAuthenticator;
import com.example.oauthtest.User;
import com.example.oauthtest.AuthAppConfiguration;





public class AuthApp extends Application<AuthAppConfiguration> {
    @Override
    public String getName() {
        return "auth-app";
    }
    
    @Override
    public void initialize(Bootstrap<AuthAppConfiguration> b) {
		b.setConfigurationSourceProvider(
				new SubstitutingSourceProvider(
						b.getConfigurationSourceProvider(),
						new EnvironmentVariableSubstitutor())
		);
    }

    @Override
    public void run(AuthAppConfiguration configuration,
		    Environment environment) {

		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

		final AuthAppResource resource = new AuthAppResource(
								   				configuration.getTemplate(),
												configuration.getDefaultName(),
												configuration.getClientId(),
												configuration.getClientSecret()
								   			);
								   environment.jersey().register(resource);

		environment.jersey().register(new AuthDynamicFeature(
				new OAuthCredentialAuthFilter.Builder<User>()
						.setAuthenticator(new ExampleOAuthAuthenticator())
						.setPrefix("Bearer")
						.buildAuthFilter()));
    }

    public static void main(String[] args) throws Exception {
		new AuthApp().run(args);
    }
}
