package com.matsjonas.rps.api.config;

import com.matsjonas.rps.service.GameService;
import com.matsjonas.rps.service.GameServiceImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

/**
 * Jersey application binder.
 * <p>
 * Perform bindings of services to enable injection.
 */
public class RPSJerseyApplicationBinder extends AbstractBinder {

    /**
     * Perform application specific configuration.
     */
    @Override
    protected void configure() {
        bind(GameServiceImpl.class).to(GameService.class).in(Singleton.class);
    }

}
