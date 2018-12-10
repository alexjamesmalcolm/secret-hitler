package com.alexjamesmalcolm.secrethitler;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Game {

    private Collection<Player> players = new ArrayList<>();
    private boolean isStarted = false;

    public void addPlayer(Player player) {
        if (!isStarted) {
            this.players.add(player);
        }
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

    public void start() throws TooFewPlayersException {
        if (players.size() < 5) {
            throw new TooFewPlayersException();
        }
        isStarted = true;
        assignIdentities();
    }

    void assignIdentities() {
        try {
            List<Player> players = (List<Player>) this.players;
            Collections.shuffle(players);
            Player hitler = players.get(0);
            List<Player> liberals = players.subList(1, 1 + numberOfLiberals());
            List<Player> fascists = players.subList(1 + numberOfLiberals(), numberOfLiberals() + numberOfFascists());
            for (Player liberal : liberals) {
                liberal.setAsLiberal();
            }
            for (Player fascist : fascists) {
                fascist.setAsFascist();
            }
            hitler.setAsHitler();
        } catch (IdentityAlreadyAssigned e) {
            e.printStackTrace();
        }
    }

    public void getBoard() throws GameNotStartedException {
        throw new GameNotStartedException();
    }
}
