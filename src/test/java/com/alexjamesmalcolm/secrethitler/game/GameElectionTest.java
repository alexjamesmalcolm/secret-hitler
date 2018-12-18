package com.alexjamesmalcolm.secrethitler.game;

import com.alexjamesmalcolm.secrethitler.throwable.events.GovernmentShutdown;
import com.alexjamesmalcolm.secrethitler.throwable.events.presidentialpower.PresidentialPower;
import com.alexjamesmalcolm.secrethitler.throwable.events.victories.Victory;
import com.alexjamesmalcolm.secrethitler.throwable.exceptions.*;
import com.alexjamesmalcolm.secrethitler.policies.LiberalPolicy;
import com.alexjamesmalcolm.secrethitler.policies.Policy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GameElectionTest {
	
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
    }

	@Test
	public void shouldHavePlayerOneBeFirstPresidentialCandidate() throws GameFullOfPlayers, TooFewPlayersException {
	    underTest.addPlayer(playerOne);
	    underTest.addPlayer(playerTwo);
	    underTest.addPlayer(playerThree);
	    underTest.addPlayer(playerFour);
	    underTest.addPlayer(playerFive);
	    underTest.start();
	    Player candidate = underTest.getPresidentialCandidate();
	    assertThat(candidate, is(playerOne));
	}

	@Test
	public void shouldElectPlayerOneAsPresidentAndPlayerTwoAsChancellor() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
		Player president = underTest.getPresident().get();
		Player chancellor = underTest.getChancellor().get();
		assertThat(president, is(playerOne));
		assertThat(chancellor, is(playerTwo));
	}

	@Test
	public void shouldHaveNoElectedOfficialsIfVoteWasMajorityNo() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
	public void shouldHaveNoElectedOfficialsIfVoteThatWasYesWasChangedToBeNo() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
	public void shouldHaveElectedOfficialsIfVoteThatWasNoWasChangedToBeYes() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
	public void shouldHaveNoElectedOfficialsIfVotingIsNotComplete() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
	public void shouldHavePresidentialCandidateBePlayerTwoIfElectionFails() throws TooFewPlayersException, GameFullOfPlayers, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
	    List<Player> players = underTest.getPlayers();
	    Player nextCandidate = players.get(1);
	    Player presidentialCandidate = underTest.getPresidentialCandidate();
	    assertThat(presidentialCandidate, is(nextCandidate));
	}

	@Test(expected = InvalidNomination.class)
	public void shouldNotBeAbleToNominateYourselfForChancellor() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame {
	    underTest.addPlayer(playerOne);
	    underTest.addPlayer(playerTwo);
	    underTest.addPlayer(playerThree);
	    underTest.addPlayer(playerFour);
	    underTest.addPlayer(playerFive);
	    underTest.start();
	    underTest.nominateAsChancellor(playerOne);
	}

	@Test
	public void shouldResetVotesAfterAnElection() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
	    assertThat(underTest.getChancellor().isPresent(), is(true));
	    underTest.nominateAsChancellor(playerThree);
	    underTest.voteYes(playerOne);
	    underTest.voteYes(playerTwo);
	    underTest.voteYes(playerThree);
	    underTest.voteYes(playerFour);
	    underTest.voteYes(playerFive);
	    Optional<Player> potentialChancellor = underTest.getChancellor();
	    assertThat(potentialChancellor.get(), is(playerThree));
	}

	@Test(expected = InvalidNomination.class)
	public void shouldNotAllowATermLimitedPlayerToBeNominated() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame {
	    underTest.addPlayer(playerOne);
	    underTest.addPlayer(playerTwo);
	    underTest.addPlayer(playerThree);
	    underTest.addPlayer(playerFour);
	    underTest.addPlayer(playerFive);
	    underTest.start();
	    playerTwo.limitTerm();
	    underTest.nominateAsChancellor(playerTwo);
	}

	@Test(expected = InvalidNomination.class)
	public void shouldTermLimitPlayerAfterBeingElectedChancellor() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
	    underTest.addPlayer(playerOne);
	    underTest.addPlayer(playerTwo);
	    underTest.addPlayer(playerThree);
	    underTest.addPlayer(playerFour);
	    underTest.addPlayer(playerFive);
	    underTest.start();
	    underTest.nominateAsChancellor(playerFive);
	    underTest.voteYes(playerOne);
	    underTest.voteYes(playerTwo);
	    underTest.voteYes(playerThree);
	    underTest.voteYes(playerFour);
	    underTest.voteYes(playerFive);
	    underTest.nominateAsChancellor(playerFive);
	}

	@Test(expected = InvalidNomination.class)
	public void shouldTermLimitPlayerAfterBeingElectedPresident() throws InvalidNomination, GameFullOfPlayers, TooFewPlayersException, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
	    underTest.addPlayer(playerOne);
	    underTest.addPlayer(playerTwo);
	    underTest.addPlayer(playerThree);
	    underTest.addPlayer(playerFour);
	    underTest.addPlayer(playerFive);
	    underTest.start();
	    underTest.nominateAsChancellor(playerFive);
	    underTest.voteYes(playerOne);
	    underTest.voteYes(playerTwo);
	    underTest.voteYes(playerThree);
	    underTest.voteYes(playerFour);
	    underTest.voteYes(playerFive);
	    underTest.nominateAsChancellor(playerOne);
	}

	@Test
	public void shouldMoveUpElectionTrackerWhenVoteFails() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
	    int actual = underTest.getFailedElectionsUntilShutdown();
	    assertThat(actual, is(2));
	}

	@Test
	public void shouldHaveElectionTrackerStartAtThree() throws GameFullOfPlayers, TooFewPlayersException {
	    underTest.addPlayer(playerOne);
	    underTest.addPlayer(playerTwo);
	    underTest.addPlayer(playerThree);
	    underTest.addPlayer(playerFour);
	    underTest.addPlayer(playerFive);
	    underTest.start();
	    int actual = underTest.getFailedElectionsUntilShutdown();
	    assertThat(actual, is(3));
	}

	@Test
	public void shouldHaveElectionTrackerStayAtThreeIfElectionPasses() throws InvalidNomination, GameFullOfPlayers, TooFewPlayersException, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
	    int actual = underTest.getFailedElectionsUntilShutdown();
	    assertThat(actual, is(3));
	}

	@Test(expected = GovernmentShutdown.class)
	public void shouldShutDownGovernmentIfThreeElectionsAreFailed() throws GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
	    try {
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
	        underTest.nominateAsChancellor(playerThree);
	        underTest.voteNo(playerOne);
	        underTest.voteNo(playerTwo);
	        underTest.voteNo(playerThree);
	        underTest.voteNo(playerFour);
	        underTest.voteNo(playerFive);
	        underTest.nominateAsChancellor(playerFour);
	        underTest.voteNo(playerOne);
	        underTest.voteNo(playerTwo);
	        underTest.voteNo(playerThree);
	        underTest.voteNo(playerFour);
	    } catch (Exception e) {
	        Assert.fail(e.toString());
	    }
	    underTest.voteNo(playerFive);
	}

	@Test
	public void shouldHavePlayersNotBeTermLimitedIfElectionFails() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
	    boolean isPlayerOneTermLimited = playerOne.isTermLimited();
	    boolean isPlayerTwoTermLimited = playerTwo.isTermLimited();
	    assertThat(isPlayerOneTermLimited, is(false));
	    assertThat(isPlayerTwoTermLimited, is(false));
	}
	
	@Test
	public void shouldRemoveTermLimitIfPolicyIsPlacedByOtherElectedOfficials() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, Victory, PresidentialPower, GameNotStartedException, OutstandingChancellorNomination, PlayerNotInGame {
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
	    underTest.getBoard().place(new LiberalPolicy());
	    underTest.nominateAsChancellor(playerThree);
	    underTest.voteYes(playerOne);
	    underTest.voteYes(playerTwo);
	    underTest.voteYes(playerThree);
	    underTest.voteYes(playerFour);
	    underTest.voteYes(playerFive);
	    underTest.getBoard().place(new LiberalPolicy());
	    boolean isPlayerOneTermLimited = playerOne.isTermLimited();
	    assertThat(isPlayerOneTermLimited, is(false));
	}
	
	@Test
	public void shouldKeepTermLimitForPresidentWhenPlacingPolicy() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, Victory, PresidentialPower, GameNotStartedException, OutstandingChancellorNomination, PlayerNotInGame {
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
	    underTest.getBoard().place(new LiberalPolicy());
	    assertThat(playerOne.isTermLimited(), is(true));
	}
	
	@Test
	public void shouldKeepTermLimitForChancellorWhenPlacingPolicy() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, Victory, PresidentialPower, GameNotStartedException, OutstandingChancellorNomination, PlayerNotInGame {
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
	    underTest.getBoard().place(new LiberalPolicy());
	    assertThat(playerTwo.isTermLimited(), is(true));
	}

	@Test(expected = OutstandingChancellorNomination.class)
	public void shouldThrowOutStandingChancellorNominationIfPeopleStartVotingWhenAChancellorHasNotBeenNominated() throws GameFullOfPlayers, TooFewPlayersException, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
		underTest.addPlayer(playerOne);
		underTest.addPlayer(playerTwo);
		underTest.addPlayer(playerThree);
		underTest.addPlayer(playerFour);
		underTest.addPlayer(playerFive);
		underTest.start();
		underTest.voteYes(playerOne);
	}

	@Test(expected = OutstandingChancellorNomination.class)
	public void shouldThrowOutStandingChancellorNominationIfPeopleVoteNoWhenAChancellorHasNotBeenNominated() throws GameFullOfPlayers, TooFewPlayersException, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
		underTest.addPlayer(playerOne);
		underTest.addPlayer(playerTwo);
		underTest.addPlayer(playerThree);
		underTest.addPlayer(playerFour);
		underTest.addPlayer(playerFive);
		underTest.start();
		underTest.voteNo(playerOne);
	}

	@Test(expected = OutstandingChancellorNomination.class)
	public void shouldThrowOutstandingChancellorNominationIfPeopleStartVotingBeforeAChancellorHasBeenNominatedButAfterAnElectionAlreadyHappened() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
		underTest.voteYes(playerOne);
	}

	@Test(expected = PlayerNotInGame.class)
	public void shouldNotBeAbleToVoteIfNotInGame() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
		underTest.addPlayer(playerOne);
		underTest.addPlayer(playerTwo);
		underTest.addPlayer(playerThree);
		underTest.addPlayer(playerFour);
		underTest.addPlayer(playerFive);
		underTest.start();
		underTest.nominateAsChancellor(playerTwo);
		underTest.voteYes(playerSix);
	}

	@Test(expected = PlayerNotInGame.class)
	public void shouldNotBeAbleToNominatePlayerIfNotInGame() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame {
		underTest.addPlayer(playerOne);
		underTest.addPlayer(playerTwo);
		underTest.addPlayer(playerThree);
		underTest.addPlayer(playerFour);
		underTest.addPlayer(playerFive);
		underTest.start();
		underTest.nominateAsChancellor(playerSix);
	}

	@Test
	public void shouldAddDiscardPileToDrawPileWhenDrawPileIsEmpty() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, OutstandingChancellorNomination {
		underTest.addPlayer(playerOne);
		underTest.addPlayer(playerTwo);
		underTest.addPlayer(playerThree);
		underTest.addPlayer(playerFour);
		underTest.addPlayer(playerFive);
		underTest.start();
		new LinkedList<>(underTest.getDrawPile()).forEach(policy -> underTest.discardCard(policy));
		List<Policy> drawnCards = underTest.drawThreeCards();
		assertThat(drawnCards, hasSize(3));
	}

	@Test
	public void whenCardsAreDiscardedTheyShouldExitTheDrawPile() throws GameFullOfPlayers, TooFewPlayersException {
		underTest.addPlayer(playerOne);
		underTest.addPlayer(playerTwo);
		underTest.addPlayer(playerThree);
		underTest.addPlayer(playerFour);
		underTest.addPlayer(playerFive);
		underTest.start();
		new LinkedList<>(underTest.getDrawPile()).forEach(policy -> underTest.discardCard(policy));
		List<Policy> drawPile = underTest.getDrawPile();
		assertThat(drawPile, hasSize(0));
	}

	@Test
	public void whenTheDrawPileIsFilledByTheDiscardPileThatShouldEmptyTheDiscardPile() throws GameFullOfPlayers, TooFewPlayersException {
		underTest.addPlayer(playerOne);
		underTest.addPlayer(playerTwo);
		underTest.addPlayer(playerThree);
		underTest.addPlayer(playerFour);
		underTest.addPlayer(playerFive);
		underTest.start();
		new LinkedList<>(underTest.getDrawPile()).forEach(policy -> underTest.discardCard(policy));
		underTest.drawThreeCards();
		List<Policy> discardPile = underTest.getDiscardedPile();
		assertThat(discardPile, hasSize(0));
	}

	@Test
	public void whenDrawPileHasTwoCardsShouldAddDiscardPileToDrawPile() throws GameFullOfPlayers, TooFewPlayersException {
		underTest.addPlayer(playerOne);
		underTest.addPlayer(playerTwo);
		underTest.addPlayer(playerThree);
		underTest.addPlayer(playerFour);
		underTest.addPlayer(playerFive);
		underTest.start();
		LinkedList<Policy> drawPile = new LinkedList<>(underTest.getDrawPile());
		drawPile.remove();
		drawPile.remove();
		drawPile.forEach(policy -> underTest.discardCard(policy));
		List<Policy> drawnCards = underTest.drawThreeCards();
		assertThat(drawnCards, hasSize(3));
	}
}
