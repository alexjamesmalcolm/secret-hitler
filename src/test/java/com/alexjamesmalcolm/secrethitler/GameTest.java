package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.GameFullOfPlayers;
import com.alexjamesmalcolm.secrethitler.exceptions.GameNotStartedException;
import com.alexjamesmalcolm.secrethitler.exceptions.TooFewPlayersException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

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
        playerOne = new Player();
        playerTwo = new Player();
        playerThree = new Player();
        playerFour = new Player();
        playerFive = new Player();
        playerSix = new Player();
    }

    @Test
    public void shouldHavePlayer() throws GameFullOfPlayers {
        String name = "John";
        Player player = new Player();
        underTest.addPlayer(player);
        Collection<Player> players = underTest.getPlayers();
        assertThat(players.contains(player), is(true));
    }

    @Test
    public void shouldHaveManyPlayers() throws GameFullOfPlayers {
        Player player = new Player();
        Player anotherPlayer = new Player();
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
        assertThat(actual, is (5));
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
        Player playerEleven = new Player();
        underTest.addPlayer(playerEleven);
    }

    @Test
    public void shouldHavePlayerOneBeFirstPresidentialCandidate() throws GameFullOfPlayers, TooFewPlayersException {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        System.out.println(underTest.getPlayers());
        Player candidate = underTest.getPresidentialCandidate();
        assertThat(candidate, is(playerOne));
    }

    @Test
    public void shouldElectPlayerOneAsPresidentAndPlayerTwoAsChancellor() throws GameFullOfPlayers, TooFewPlayersException {
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
        underTest.voteNo(playerFour);
        underTest.voteNo(playerFive);
        Optional<Player> potentialPresident = underTest.getPresident();
        Optional<Player> potentialChancellor = underTest.getChancellor();
        assertThat(potentialPresident.get(), is(playerOne));
        assertThat(potentialChancellor.get(), is(playerTwo));
    }

    @Test
    public void shouldHaveNoElectedOfficialsIfVoteWasMajorityNo() throws GameFullOfPlayers, TooFewPlayersException {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        underTest.nominateAsChancellor(playerTwo);
        underTest.voteYes(playerOne);
        underTest.voteYes(playerTwo);
        underTest.voteNo(playerThree);
        underTest.voteNo(playerFour);
        underTest.voteNo(playerFive);
        Optional<Player> potentialPresident = underTest.getPresident();
        Optional<Player> potentialChancellor = underTest.getChancellor();
        assertThat(potentialPresident.isPresent(), is(false));
        assertThat(potentialChancellor.isPresent(), is(false));
    }

    @Test
    public void shouldHaveNoElectedOfficialsIfVoteThatWasYesWasChangedToBeNo() throws GameFullOfPlayers, TooFewPlayersException {
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
        underTest.voteNo(playerFour);
        underTest.voteNo(playerThree);
        underTest.voteNo(playerFour);
        Optional<Player> potentialPresident = underTest.getPresident();
        Optional<Player> potentialChancellor = underTest.getChancellor();
        assertThat(potentialPresident.isPresent(), is(false));
        assertThat(potentialChancellor.isPresent(), is(false));
    }

    @Test
    public void shouldHaveElectedOfficialsIfVoteThatWasNoWasChangedToBeYes() throws GameFullOfPlayers, TooFewPlayersException {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        underTest.nominateAsChancellor(playerTwo);
        underTest.voteYes(playerOne);
        underTest.voteYes(playerTwo);
        underTest.voteNo(playerThree);
        underTest.voteNo(playerFour);
        underTest.voteYes(playerThree);
        underTest.voteNo(playerFour);
        Optional<Player> potentialPresident = underTest.getPresident();
        Optional<Player> potentialChancellor = underTest.getChancellor();
        assertThat(potentialPresident.isPresent(), is(true));
        assertThat(potentialChancellor.isPresent(), is(true));
    }

    @Test
    public void shouldHaveNoElectedOfficialsIfVotingIsNotComplete() throws GameFullOfPlayers, TooFewPlayersException {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        underTest.nominateAsChancellor(playerTwo);
        underTest.voteYes(playerOne);
        underTest.voteYes(playerTwo);
        Optional<Player> potentialPresident = underTest.getPresident();
        Optional<Player> potentialChancellor = underTest.getChancellor();
        assertThat(potentialPresident.isPresent(), is(false));
        assertThat(potentialChancellor.isPresent(), is(false));
    }

    @Test
    public void shouldHavePresidentialCandidateBePlayerTwoIfElectionFails() throws TooFewPlayersException, GameFullOfPlayers {
        underTest.addPlayer(playerOne);
        underTest.addPlayer(playerTwo);
        underTest.addPlayer(playerThree);
        underTest.addPlayer(playerFour);
        underTest.addPlayer(playerFive);
        underTest.start();
        underTest.nominateAsChancellor(playerTwo);
        underTest.voteNo(playerOne);
        underTest.voteNo(playerTwo);
        underTest.voteNo(playerThree);
        underTest.voteNo(playerFour);
        underTest.voteNo(playerFive);
        System.out.println(underTest.getPlayers());
        Player presidentialCandidate = underTest.getPresidentialCandidate();
        assertThat(presidentialCandidate, is(playerTwo));
    }
}
