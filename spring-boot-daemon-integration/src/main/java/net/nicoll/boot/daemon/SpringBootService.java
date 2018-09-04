/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.nicoll.boot.daemon;

import java.io.IOException;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;

import org.springframework.boot.SpringApplication;
import org.springframework.util.ClassUtils;

/**
 * Basic daemon implementation for a Spring Boot app.
 *
 * @author Stephane Nicoll
 */
class SpringBootService {

    /**
     * Start the service.
     *
     * @param args Command line arguments as an array, where the first item is the fully qualified Spring Boot
     *             application class
     */
    void start(final String[] args) {
        if (args.length == 0) {
            throw new IllegalStateException(
                    "Spring Boot application class must be provided.");
        }
        Class<?> springBootApp = ClassUtils.resolveClassName(args[0],
                SpringBootService.class.getClassLoader());
        System.out.println(
                "Starting Spring Boot application [" + springBootApp.getName()
                + "]");
        SpringApplication.run(springBootApp);
    }

    /**
     * Stop the service.
     *
     * @param args Command line arguments as an array, where the first item is the JMX port to use to connect to the
     *             running service
     * @throws IOException if the port can't be opened or the service can't be connected with
     */
    void stop(final String[] args) throws IOException {
        System.out.println("Stopping Spring Boot application...");
        int jmxPort = Integer.parseInt(args[0]);
        String jmxName = SpringApplicationAdminClient.DEFAULT_OBJECT_NAME;
        try (JMXConnector connector = SpringApplicationAdminClient.connect(
                jmxPort)) {
            MBeanServerConnection connection
                    = connector.getMBeanServerConnection();
            new SpringApplicationAdminClient(connection, jmxName).stop();
        } catch (InstanceNotFoundException ex) {
            throw new IllegalStateException(
                    "Spring application lifecycle JMX bean not "
                    + "found, could not stop application "
                    + "gracefully", ex);
        }
    }

}
