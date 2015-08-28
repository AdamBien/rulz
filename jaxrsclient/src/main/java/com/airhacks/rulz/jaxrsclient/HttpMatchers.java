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
import org.hamcrest.Matcher;

/**
 *
 * @author airhacks.com
 */
public class HttpMatchers {

    public static Matcher<Response> successful() {
        return new CustomMatcher<Response>("is successful response") {

            @Override
            public boolean matches(Object o) {
                return (o instanceof Response)
                        && (((Response) o).getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
            }
        };
    }

    public static Matcher<Response> noContent() {
        return new CustomMatcher<Response>("no content") {

            @Override
            public boolean matches(Object o) {
                return (o instanceof Response)
                        && (((Response) o).getStatus() == Response.Status.NO_CONTENT.getStatusCode());
            }
        };
    }

    public static Matcher<Response> created() {
        return new CustomMatcher<Response>("201 created status with location header") {

            @Override
            public boolean matches(Object o) {
                if (!(o instanceof Response)) {
                    return false;
                }
                Response response = (Response) o;
                if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                    String header = response.getHeaderString("Location");
                    return (header != null);
                } else {
                    return false;
                }
            }
        };
    }
}
