package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.*;
import com.alexjamesmalcolm.secrethitler.policies.Policy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
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
    public void shouldHavePresidentsUnpickedCardEnterDiscardPile() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
    public void shouldHaveDiscardedCardsBeSaved() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination, PlayerNotInGame {
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
        System.out.println(discardedPolicy);
        System.out.println(discardedPolicies);
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
    public void shouldHaveDrawPileBeFourteenCardsAfterSomeoneIsElectedPresident() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, OutstandingChancellorNomination {
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
        cards.forEach(System.out::println);
        assertThat(cards.size(), is(14));
    }

    @Test
    public void shouldRemoveCardsFromHandWhenPresidentGivesThemToChancellor() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, OutstandingChancellorNomination {
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
}
