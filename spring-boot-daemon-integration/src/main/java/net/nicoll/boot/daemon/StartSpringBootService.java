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

/**
 * Start a Spring Boot application as a service.
 *
 * @author Stephane Nicoll
 */
@SuppressWarnings("hideutilityclassconstructor")
public class StartSpringBootService {

    /**
     * Application Main method.
     *
     * @param args command line arguments to pass to Spring
     *
     * @throws Exception if there is an error starting the server
     */
    public static void main(final String[] args) throws Exception {
        new SpringBootService().start(args);
    }

}
