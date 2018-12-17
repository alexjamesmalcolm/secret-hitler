package com.alexjamesmalcolm.secrethitler.game;

import com.alexjamesmalcolm.secrethitler.Board;
import com.alexjamesmalcolm.secrethitler.exceptions.GameFullOfPlayers;
import com.alexjamesmalcolm.secrethitler.exceptions.GameNotStartedException;
import com.alexjamesmalcolm.secrethitler.exceptions.TooFewPlayersException;
import com.alexjamesmalcolm.secrethitler.policies.FascistPolicy;
import com.alexjamesmalcolm.secrethitler.policies.LiberalPolicy;
import com.alexjamesmalcolm.secrethitler.policies.Policy;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameTest {

    private Game underTest;
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private Player playerFour;
    private Player playerFive;
    private Player playerSix;
    private Player playerSeven;
    private Player playerEight;
    private Player playerNine;
    private Player playerTen;

    @Before
    public void setup() {
        underTest = new Game();
        playerOne = new Player(0);
        playerTwo = new Player(1);
        playerThree = new Player(2);
        playerFour = new Player(3);
        playerFive = new Player(4);
        playerSix = new Player(5);
        playerSeven = new Player(6);
        playerEight = new Player(7);
        playerNine = new Player(8);
        playerTen = new Player(9);

    }

    @Test
    public void shouldHavePlayer() throws GameFullOfPlayers {
        String name = "John";
        Player player = new Player(name);
        underTest.addPlayer(player);
        Collection<Player> players = underTest.getPlayers();
        assertThat(players.contains(player), is(true));
    }

    @Test
    public void shouldHaveManyPlayers() throws GameFullOfPlayers {
        Player player = new Player("Player");
        Player anotherPlayer = new Player("Another Player");
        underTest.addPlayer(player);
        underTest.addPlayer(anotherPlayer);
        Collection<Player> players = underTest.getPlayers();
        assertThat(players.contains(player), is(true));
    }

    @Test
    public void shouldHaveThreeLiberalsIfThereAreFivePlayers() throws GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        int actual = underTest.numberOfLiberals();
        assertThat(actual, is(3));
    }

    @Test
    public void shouldHaveFourLiberalsIfThereAreSixPlayers() throws GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.addPlayer(playerSix);
        int actual = underTest.numberOfLiberals();
        assertThat(actual, is(4));
    }

    @Test
    public void shouldHaveFiveLiberalsIfThereAreEightPlayers() throws GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.addPlayer(playerSix);
        underTest.addPlayer(playerSeven);
        underTest.addPlayer(playerEight);
        int actual = underTest.numberOfLiberals();
        assertThat(actual, is(5));
    }

    @Test
    public void shouldHaveSixLiberalsIfThereAreTenPlayers() throws GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.addPlayer(playerSix);
        underTest.addPlayer(playerSeven);
        underTest.addPlayer(playerEight);
        underTest.addPlayer(playerNine);
        underTest.addPlayer(playerTen);
        int actual = underTest.numberOfLiberals();
        assertThat(actual, is(6));
    }

    @Test
    public void shouldHaveTwoFascistsIfThereAreFivePlayers() throws GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        int actual = underTest.numberOfFascists();
        assertThat(actual, is(2));
    }

    @Test
    public void shouldHaveThreeFascistsIfThereAreSevenPlayers() throws GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.addPlayer(playerSix);
        underTest.addPlayer(playerSeven);
        int actual = underTest.numberOfFascists();
        assertThat(actual, is(3));
    }

    @Test
    public void shouldHaveFourFascistsIfThereAreNinePlayers() throws GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.addPlayer(playerSix);
        underTest.addPlayer(playerSeven);
        underTest.addPlayer(playerEight);
        underTest.addPlayer(playerNine);
        int actual = underTest.numberOfFascists();
        assertThat(actual, is(4));
    }

    @Test
    public void shouldNotBeAbleToAddPlayersWhenGameIsStarted() throws TooFewPlayersException, GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        underTest.addPlayer(playerSix);
        Collection<Player> players = underTest.getPlayers();
        assertThat(players.contains(playerSix), is(false));
    }

    @Test(expected = TooFewPlayersException.class)
    public void shouldNotBeAbleToStartWithNoPlayers() throws TooFewPlayersException {
        underTest.start();
    }

    @Test(expected = TooFewPlayersException.class)
    public void shouldNotBeAbleToStartWithFourPlayers() throws TooFewPlayersException, GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.start();
    }

    @Test(expected = GameNotStartedException.class)
    public void shouldNotHaveBoardBeforeGameStarts() throws GameNotStartedException {
        underTest.getBoard();
    }

    @Test
    public void shouldHaveBoardAfterGameStarts() throws GameNotStartedException, TooFewPlayersException, GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        Board board = underTest.getBoard();
        assertThat(board, notNullValue());
    }

    @Test(expected = GameFullOfPlayers.class)
    public void shouldNotBeAbleToAddElevenPlayers() throws GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.addPlayer(playerSix);
        underTest.addPlayer(playerSeven);
        underTest.addPlayer(playerEight);
        underTest.addPlayer(playerNine);
        underTest.addPlayer(playerTen);
        Player playerEleven = new Player("Player Eleven");
        underTest.addPlayer(playerEleven);
    }

    @Test
    public void shouldHaveElevenFascistPoliciesInDrawPile() throws GameFullOfPlayers, TooFewPlayersException {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        List<Policy> policies = underTest.getDrawPile();
        int count = (int) policies.stream().filter(policy -> policy instanceof FascistPolicy).count();
        assertThat(count, is(11));
    }

    @Test
    public void shouldHaveSixLiberalPoliciesInDrawPile() throws GameFullOfPlayers, TooFewPlayersException {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        List<Policy> policies = underTest.getDrawPile();
        int count = (int) policies.stream().filter(policy -> policy instanceof LiberalPolicy).count();
        assertThat(count, is(6));
    }
}
