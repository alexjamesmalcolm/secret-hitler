package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.IdentityAlreadyAssigned;

public class Player {
    private String role;
    private String party;

    public Player(String name) {
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
}
