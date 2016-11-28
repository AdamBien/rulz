/*
 * Copyright 2016 Adam Bien.
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
package com.airhacks.rulz.jaxrsclient;

/*
 * #%L
 * jaxrsclient
 * %%
 * Copyright (C) 2015 - 2016 Adam Bien
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

import static com.airhacks.rulz.jaxrsclient.HttpMatchers.clientError;
import static com.airhacks.rulz.jaxrsclient.HttpMatchers.created;
import static com.airhacks.rulz.jaxrsclient.HttpMatchers.informational;
import static com.airhacks.rulz.jaxrsclient.HttpMatchers.noContent;
import static com.airhacks.rulz.jaxrsclient.HttpMatchers.other;
import static com.airhacks.rulz.jaxrsclient.HttpMatchers.redirection;
import static com.airhacks.rulz.jaxrsclient.HttpMatchers.serverError;
import static com.airhacks.rulz.jaxrsclient.HttpMatchers.successful;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author airhacks.com
 */
public class HttpMatchersIT {

    private Client client;

    /**
     * URI refers to {@link
     * <a href="https://github.com/AdamBien/statustest">statustest</a>}.
     */
    private static final String STATUSTEST_URI = "http://localhost:8080/statustest/resources/statuses";
    private WebTarget tut;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void init() {
        this.client = ClientBuilder.newClient();
        this.tut = this.client.target(STATUSTEST_URI);
    }

    @Test
    public void successfulMessage() {
        expected.expect(AssertionError.class);
        expected.expectMessage(containsString("Internal Server Error 500"));
        Response response = this.tut.request().
                header("status", 500).
                get();
        assertThat(response, is(successful()));
    }

    @Test
    public void contentMessage() {
        expected.expect(AssertionError.class);
        expected.expectMessage(containsString("Internal Server Error 500"));
        expected.expectMessage(containsString("is no content (204)"));
        Response response = this.tut.request().
                header("status", 500).
                get();
        assertThat(response, is(noContent()));
    }

    @Test
    public void serverErrorMessage() {
        expected.expect(AssertionError.class);
        expected.expectMessage(containsString("Internal server error(500)"));
        expected.expectMessage(containsString("OK 200 returned"));
        Response response = this.tut.request().
                header("status", 200).
                get();
        assertThat(response, is(serverError()));
    }

    @Test
    public void createdMessage() {
        expected.expect(AssertionError.class);
        expected.expectMessage(containsString("Internal Server Error 500 returned"));
        expected.expectMessage(containsString("created status (201) with location header"));
        Response response = this.tut.request().
                header("status", 500).
                get();
        assertThat(response, is(created()));
    }

    @Test
    public void clientErrorMessage() {
        expected.expect(AssertionError.class);
        expected.expectMessage(containsString("Internal Server Error 500 returned"));
        expected.expectMessage(containsString("4xx (client error) family of responses"));
        Response response = this.tut.request().
                header("status", 500).
                get();
        assertThat(response, is(clientError()));
    }

    @Test
    public void redirectionMessage() {
        expected.expect(AssertionError.class);
        expected.expectMessage(containsString("Internal Server Error 500 returned"));
        expected.expectMessage(containsString("is 3xx (redirection) family of responses"));
        Response response = this.tut.request().
                header("status", 500).
                get();
        assertThat(response, is(redirection()));
    }

    @Test
    public void informationalMessage() {
        expected.expect(AssertionError.class);
        expected.expectMessage(containsString("Internal Server Error 500 returned"));
        expected.expectMessage(containsString("1xx (informational) family of responses"));
        Response response = this.tut.request().
                header("status", 500).
                get();
        assertThat(response, is(informational()));
    }

    @Test
    public void otherMessage() {
        expected.expect(AssertionError.class);
        expected.expectMessage(containsString("Internal Server Error 500 returned"));
        expected.expectMessage(containsString("unrecognized family of response"));
        Response response = this.tut.request().
                header("status", 500).
                get();
        assertThat(response, is(other()));
    }

}
