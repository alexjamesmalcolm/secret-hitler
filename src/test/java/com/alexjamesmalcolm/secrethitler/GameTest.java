package com.alexjamesmalcolm.secrethitler;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

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
        playerOne = new Player("John");
        playerTwo = new Player("Jane");
        playerThree = new Player("Joey");
        playerFour = new Player("Jose");
        playerFive = new Player("James");
        playerSix = new Player("Jesus");
    }

    @Test
    public void shouldHavePlayer() {
        String name = "John";
        Player player = new Player(name);
        underTest.addPlayer(player);
        Collection<Player> players = underTest.getPlayers();
        assertThat(players.contains(player), is(true));
    }

    @Test
    public void shouldHaveManyPlayers() {
        Player player = new Player("John");
        Player anotherPlayer = new Player("Jane");
        underTest.addPlayer(player);
        underTest.addPlayer(anotherPlayer);
        Collection<Player> players = underTest.getPlayers();
        assertThat(players.contains(player), is(true));
    }

    @Test
    public void shouldHaveThreeLiberalsIfThereAreFivePlayers() {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        int actual = underTest.numberOfLiberals();
        assertThat(actual, is(3));
    }

    @Test
    public void shouldHaveFourLiberalsIfThereAreSixPlayers() {
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
    public void shouldHaveFiveLiberalsIfThereAreEightPlayers() {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.addPlayer(playerSix);
        underTest.addPlayer(playerSeven);
        underTest.addPlayer(playerEight);
        int actual = underTest.numberOfLiberals();
        assertThat(actual, is (5));
    }

    @Test
    public void shouldHaveSixLiberalsIfThereAreTenPlayers() {
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
}
