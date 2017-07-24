

package com.example.oauthtest;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class AuthAppConfiguration extends Configuration {
        @NotEmpty
	private String template;

        @NotEmpty
	private String defaultName = "Stranger";

	@NotEmpty
	private String clientId;

	@JsonProperty
	public String getClientId() {
		return clientId;
	}

	@NotEmpty
	private String clientSecret;

	@JsonProperty
	public String getClientSecret() {
		return clientSecret;
	}

        @JsonProperty
	public String getTemplate() {
	    return template;
	}

        @JsonProperty
	public void setTemplate(String template) {
	    this.template = template;
	}

        @JsonProperty
	public String getDefaultName() {
	    return defaultName;
	}

        @JsonProperty
	public void setDefaultName(String name) {
	    this.defaultName = name;
	}

}

