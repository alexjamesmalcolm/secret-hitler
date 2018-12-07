package com.alexjamesmalcolm.secrethitler;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameTest {

    @Test
    public void shouldHavePlayer() {
        Game underTest = new Game();
        String name = "John";
        Player player = new Player(name);
        underTest.addPlayer(player);
        Collection<Player> players = underTest.getPlayers();
        assertThat(players.contains(player), is(true));
    }

    @Test
    public void shouldHaveManyPlayers() {
        Game underTest = new Game();
        Player player = new Player("John");
        Player anotherPlayer = new Player("Jane");
        underTest.addPlayer(player);
        underTest.addPlayer(anotherPlayer);
        Collection<Player> players = underTest.getPlayers();
        assertThat(players.contains(player), is(true));
    }
}
