package com.alexjamesmalcolm.secrethitler;

import java.util.*;

import com.alexjamesmalcolm.secrethitler.exceptions.*;
import com.alexjamesmalcolm.secrethitler.policies.FascistPolicy;
import com.alexjamesmalcolm.secrethitler.policies.LiberalPolicy;
import com.alexjamesmalcolm.secrethitler.policies.Policy;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;

@Entity
public class Game {

    @Id
    private long id;

    @OneToMany(cascade = ALL)
    private List<Player> players = new ArrayList<>();
    private boolean isStarted = false;
    @OneToOne(cascade = ALL)
    private Player presidentialCandidate;
    @OneToOne
    private Player chancellorNominee;
    @OneToMany
    private Collection<Player> playersThatVotedYes = new ArrayList<>();
    @OneToMany
    private Collection<Player> playersThatVotedNo = new ArrayList<>();
    @OneToOne
    private Player president;
    private int failedElectionsUntilShutdown = 3;
    @OneToOne(cascade = ALL)
	private Board board;
    @OneToMany
    private List<Policy> pile;
    @OneToOne
    private Player chancellor;

    public Game() {
    }

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
        board = new Board(this);
        assignIdentities();
        pile = new ArrayList<>();
        pile.add(new FascistPolicy());
        pile.add(new FascistPolicy());
        pile.add(new FascistPolicy());
        pile.add(new FascistPolicy());
        pile.add(new FascistPolicy());
        pile.add(new FascistPolicy());
        pile.add(new FascistPolicy());
        pile.add(new FascistPolicy());
        pile.add(new FascistPolicy());
        pile.add(new FascistPolicy());
        pile.add(new FascistPolicy());
        pile.add(new LiberalPolicy());
        pile.add(new LiberalPolicy());
        pile.add(new LiberalPolicy());
        pile.add(new LiberalPolicy());
        pile.add(new LiberalPolicy());
        pile.add(new LiberalPolicy());
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
            return board;
        } else {
            throw new GameNotStartedException();
        }
    }

    public void nominateAsChancellor(Player player) throws InvalidNomination {
        if (presidentialCandidate.equals(player)) {
            throw new InvalidNomination();
        }
        if (player.isTermLimited()) {
            throw new InvalidNomination();
        }
        playersThatVotedYes.clear();
        playersThatVotedNo.clear();
        chancellorNominee = player;
    }

    public void voteYes(Player voter) throws GovernmentShutdown, OutstandingChancellorNomination {
        preVoteHelper();
        playersThatVotedYes.add(voter);
        playersThatVotedNo.remove(voter);
        postVoteHelper();
    }

    public void voteNo(Player voter) throws GovernmentShutdown, OutstandingChancellorNomination {
        preVoteHelper();
        playersThatVotedNo.add(voter);
        playersThatVotedYes.remove(voter);
        postVoteHelper();
    }

    private void preVoteHelper() throws OutstandingChancellorNomination {
        if (chancellorNominee == null) {
            throw new OutstandingChancellorNomination();
        }
    }

    private void postVoteHelper() throws GovernmentShutdown {
        if (playersThatVotedNo.size() + playersThatVotedYes.size() == players.size()) {
            if (playersThatVotedYes.size() > playersThatVotedNo.size()) {
                president = presidentialCandidate;
                chancellor = chancellorNominee;
                chancellorNominee = null;
                president.limitTerm();
                chancellor.limitTerm();
                president.giveCards(drawThreeCards());
            } else {
                failedElectionsUntilShutdown -= 1;
            }
            if (failedElectionsUntilShutdown == 0) {
                throw new GovernmentShutdown();
            }
            try {
                presidentialCandidate = players.get(players.indexOf(presidentialCandidate) + 1);
            } catch (IndexOutOfBoundsException e) {
                presidentialCandidate = players.get(0);
            }
        }
    }

    public Optional<Player> getPresident() {
        return Optional.ofNullable(president);
    }

    public Optional<Player> getChancellor() {
        return Optional.ofNullable(chancellor);
    }

    public Player getPresidentialCandidate() {
        return presidentialCandidate;
    }

    public int getFailedElectionsUntilShutdown() {
        return failedElectionsUntilShutdown;
    }

    public List<Policy> getDrawPile() {
        return pile;
    }

    public List<Policy> drawThreeCards() {
        List<Policy> drawnCards = new ArrayList<>();
        drawnCards.add(pile.get(0));
        drawnCards.add(pile.get(1));
        drawnCards.add(pile.get(2));
        pile.removeAll(drawnCards);
        return drawnCards;
    }

    public List<Policy> getDiscardedPile() {
        return null;
    }

    public long getId() {
        return id;
    }

    public Optional<Player> getChancellorNominee() {
        return Optional.empty();
    }
}
