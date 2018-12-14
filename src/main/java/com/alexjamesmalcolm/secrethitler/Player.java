package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.IdentityAlreadyAssigned;
import com.alexjamesmalcolm.secrethitler.policies.Policy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String role;
    private String party;
    private boolean termLimited;
    @OneToMany
    private List<Policy> hand;

    Player() {}

    public Player(String name) {
        this.name = name;
        role = "";
        party = "";
    }

    public void setAsLiberal() throws IdentityAlreadyAssigned {
        if (!role.isEmpty()) {
            throw new IdentityAlreadyAssigned();
        }
        role = "liberal";
        party = "liberal";
    }

    public void setAsFascist() throws IdentityAlreadyAssigned {
        if (!role.isEmpty()) {
            throw new IdentityAlreadyAssigned();
        }
        role = "fascist";
        party = "fascist";
    }

    public void setAsHitler() throws IdentityAlreadyAssigned {
        if (!role.isEmpty()) {
            throw new IdentityAlreadyAssigned();
        }
        role = "hitler";
        party = "fascist";
    }

    public String getSecretRole() {
        return role;
    }

    public String getPartyMembership() {
        return party;
    }

    @Override
    public String toString() {
        return "Player@" + name;
    }

    public void limitTerm() {
        termLimited = true;
    }

    public boolean isTermLimited() {
        return termLimited;
    }

	public void removeTermLimit() {
		termLimited = false;
	}

    public List<Policy> getPolicyHand() {
        return hand;
    }

    public void giveCards(List<Policy> policies) {
        hand = policies;
    }

    public void giveTwoCardsToChancellor(Policy firstPolicy, Policy secondPolicy) {

    }
}
