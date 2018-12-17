package com.alexjamesmalcolm.secrethitler.game;

import com.alexjamesmalcolm.secrethitler.GameRepository;
import com.alexjamesmalcolm.secrethitler.PlayerRepository;
import com.alexjamesmalcolm.secrethitler.PolicyRepository;
import com.alexjamesmalcolm.secrethitler.events.GovernmentShutdown;
import com.alexjamesmalcolm.secrethitler.exceptions.*;
import com.alexjamesmalcolm.secrethitler.policies.Policy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GameJpaTest {

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

    //Should shuffle discard pile and draw pile together if there are less than 3 cards in the draw pile
    @Test
    public void shouldShuffleCardsWhenThereAreNoCardsInTheDrawPile() throws GameFullOfPlayers, TooFewPlayersException, InvalidNomination, PlayerNotInGame, GovernmentShutdown, OutstandingChancellorNomination {
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
        game.getDrawPile().forEach(policy -> game.discardCard(policy));
        List<Policy> drawnCards = game.drawThreeCards();
        assertThat(drawnCards, hasSize(3));
    }
}
