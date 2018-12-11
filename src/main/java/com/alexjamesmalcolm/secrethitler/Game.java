package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.*;

import java.util.*;

public class Game {

    private List<Player> players = new ArrayList<>();
    private boolean isStarted = false;
    private Player presidentialCandidate;
    private Player chancellorNominee;
    private Collection<Player> playersThatVotedYes = new ArrayList<>();
    private Collection<Player> playersThatVotedNo = new ArrayList<>();
    private Player president;

    public void addPlayer(Player player) throws GameFullOfPlayers {
        if (players.size() == 10) {
            throw new GameFullOfPlayers();
        }
        if (!isStarted) {
            if (presidentialCandidate == null) {
                presidentialCandidate = player;
            }
            players.add(player);
        }
    }

    public List<Player> getPlayers() {
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
            List<Player> players = new LinkedList<>(getPlayers());
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

    public Board getBoard() throws GameNotStartedException {
        if (isStarted) {
            return new Board();
        } else {
            throw new GameNotStartedException();
        }
    }

    public void nominateAsChancellor(Player player) throws InvalidNomination {
        if (presidentialCandidate.equals(player)) {
            throw new InvalidNomination();
        }
        chancellorNominee = player;
    }

    public void voteYes(Player voter) {
        playersThatVotedYes.add(voter);
        playersThatVotedNo.remove(voter);
        if (playersThatVotedNo.size() + playersThatVotedYes.size() == players.size()) {
            president = presidentialCandidate;
            int index = players.indexOf(presidentialCandidate);
            try {
                presidentialCandidate = players.get(index + 1);
            } catch (IndexOutOfBoundsException e) {
                presidentialCandidate = players.get(0);
            }
        }
    }

    public void voteNo(Player voter) {
        playersThatVotedNo.add(voter);
        playersThatVotedYes.remove(voter);
        if (playersThatVotedNo.size() + playersThatVotedYes.size() == players.size()) {
            president = presidentialCandidate;
            int index = players.indexOf(presidentialCandidate);
            try {
                presidentialCandidate = players.get(index + 1);
            } catch (IndexOutOfBoundsException e) {
                presidentialCandidate = players.get(0);
            }
        }
    }

    public Optional<Player> getPresident() {
        if (playersThatVotedYes.size() > playersThatVotedNo.size()) {
            if (playersThatVotedYes.size() + playersThatVotedNo.size() == players.size()) {
                return Optional.of(president);
            }
        }
        return Optional.empty();
    }

    public Optional<Player> getChancellor() {
        if (playersThatVotedYes.size() > playersThatVotedNo.size()) {
            if (playersThatVotedYes.size() + playersThatVotedNo.size() == players.size()) {
                return Optional.of(chancellorNominee);
            }
        }
        return Optional.empty();
    }

    public Player getPresidentialCandidate() {
        return presidentialCandidate;
    }
}
