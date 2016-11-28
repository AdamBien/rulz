package com.airhacks.rulz.jaxrsclient;

/*
 * #%L
 * jaxrsclient
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
import javax.ws.rs.core.Response;
import org.hamcrest.CustomMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 *
 * @author airhacks.com
 */
public class HttpMatchers {

    public static Matcher<Response> successful() {
        return new CustomMatcher<Response>("2xx family of successful responses") {

            @Override
            public boolean matches(Object o) {
                return (o instanceof Response)
                        && (((Response) o).getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                Response response = (Response) item;
                provideDescription(response, description);
            }

        };
    }
    
       public static Matcher<Response> clientError() {
           return new CustomMatcher<Response>("4xx (client error) family of responses") {

            @Override
            public boolean matches(Object o) {
                return (o instanceof Response)
                        && (((Response) o).getStatusInfo().getFamily() == Response.Status.Family.CLIENT_ERROR);
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                Response response = (Response) item;
                provideDescription(response, description);
            }

        };
    }

    public static Matcher<Response> noContent() {
        final int statusCode = Response.Status.NO_CONTENT.getStatusCode();
        return new CustomMatcher<Response>("no content (" + statusCode + ")") {

            @Override
            public boolean matches(Object o) {
                return (o instanceof Response)
                        && (((Response) o).getStatus() == statusCode);
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                Response response = (Response) item;
                provideDescription(response, description);
            }

        };
    }

    public static Matcher<Response> serverError() {
        final int statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        return new CustomMatcher<Response>("Internal server error(" + statusCode + ")") {

            @Override
            public boolean matches(Object o) {
                return (o instanceof Response)
                        && (((Response) o).getStatus() == statusCode);
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                Response response = (Response) item;
                provideDescription(response, description);
            }

        };
    }

    public static Matcher<Response> created() {
        final int statusCode = Response.Status.CREATED.getStatusCode();
        return new CustomMatcher<Response>("created status (" + statusCode + ") with location header") {

            @Override
            public boolean matches(Object o) {
                if (!(o instanceof Response)) {
                    return false;
                }
                Response response = (Response) o;
                if (response.getStatus() == statusCode) {
                    String header = response.getHeaderString("Location");
                    return (header != null);
                } else {
                    return false;
                }
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                Response response = (Response) item;
                provideDescription(response, description);
            }
        };
    }

    static void provideDescription(Response response, Description description) {
        description.appendText(response.getStatusInfo().getReasonPhrase() + " " + response.getStatus()).
                appendText(" returned");

    }
}
