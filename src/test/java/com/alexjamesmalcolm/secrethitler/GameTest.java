package com.alexjamesmalcolm.secrethitler;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameTest {

    private Game underTest;

    @Before
    public void setup() {
        underTest = new Game();
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
        Player playerOne = new Player("John");
        Player playerTwo = new Player("Jane");
        Player playerThree = new Player("Joey");
        Player playerFour = new Player("Jose");
        Player playerFive = new Player("James");
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        int actual = underTest.numberOfLiberals();
        assertThat(actual, is(3));
    }
}
