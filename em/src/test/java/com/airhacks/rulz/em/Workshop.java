package com.airhacks.rulz.em;

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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author airhacks.com
 */
@Entity
public class Workshop {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;

    public Workshop(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Workshop() {
    }

}
