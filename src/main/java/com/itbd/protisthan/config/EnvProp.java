package com.itbd.protisthan.config;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

@Endpoint
@AnonymousAllowed
public class EnvProp {

    @Autowired
    private Environment env;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    public String[] getProp() {
        return this.env.getActiveProfiles();
    }

    public String getActiveProfile() {
        return this.activeProfile;
    }
}
