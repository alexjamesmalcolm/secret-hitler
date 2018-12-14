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
    public void shouldSaveChancellorNominee() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination {
        game = new Game();
        playerTwo = new Player("Player Two");
        game.addPlayer(new Player("Player One"));
        game.addPlayer(playerTwo);
        game.addPlayer(new Player("Player Three"));
        game.addPlayer(new Player("Player Four"));
        game.addPlayer(new Player("Player Five"));
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
    public void shouldHavePresidentsUnpickedCardEnterDiscardPile() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, GovernmentShutdown, OutstandingChancellorNomination {
//        game = new Game();
//        game = gameRepo.save(game);
//        game.addPlayer(new Player("Player One"));
//        game.addPlayer(new Player("Player Two"));
//        game.addPlayer(new Player("Player Three"));
//        game.addPlayer(new Player("Player Four"));
//        game.addPlayer(new Player("Player Five"));
//        em.flush();
//        em.clear();
//        underTest.start();
//        underTest.nominateAsChancellor(playerTwo);
//        underTest.voteYes(playerOne);
//        underTest.voteYes(playerTwo);
//        underTest.voteYes(playerThree);
//        underTest.voteYes(playerFour);
//        underTest.voteYes(playerFive);
//        Player president = underTest.getPresident().get();
//        List<Policy> policies = president.getPolicyHand();
//        Policy firstPolicy = policies.get(0);
//        Policy secondPolicy = policies.get(1);
//        Policy discardedPolicy = policies.get(2);
//        president.giveTwoCardsToChancellor(firstPolicy, secondPolicy);
//        List<Policy> discardedPolicies = underTest.getDiscardedPile();
//        boolean assertion = discardedPolicies.contains(discardedPolicy);
//        assertTrue(assertion);
    }
}
