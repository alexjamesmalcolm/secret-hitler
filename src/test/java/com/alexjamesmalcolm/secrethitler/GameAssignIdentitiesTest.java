package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.GameFullOfPlayers;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GameAssignIdentitiesTest {

    private List<Player> players;

    private Game underTest;

    @Before
    public void setup() {
        underTest = new Game();
        Player playerOne = new Player();
        Player playerTwo = new Player();
        Player playerThree = new Player();
        Player playerFour = new Player();
        Player playerFive = new Player();
        Player playerSix = new Player();
        Player playerSeven = new Player();
        Player playerEight = new Player();
        Player playerNine = new Player();
        Player playerTen = new Player();
        players = Arrays.asList(playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine, playerTen);
    }

    private void addPlayers(int numberOfPlayers) throws GameFullOfPlayers {
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = players.get(i);
            underTest.addPlayer(player);
        }
    }

    private void actAndAssert(String roleToLookFor, int expectedNumberOfRoles, int numberOfPlayers) throws GameFullOfPlayers {
        addPlayers(numberOfPlayers);
        underTest.assignIdentities();
        int actualNumberOfRoles = (int) players.stream().filter(player -> player.getSecretRole().equals(roleToLookFor)).count();
        assertThat(actualNumberOfRoles, is(expectedNumberOfRoles));
    }

    @Test
    public void shouldFindThreeLiberalRolesOutOfFivePlayers() throws GameFullOfPlayers {
        String roleToLookFor = "liberal";
        int expectedNumberOfRoles = 3;
        int numberOfPlayers = 5;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldFindOneFascistRoleOutOfFivePlayers() throws GameFullOfPlayers {
        String roleToLookFor = "fascist";
        int expectedNumberOfRoles = 1;
        int numberOfPlayers = 5;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldFindFourLiberalRolesOutOfSixPlayers() throws GameFullOfPlayers {
        String roleToLookFor = "liberal";
        int expectedNumberOfRoles = 4;
        int numberOfPlayers = 6;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldFindFourLiberalRolesOutOfSevenPlayers() throws GameFullOfPlayers {
        String roleToLookFor = "liberal";
        int expectedNumberOfRoles = 4;
        int numberOfPlayers = 7;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldFindFiveLiberalRolesOutOfEightPlayers() throws GameFullOfPlayers {
        String roleToLookFor = "liberal";
        int expectedNumberOfRoles = 5;
        int numberOfPlayers = 8;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldFindFiveLiberalRolesOutOfNinePlayers() throws GameFullOfPlayers {
        String roleToLookFor = "liberal";
        int expectedNumberOfRoles = 5;
        int numberOfPlayers = 9;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldFindSixLiberalRolesOutOfTenPlayers() throws GameFullOfPlayers {
        String roleToLookFor = "liberal";
        int expectedNumberOfRoles = 6;
        int numberOfPlayers = 10;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldFindOneFascistRoleOutOfSixPlayers() throws GameFullOfPlayers {
        String roleToLookFor = "fascist";
        int expectedNumberOfRoles = 1;
        int numberOfPlayers = 6;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldFindTwoFascistRolesOutOfSevenPlayers() throws GameFullOfPlayers {
        String roleToLookFor = "fascist";
        int expectedNumberOfRoles = 2;
        int numberOfPlayers = 7;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldFindTwoFascistRolesOutOfEightPlayers() throws GameFullOfPlayers {
        String roleToLookFor = "fascist";
        int expectedNumberOfRoles = 2;
        int numberOfPlayers = 8;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldFindThreeFascistRolesOutOfNinePlayers() throws GameFullOfPlayers {
        String roleToLookFor = "fascist";
        int expectedNumberOfRoles = 3;
        int numberOfPlayers = 9;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldFindThreeFascistRolesOutOfTenPlayers() throws GameFullOfPlayers {
        String roleToLookFor = "fascist";
        int expectedNumberOfRoles = 3;
        int numberOfPlayers = 10;
        actAndAssert(roleToLookFor, expectedNumberOfRoles, numberOfPlayers);
    }

    @Test
    public void shouldAddSevenPlayers() throws GameFullOfPlayers {
        addPlayers(7);
        Collection<Player> players = underTest.getPlayers();
        int size = players.size();
        assertThat(size, is(7));
    }
}
