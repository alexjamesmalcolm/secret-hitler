package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.*;
import com.alexjamesmalcolm.secrethitler.policies.Policy;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GameLegislativeTest {

    private Game underTest;
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private Player playerFour;
    private Player playerFive;

    @Before
    public void setup() {
        underTest = new Game();
        playerOne = new Player("Player One");
        playerTwo = new Player("Player Two");
        playerThree = new Player("Player Three");
        playerFour = new Player("Player Four");
        playerFive = new Player("Player Five");
    }

    @Test
    public void shouldDrawThreePolicies() throws GameFullOfPlayers, TooFewPlayersException {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        List<Policy> drawCards = underTest.drawThreeCards();
        int amount = drawCards.size();
        assertThat(amount, is(3));
    }

    @Test
    public void shouldDrawPoliciesFromDrawPile() throws GameFullOfPlayers, TooFewPlayersException {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        List<Policy> drawPile = new LinkedList<>(underTest.getDrawPile());
        List<Policy> threeDrawCards = underTest.drawThreeCards();
        boolean assertion = drawPile.containsAll(threeDrawCards);
        assertTrue(assertion);
    }

    @Test
    public void shouldRemoveDrawnCardsFromTheDrawPileAfterDrawing() throws GameFullOfPlayers, TooFewPlayersException {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        List<Policy> threeDrawCards = underTest.drawThreeCards();
        List<Policy> drawPile = underTest.getDrawPile();
        boolean assertion = !drawPile.containsAll(threeDrawCards);
        assertTrue(assertion);
    }

    @Test
    public void shouldHavePresidentAutoDrawCardsWhenElected() throws GameFullOfPlayers, TooFewPlayersException, GovernmentShutdown, OutstandingChancellorNomination, InvalidNomination, PlayerNotInGame {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        List<Policy> drawPile = new LinkedList<>(underTest.getDrawPile());
        underTest.nominateAsChancellor(playerTwo);
        underTest.voteYes(playerOne);
        underTest.voteYes(playerTwo);
        underTest.voteYes(playerThree);
        underTest.voteYes(playerFour);
        underTest.voteYes(playerFive);
        Player president = underTest.getPresident().get();
        List<Policy> policies = president.getPolicyHand();
        boolean assertion = drawPile.containsAll(policies);
        assertTrue(assertion);
    }

    @Test
    public void shouldHavePresidentHaveThreeCards() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        underTest.nominateAsChancellor(playerTwo);
        underTest.voteYes(playerOne);
        underTest.voteYes(playerTwo);
        underTest.voteYes(playerThree);
        underTest.voteYes(playerFour);
        underTest.voteYes(playerFive);
        List<Policy> policies = underTest.getPresident().get().getPolicyHand();
        assertThat(policies.size(), is(3));
    }
}
