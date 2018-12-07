package com.alexjamesmalcolm.secrethitler;

import java.util.ArrayList;
import java.util.Collection;

public class Game {
    private Collection<Player> players = new ArrayList<>();

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public int numberOfLiberals() {
        if (players.size() > 9) {
            return 6;
        }
        if (players.size() > 7) {
            return 5;
        }
        if (players.size() > 5) {
            return 4;
        }
        return 3;
    }

    public int numberOfFascists() {
        if (players.size() >= 9) {
            return 4;
        }
        if (players.size() >= 7) {
            return 3;
        }
        return 2;
    }
}
