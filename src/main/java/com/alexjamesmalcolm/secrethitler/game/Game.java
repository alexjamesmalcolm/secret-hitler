package com.alexjamesmalcolm.secrethitler.game;

import com.alexjamesmalcolm.secrethitler.Board;
import com.alexjamesmalcolm.secrethitler.throwable.events.GovernmentShutdown;
import com.alexjamesmalcolm.secrethitler.throwable.exceptions.*;
import com.alexjamesmalcolm.secrethitler.policies.FascistPolicy;
import com.alexjamesmalcolm.secrethitler.policies.LiberalPolicy;
import com.alexjamesmalcolm.secrethitler.policies.Policy;
import com.alexjamesmalcolm.secrethitler.throwable.state.ChancellorNominationState;
import com.alexjamesmalcolm.secrethitler.throwable.state.GameNotStartedState;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import static javax.persistence.CascadeType.ALL;

@Entity
public class Game {

    @Id
    private long id;

    @OneToMany(cascade = ALL, mappedBy = "game")
    private List<Player> players = new ArrayList<>();

    private boolean isStarted = false;

    @OneToOne(cascade = ALL)
    private Player presidentialCandidate;

    @OneToOne(cascade = ALL)
    private Player chancellorNominee;

    @OneToMany
    private Collection<Player> playersThatVotedYes = new ArrayList<>();

    @OneToMany
    private Collection<Player> playersThatVotedNo = new ArrayList<>();

    @OneToOne(cascade = ALL)
    private Player president;

    private int failedElectionsUntilShutdown = 3;

    @OneToOne(cascade = ALL)
	private Board board;

    @OneToMany(cascade = ALL)
    private List<Policy> pile;

    @OneToOne
    private Player chancellor;

    @OneToMany(cascade = ALL)
    private List<Policy> discardedPile = new ArrayList<>();

    public Game() {}

    public void addPlayer(Player player) throws GameFullOfPlayers {
        if (players.size() == 10) {
            throw new GameFullOfPlayers();
        }
        if (!isStarted) {
            if (presidentialCandidate == null) {
                presidentialCandidate = player;
            }
            players.add(player);
            player.setGame(this);
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
        // TODO Make the order of presidents random
        if (players.size() < 5) {
            throw new TooFewPlayersException();
        }
        isStarted = true;
        board = new Board(this);
        assignIdentities();
        pile = new LinkedList<>();
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
        shuffle(pile);
    }

    void assignIdentities() {
        try {
            List<Player> players = new LinkedList<>(getPlayers());
            shuffle(players);
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

    public Board getBoard() throws GameNotStartedState {
        if (isStarted) {
            return board;
        } else {
            throw new GameNotStartedState();
        }
    }

    public void nominateAsChancellor(Player player) throws InvalidNomination, PlayerNotInGame {
        if (!players.contains(player)) {
            throw new PlayerNotInGame();
        }
        if (presidentialCandidate.equals(player)) {
            throw new InvalidNomination("Player(" + player.toString() + ") is already the Presidential Candidate(" + presidentialCandidate.toString() + ")");
        }
        if (player.isTermLimited()) {
            throw new InvalidNomination("Player(" + player.toString() + ") is term limited.");
        }
        playersThatVotedYes.clear();
        playersThatVotedNo.clear();
        chancellorNominee = player;
    }

    public void voteYes(Player voter) throws GovernmentShutdown, ChancellorNominationState, PlayerNotInGame {
        preVoteHelper(voter);
        playersThatVotedYes.add(voter);
        playersThatVotedNo.remove(voter);
        postVoteHelper();
    }

    public void voteNo(Player voter) throws GovernmentShutdown, ChancellorNominationState, PlayerNotInGame {
        preVoteHelper(voter);
        playersThatVotedNo.add(voter);
        playersThatVotedYes.remove(voter);
        postVoteHelper();
    }

    private void preVoteHelper(Player voter) throws ChancellorNominationState, PlayerNotInGame {
        if (!players.contains(voter)) {
            throw new PlayerNotInGame();
        }
        if (chancellorNominee == null) {
            throw new ChancellorNominationState();
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
        if (pile.size() < 3) {
            pile.addAll(discardedPile);
            shuffle(pile);
            discardedPile.clear();
        }
        List<Policy> drawnCards = new ArrayList<>();
        drawnCards.add(pile.get(0));
        pile.remove(0);
        drawnCards.add(pile.get(0));
        pile.remove(0);
        drawnCards.add(pile.get(0));
        pile.remove(0);
        return drawnCards;
    }

    public List<Policy> getDiscardedPile() {
        return discardedPile;
    }

    public long getId() {
        return id;
    }

    public Optional<Player> getChancellorNominee() {
        return Optional.ofNullable(chancellorNominee);
    }

    public void giveTwoCardsToChancellor(Policy firstPolicy, Policy secondPolicy, Policy discardedPolicy) {
        chancellor.giveCards(asList(firstPolicy, secondPolicy));
        discardedPile.add(discardedPolicy);
    }

    public void discardCard(Policy policy) {
        discardedPile.add(policy);
        int index = pile.indexOf(policy);
        if (index > -1) {
            pile.remove(index);
        }
    }
}
