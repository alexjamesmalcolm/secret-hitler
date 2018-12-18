package com.alexjamesmalcolm.secrethitler.game;

import com.alexjamesmalcolm.secrethitler.Board;
import com.alexjamesmalcolm.secrethitler.GameRepository;
import com.alexjamesmalcolm.secrethitler.PlayerRepository;
import com.alexjamesmalcolm.secrethitler.PolicyRepository;
import com.alexjamesmalcolm.secrethitler.throwable.events.GovernmentShutdown;
import com.alexjamesmalcolm.secrethitler.throwable.events.presidentialpower.PresidentialPower;
import com.alexjamesmalcolm.secrethitler.throwable.events.victories.Victory;
import com.alexjamesmalcolm.secrethitler.throwable.exceptions.*;
import com.alexjamesmalcolm.secrethitler.policies.Policy;
import com.alexjamesmalcolm.secrethitler.throwable.state.ChancellorNominationState;
import com.alexjamesmalcolm.secrethitler.throwable.state.GameNotStartedState;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GameLegislativeJpaTest {

    @Resource
    private TestEntityManager em;

    @Resource
    private GameRepository gameRepo;

    @Resource
    private PlayerRepository playerRepo;

    @Resource
    private PolicyRepository policyRepo;

    private Game game;
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private Player playerFour;
    private Player playerFive;

    @Test
    public void shouldSaveGame() {
        game = new Game();
        game = gameRepo.save(game);
    }

    @Test
    public void shouldSavePlayerByAddingToGame() throws GameFullOfPlayers {
        game = new Game();
        game = gameRepo.save(game);
        game.addPlayer(new Player("Player One"));
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        List<Player> gamesPlayers = game.getPlayers();
        List<Player> reposPlayers = (List<Player>) playerRepo.findAll();
        boolean assertion = gamesPlayers.containsAll(reposPlayers);
        assertTrue(assertion);
    }

    @Test
    public void shouldSaveMultiplePlayersByAddingToGame() throws GameFullOfPlayers {
        game = new Game();
        game.addPlayer(new Player("Player One"));
        game.addPlayer(new Player("Player Two"));
        game.addPlayer(new Player("Player Three"));
        game.addPlayer(new Player("Player Four"));
        game.addPlayer(new Player("Player Five"));
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        List<Player> gamesPlayers = game.getPlayers();
        List<Player> reposPlayers = (List<Player>) playerRepo.findAll();
        boolean assertion = gamesPlayers.containsAll(reposPlayers);
        assertTrue(assertion);
    }

    @Test
    public void shouldSaveStartedGame() throws GameFullOfPlayers, TooFewPlayersException {
        game = new Game();
        game.addPlayer(new Player("Player One"));
        game.addPlayer(new Player("Player Two"));
        game.addPlayer(new Player("Player Three"));
        game.addPlayer(new Player("Player Four"));
        game.addPlayer(new Player("Player Five"));
        game.start();
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Collection<Policy> repoPolicies = (Collection<Policy>) policyRepo.findAll();
        List<Policy> gamePolicies = game.getDrawPile();
        boolean assertion = gamePolicies.containsAll(repoPolicies);
        assertTrue(assertion);
    }

    @Test
    public void shouldSaveChancellorNominee() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame {
        game = new Game();
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        game.addPlayer(new Player("Player One"));
        game = gameRepo.save(game);
        game.addPlayer(playerTwo);
        game = gameRepo.save(game);
        game.addPlayer(new Player("Player Three"));
        game = gameRepo.save(game);
        game.addPlayer(new Player("Player Four"));
        game = gameRepo.save(game);
        game.addPlayer(new Player("Player Five"));
        game = gameRepo.save(game);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Player chancellorNominee = game.getChancellorNominee().get();
        assertThat(chancellorNominee, is(playerTwo));
    }

    @Test
    public void shouldHavePresidentsUnpickedCardEnterDiscardPile() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, ChancellorNominationState, PlayerNotInGame {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game.voteYes(playerOne);
        game.voteYes(playerTwo);
        game.voteYes(playerThree);
        game.voteYes(playerFour);
        game.voteYes(playerFive);
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Player president = game.getPresident().get();
        List<Policy> hand = president.getPolicyHand();
        Policy firstPolicy = hand.get(0);
        Policy secondPolicy = hand.get(1);
        Policy discardedPolicy = hand.get(2);
        president.giveTwoCardsToChancellor(firstPolicy, secondPolicy);
        List<Policy> discardedPolicies = game.getDiscardedPile();
        boolean assertion = discardedPolicies.contains(discardedPolicy);
        assertTrue(assertion);
    }

    @Test
    public void shouldHaveDiscardedCardsBeSaved() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, ChancellorNominationState, PlayerNotInGame {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game.voteYes(playerOne);
        game.voteYes(playerTwo);
        game.voteYes(playerThree);
        game.voteYes(playerFour);
        game.voteYes(playerFive);
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Player president = game.getPresident().get();
        List<Policy> hand = president.getPolicyHand();
        Policy firstPolicy = hand.get(1);
        Policy secondPolicy = hand.get(2);
        Policy discardedPolicy = hand.get(0);
        president.giveTwoCardsToChancellor(firstPolicy, secondPolicy);
        game = gameRepo.save(game);
        id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        List<Policy> discardedPolicies = game.getDiscardedPile();
        boolean assertion = discardedPolicies.contains(discardedPolicy);
        assertTrue(assertion);
    }

    @Test
    public void shouldHaveDrawPileBeSeventeenCards() throws GameFullOfPlayers, TooFewPlayersException {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        List<Policy> cards = game.getDrawPile();
        assertThat(cards.size(), is(17));
    }

    @Test
    public void shouldHaveDrawPileBeFourteenCardsAfterSomeoneIsElectedPresident() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, ChancellorNominationState {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game.voteYes(playerOne);
        game.voteYes(playerTwo);
        game.voteYes(playerThree);
        game.voteYes(playerFour);
        game.voteYes(playerFive);
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        List<Policy> cards = game.getDrawPile();
        assertThat(cards.size(), is(14));
    }

    @Test
    public void shouldRemoveCardsFromHandWhenPresidentGivesThemToChancellor() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, ChancellorNominationState {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game.voteYes(playerOne);
        game.voteYes(playerTwo);
        game.voteYes(playerThree);
        game.voteYes(playerFour);
        game.voteYes(playerFive);
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Player president = game.getPresident().get();
        List<Policy> hand = president.getPolicyHand();
        Policy firstPolicy = hand.get(0);
        Policy secondPolicy = hand.get(1);
        president.giveTwoCardsToChancellor(firstPolicy, secondPolicy);
        gameRepo.save(game);
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        hand = game.getPresident().get().getPolicyHand();
        assertThat(hand.size(), is(0));
    }

    @Test
    public void shouldRemoveDrawnCardsFromTheDrawPileAfterDrawing() throws GameFullOfPlayers, TooFewPlayersException {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        long id = gameRepo.save(game).getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        List<Policy> threeDrawCards = game.drawThreeCards();
        System.out.println("Three draw cards");
        threeDrawCards.forEach(System.out::println);
        List<Policy> drawPile = game.getDrawPile();
        System.out.println("Draw Pile");
        drawPile.forEach(System.out::println);
        boolean assertion = !drawPile.containsAll(threeDrawCards);
        assertTrue(assertion);
    }

    @Test
    public void shouldGiveCardsToChancellorAfterPresidentPicks() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, ChancellorNominationState {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game.voteYes(playerOne);
        game.voteYes(playerTwo);
        game.voteYes(playerThree);
        game.voteYes(playerFour);
        game.voteYes(playerFive);
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Player president = game.getPresident().get();
        List<Policy> hand = president.getPolicyHand();
        Policy firstPolicy = hand.get(0);
        Policy secondPolicy = hand.get(1);
        president.giveTwoCardsToChancellor(firstPolicy, secondPolicy);
        gameRepo.save(game);
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        hand = game.getChancellor().get().getPolicyHand();
        boolean assertion = hand.containsAll(asList(firstPolicy, secondPolicy));
        assertTrue(assertion);
    }

    @Test
    public void shouldHaveChancellorPlacePolicyOnBoard() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, ChancellorNominationState, GameNotStartedState, Victory, PresidentialPower, NotOwnerOfPolicy, NotChancellorCannotPlacePolicies {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game.voteYes(playerOne);
        game.voteYes(playerTwo);
        game.voteYes(playerThree);
        game.voteYes(playerFour);
        game.voteYes(playerFive);
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Player president = game.getPresident().get();
        List<Policy> hand = president.getPolicyHand();
        Policy firstPolicy = hand.get(0);
        Policy secondPolicy = hand.get(1);
        president.giveTwoCardsToChancellor(firstPolicy, secondPolicy);
        Player chancellor = game.getChancellor().get();
        chancellor.placeOnBoard(firstPolicy);
        gameRepo.save(game);
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Board board = game.getBoard();
        List<Policy> policies = board.getPolicies();
        assertThat(policies, containsInAnyOrder(firstPolicy));
    }

    @Test(expected = NotOwnerOfPolicy.class)
    public void shouldThrowNotOwnerOfPolicyWhenChancellorTriesToPlaceAPolicyThatTheyDoNotHaveInTheirHand() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, ChancellorNominationState, Victory, GameNotStartedState, PresidentialPower, NotOwnerOfPolicy, NotChancellorCannotPlacePolicies {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game.voteYes(playerOne);
        game.voteYes(playerTwo);
        game.voteYes(playerThree);
        game.voteYes(playerFour);
        game.voteYes(playerFive);
        game = gameRepo.save(game);
        long id = gameRepo.save(game).getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Policy card = game.getDrawPile().get(0);
        Player chancellor = game.getChancellor().get();
        chancellor.placeOnBoard(card);
    }

    @Test
    public void shouldNotAllowPolicyToBePlacedOnBoardIfItWasNotInChancellorsHand() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, ChancellorNominationState, GameNotStartedState, Victory, PresidentialPower {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game.voteYes(playerOne);
        game.voteYes(playerTwo);
        game.voteYes(playerThree);
        game.voteYes(playerFour);
        game.voteYes(playerFive);
        game = gameRepo.save(game);
        long id = gameRepo.save(game).getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Policy card = game.getDrawPile().get(0);
        Player chancellor = game.getChancellor().get();
        try {
            chancellor.placeOnBoard(card);
        } catch (NotOwnerOfPolicy ignored) {} catch (NotChancellorCannotPlacePolicies notChancellorCannotPlacePolicies) {
            notChancellorCannotPlacePolicies.printStackTrace();
        }
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Board board = game.getBoard();
        assertThat(board.getPolicies(), not(containsInAnyOrder(card)));
    }

    @Test(expected = NotChancellorCannotPlacePolicies.class)
    public void shouldNotAllowPresidentToPlaceCardsOnBoard() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, ChancellorNominationState, Victory, NotOwnerOfPolicy, GameNotStartedState, PresidentialPower, NotChancellorCannotPlacePolicies {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game.voteYes(playerOne);
        game.voteYes(playerTwo);
        game.voteYes(playerThree);
        game.voteYes(playerFour);
        game.voteYes(playerFive);
        game = gameRepo.save(game);
        long id = gameRepo.save(game).getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Player president = game.getPresident().get();
        Policy card = president.getPolicyHand().get(0);
        president.placeOnBoard(card);
    }

    @Test
    public void shouldRemoveCardsFromChancellorsHandWhenHePlacedACardOnBoard() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, ChancellorNominationState, Victory, NotChancellorCannotPlacePolicies, NotOwnerOfPolicy, GameNotStartedState, PresidentialPower {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game.voteYes(playerOne);
        game.voteYes(playerTwo);
        game.voteYes(playerThree);
        game.voteYes(playerFour);
        game.voteYes(playerFive);
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Player president = game.getPresident().get();
        List<Policy> hand = president.getPolicyHand();
        Policy firstPolicy = hand.get(0);
        Policy secondPolicy = hand.get(1);
        president.giveTwoCardsToChancellor(firstPolicy, secondPolicy);
        Player chancellor = game.getChancellor().get();
        chancellor.placeOnBoard(firstPolicy);
        gameRepo.save(game);
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        hand = chancellor.getPolicyHand();
        assertThat(hand, Matchers.hasSize(0));
    }

    @Test
    public void shouldSendNotPlacedCardToDiscardPile() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, ChancellorNominationState, Victory, NotChancellorCannotPlacePolicies, NotOwnerOfPolicy, GameNotStartedState, PresidentialPower {
        game = new Game();
        playerOne = new Player("Player One");
        playerOne = playerRepo.save(playerOne);
        playerTwo = new Player("Player Two");
        playerTwo = playerRepo.save(playerTwo);
        playerThree = new Player("Player Three");
        playerThree = playerRepo.save(playerThree);
        playerFour = new Player("Player Four");
        playerFour = playerRepo.save(playerFour);
        playerFive = new Player("Player Five");
        playerFive = playerRepo.save(playerFive);
        game.addPlayer(playerOne);
        game.addPlayer(playerTwo);
        game.addPlayer(playerThree);
        game.addPlayer(playerFour);
        game.addPlayer(playerFive);
        game.start();
        game.nominateAsChancellor(playerTwo);
        game.voteYes(playerOne);
        game.voteYes(playerTwo);
        game.voteYes(playerThree);
        game.voteYes(playerFour);
        game.voteYes(playerFive);
        game = gameRepo.save(game);
        long id = game.getId();
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        Player president = game.getPresident().get();
        List<Policy> hand = president.getPolicyHand();
        Policy firstPolicy = hand.get(0);
        Policy secondPolicy = hand.get(1);
        president.giveTwoCardsToChancellor(firstPolicy, secondPolicy);
        Player chancellor = game.getChancellor().get();
        chancellor.placeOnBoard(firstPolicy);
        gameRepo.save(game);
        em.flush();
        em.clear();
        game = gameRepo.findById(id).get();
        List<Policy> discardedPile = game.getDiscardedPile();
        assertThat(secondPolicy, isIn(discardedPile));
    }
}
