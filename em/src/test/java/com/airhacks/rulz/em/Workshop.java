package com.airhacks.rulz.em;

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
