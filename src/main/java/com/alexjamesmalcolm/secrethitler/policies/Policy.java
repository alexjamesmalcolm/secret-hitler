package com.alexjamesmalcolm.secrethitler.policies;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Policy {

    @Id
    private long id;
}
