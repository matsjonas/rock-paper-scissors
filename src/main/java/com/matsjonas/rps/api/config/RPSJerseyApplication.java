package com.matsjonas.rps.api.config;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Jersey application configuration class.
 * <p>
 * Contains Jersey specific configuration.
 */
public class RPSJerseyApplication extends ResourceConfig {

    /**
     * Default constructor.
     */
    public RPSJerseyApplication() {
        register(new RPSJerseyApplicationBinder());
    }

}
