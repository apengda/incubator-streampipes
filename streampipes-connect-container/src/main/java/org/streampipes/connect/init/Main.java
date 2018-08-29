/*
 * Copyright 2018 FZI Forschungszentrum Informatik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.streampipes.connect.init;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.streampipes.connect.rest.master.*;
import org.streampipes.connect.rest.worker.WelcomePageWorker;
import org.streampipes.connect.rest.worker.WorkerResource;
import org.streampipes.model.connect.guess.GuessSchema;
import org.streampipes.rest.shared.serializer.GsonClientModelProvider;
import org.streampipes.rest.shared.serializer.GsonWithIdProvider;
import org.streampipes.rest.shared.serializer.GsonWithoutIdProvider;
import org.streampipes.rest.shared.serializer.JsonLdProvider;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {

        // TODO Two different execution modes

        String executionMode = Config.getEnv(Config.EXECUTION_MODE);

        ResourceConfig config = null;
        URI baseUri = null;

        switch (executionMode) {
            case Config.MASTER:
                LOG.info("Started StreamPipes Connect Resource in MASTER mode");
                config = new ResourceConfig(getMasterApiClasses());
                baseUri = UriBuilder
                    .fromUri(Config.getMasterBaseUrl())
                    .build();

                break;
            case Config.WORKER:
                LOG.info("Started StreamPipes Connect Resource in WORKER mode");
                config = new ResourceConfig(getWorkerApiClasses());
                baseUri = UriBuilder
                    .fromUri(Config.getWorkerBaseUrl())
                    .build();

                break;
            default:
                LOG.error("Environment Variable EXECUTION_MODE is not set correctly. Must be " + Config.MASTER + " or " + Config.WORKER);
                System.exit(0);
        }


        Server server = JettyHttpContainerFactory.createServer(baseUri, config);

    }

    private static Set<Class<?>> getMasterApiClasses() {
        Set<Class<?>> allClasses = new HashSet<>();

        allClasses.add(WelcomePageMaster.class);
        allClasses.add(AdapterResource.class);
        allClasses.add(DescriptionResource.class);
        allClasses.add(SourcesResource.class);
        allClasses.add(GuessResource.class);

        allClasses.addAll(getApiClasses());

        return allClasses;
    }

    private static Set<Class<?>> getWorkerApiClasses() {
        Set<Class<?>> allClasses = new HashSet<>();

        allClasses.add(WelcomePageWorker.class);
        allClasses.add(WorkerResource.class);

        allClasses.addAll(getApiClasses());

        return allClasses;
    }

    private static Set<Class<?>> getApiClasses() {
        Set<Class<?>> allClasses = new HashSet<>();

        // Serializers
        allClasses.add(GsonWithIdProvider.class);
        allClasses.add(GsonWithoutIdProvider.class);
        allClasses.add(GsonClientModelProvider.class);
        allClasses.add(JsonLdProvider.class);

        return allClasses;
    }
}