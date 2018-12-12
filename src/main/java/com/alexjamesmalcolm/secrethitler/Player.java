package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.IdentityAlreadyAssigned;

public class Player {
    private final String name;
    private String role;
    private String party;
    private boolean termLimited;

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
}
