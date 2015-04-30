package com.airhacks.rulz.jaxrsclient;

/*
 * #%L
 * em
 * %%
 * Copyright (C) 2015 Adam Bien
 * %%
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
 * #L%
 */
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 *
 * @author airhacks.com
 */
public class JAXRSClientProvider implements TestRule {

    private Client client;
    private WebTarget target;

    private JAXRSClientProvider(String uri) {
        this.client = ClientBuilder.newClient();
        this.target = this.client.target(uri);
    }

    public final static JAXRSClientProvider buildWithURI(String uri) {
        return new JAXRSClientProvider(uri);
    }

    public Client client() {
        return client;
    }

    public WebTarget target() {
        return target;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                base.evaluate();
                client.close();
            }

        };
    }
}
