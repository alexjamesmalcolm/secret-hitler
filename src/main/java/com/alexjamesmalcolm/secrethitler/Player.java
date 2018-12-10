package com.alexjamesmalcolm.secrethitler;

public class Player {
    private String role;

    public Player(String name) {

    }

    public void setAsLiberal() {
        role = "liberal";
    }

    public void setAsFascist() {
        role = "fascist";
    }

    public void setAsHitler() {
        role = "hitler";
    }

    public String getSecretRole() {
        return role;
    }

    public String getPartyMembership() {
        return "liberal";
    }
}
