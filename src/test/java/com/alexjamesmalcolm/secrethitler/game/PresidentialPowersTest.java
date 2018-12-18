package com.alexjamesmalcolm.secrethitler.game;

import com.alexjamesmalcolm.secrethitler.GameRepository;
import com.alexjamesmalcolm.secrethitler.PlayerRepository;
import com.alexjamesmalcolm.secrethitler.PolicyRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PresidentialPowersTest {

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
    private Player playerSix;
    private Player playerSeven;
    private Player playerEight;
    private Player playerNine;
    private Player playerTen;

    @Before
    public void setup() {
        game = new Game();
        playerOne = new Player("Player One");
        playerTwo = new Player("Player Two");
        playerThree = new Player("Player Three");
        playerFour = new Player("Player Four");
        playerFive = new Player("Player Five");
        playerSix = new Player("Player Six");
        playerSeven = new Player("Player Seven");
        playerEight = new Player("Player Eight");
        playerNine = new Player("Player Nine");
        playerTen = new Player("Player Ten");
    }
}
