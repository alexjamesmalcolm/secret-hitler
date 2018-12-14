package com.alexjamesmalcolm.secrethitler.policies;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public abstract class Policy {

    @Id
    @GeneratedValue
    private long id;

    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Policy policy = (Policy) o;
        return id == policy.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
