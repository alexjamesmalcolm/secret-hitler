package com.alexjamesmalcolm.secrethitler;

import org.junit.Before;
import org.junit.Test;

import com.alexjamesmalcolm.secrethitler.exceptions.presidentialpower.Execution;
import com.alexjamesmalcolm.secrethitler.exceptions.presidentialpower.InvestigateLoyalty;
import com.alexjamesmalcolm.secrethitler.exceptions.presidentialpower.PresidentialPower;
import com.alexjamesmalcolm.secrethitler.exceptions.presidentialpower.SpecialElection;
import com.alexjamesmalcolm.secrethitler.exceptions.victories.FascistsWin;
import com.alexjamesmalcolm.secrethitler.exceptions.victories.LiberalsWin;
import com.alexjamesmalcolm.secrethitler.exceptions.victories.Victory;
import com.alexjamesmalcolm.secrethitler.policies.FascistPolicy;
import com.alexjamesmalcolm.secrethitler.policies.LiberalPolicy;
import com.alexjamesmalcolm.secrethitler.policies.Policy;

public class BoardTest {

    private Board underTest;
    private Game game;
    private Policy liberalPolicyOne;
    private Policy liberalPolicyTwo;
    private Policy liberalPolicyThree;
    private Policy liberalPolicyFour;
    private Policy liberalPolicyFive;
    private Policy fascistPolicyOne;
    private Policy fascistPolicyTwo;
    private Policy fascistPolicyThree;
    private Policy fascistPolicyFour;
    private Policy fascistPolicyFive;
    private Policy fascistPolicySix;
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private Player playerFour;
    private Player playerFive;
    private Player playerSix;
    private Player playerSeven;
    private Player playerEight;
    private Player playerNine;

    @Before
    public void setup() {
        liberalPolicyOne = new LiberalPolicy();
        liberalPolicyTwo = new LiberalPolicy();
        liberalPolicyThree = new LiberalPolicy();
        liberalPolicyFour = new LiberalPolicy();
        liberalPolicyFive = new LiberalPolicy();
        fascistPolicyOne = new FascistPolicy();
        fascistPolicyTwo = new FascistPolicy();
        fascistPolicyThree = new FascistPolicy();
        fascistPolicyFour = new FascistPolicy();
        fascistPolicyFive = new FascistPolicy();
        fascistPolicySix = new FascistPolicy();
        playerOne = new Player("Player One");
        playerTwo = new Player("Player Two");
        playerThree = new Player("Player Three");
        playerFour = new Player("Player Four");
        playerFive = new Player("Player Five");
        playerSix = new Player("Player Six");
        playerSeven = new Player("Player Seven");
        playerEight = new Player("Player Eight");
        playerNine = new Player("Player Nine");
        game = new Game();
    }

    @Test(expected = LiberalsWin.class)
    public void shouldHaveLiberalsWinWithFiveLiberalPolicies() throws Victory, PresidentialPower {
        underTest = new Board(game);
        underTest.place(liberalPolicyOne);
        underTest.place(liberalPolicyTwo);
        underTest.place(liberalPolicyThree);
        underTest.place(liberalPolicyFour);
        underTest.place(liberalPolicyFive);
    }

    @Test
    public void shouldHaveLiberalsNotWinWithOnlyFourLiberalPolicies() throws Victory, PresidentialPower {
        underTest = new Board(game);
        underTest.place(liberalPolicyOne);
        underTest.place(liberalPolicyTwo);
        underTest.place(liberalPolicyThree);
        underTest.place(liberalPolicyFour);
    }

    @Test(expected = FascistsWin.class)
    public void shouldHaveFascistsWinWithSixFascistPolicies() throws Victory, PresidentialPower {
        underTest = new Board(game);
        try {
            underTest.place(fascistPolicyOne);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicyTwo);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicyThree);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicyFour);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicyFive);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicySix);
        } catch (PresidentialPower ignored) {
        }
    }

    @Test(expected = InvestigateLoyalty.class)
    public void shouldForcePresidentToInvestigateAPartyCardWhenThereAreNinePlayersAndOneFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
        underTest.place(new FascistPolicy());
    }

    @Test(expected = InvestigateLoyalty.class)
    public void shouldThrowInvestigateLoyaltyWhenThereAreNinePlayersAndTheSecondFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
        try {
            underTest.place(new FascistPolicy());
        } catch (InvestigateLoyalty e) {
            //Handled
        }
        underTest.place(new FascistPolicy());
    }

    @Test(expected = SpecialElection.class)
    public void shouldThrowSpecialElectionWhenThereAreNinePlayersAndTheThirdFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
        try {
            underTest.place(new FascistPolicy());
        } catch (InvestigateLoyalty e) {
            //Handled
        }
        try {
            underTest.place(new FascistPolicy());
        } catch (InvestigateLoyalty e) {
            //Handled
        }
        underTest.place(new FascistPolicy());
    }

    @Test(expected = Execution.class)
    public void shouldThrowExecutionWhenThereAreNinePlayersAndTheFourthFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
        try {
            underTest.place(new FascistPolicy());
        } catch (PresidentialPower e) {
            //Handled
        }
        try {
            underTest.place(new FascistPolicy());
        } catch (PresidentialPower e) {
            //Handled
        }
        try {
            underTest.place(new FascistPolicy());
        } catch (PresidentialPower e) {
            //Handled
        }
        underTest.place(new FascistPolicy());
    }

    @Test(expected = Execution.class)
    public void shouldThrowExecutionWhenThereAreNinePlayersAndTheFifthFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
        try {
            underTest.place(new FascistPolicy());
        } catch (PresidentialPower e) {
            //Handled
        }
        try {
            underTest.place(new FascistPolicy());
        } catch (PresidentialPower e) {
            //Handled
        }
        try {
            underTest.place(new FascistPolicy());
        } catch (PresidentialPower e) {
            //Handled
        }
        try {
            underTest.place(new FascistPolicy());
        } catch (PresidentialPower e) {
            //Handled
        }
        underTest.place(new FascistPolicy());
    }

    @Test
    public void shouldNotThrowPresidentialPowerWhenThereAreSevenPlayersAndTheFirstFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven);
        underTest.place(fascistPolicyOne);
    }

    @Test
    public void shouldNotThrowPresidentialPowerWhenThereAreEightPlayersAndTheFirstFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight);
        underTest.place(fascistPolicyOne);
    }

    @Test(expected = InvestigateLoyalty.class)
    public void shouldThrowInvestigateLoyaltyWhenThereAreSevenPlayersAndTheSecondFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven);
        underTest.place(fascistPolicyOne);
        underTest.place(fascistPolicyTwo);
    }

    @Test(expected = InvestigateLoyalty.class)
    public void shouldThrowInvestigateLoyaltyWhenThereAreEightPlayersAndTheSecondFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight);
        underTest.place(fascistPolicyOne);
        underTest.place(fascistPolicyTwo);
    }

    @Test(expected = SpecialElection.class)
    public void shouldThrowSpecialElectionWhenThereAreSevenPlayersAndTheThirdFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven);
        try {
            underTest.place(fascistPolicyOne);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicyTwo);
        } catch (PresidentialPower ignored) {
        }
        underTest.place(fascistPolicyThree);
    }

    @Test(expected = Execution.class)
    public void shouldThrowExecutionWhenThereAreSevenPlayersAndTheFourthFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven);
        try {
            underTest.place(fascistPolicyOne);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicyTwo);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicyThree);
        } catch (PresidentialPower ignored) {
        }
        underTest.place(fascistPolicyFour);
    }

    @Test(expected = Execution.class)
    public void shouldThrowExecutionWhenThereAreSevenPlayersAndTheFifthFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven);
        try {
            underTest.place(fascistPolicyOne);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicyTwo);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicyThree);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicyFour);
        } catch (PresidentialPower ignored) {
        }
        underTest.place(fascistPolicyFive);
    }

    @Test
    public void shouldNotThrowPresidentialPowerWhenThereAreFivePlayersAndTheFirstFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive);
        underTest.place(fascistPolicyOne);
    }

    @Test
    public void shouldNotThrowPresidentialPowerWhenThereAreFivePlayersAndTheSecondFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive);
        underTest.place(fascistPolicyOne);
        underTest.place(fascistPolicyTwo);
    }

    @Test
    public void shouldNotThrowPresidentialPowerWhenThereAreSixPlayersAndTheSecondFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix);
        underTest.place(fascistPolicyOne);
        underTest.place(fascistPolicyTwo);
    }

    @Test(expected = SpecialElection.class)
    public void shouldThrowSpecialElectionWhenThereAreFivePlayersAndTheThirdFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive);
        underTest.place(fascistPolicyOne);
        underTest.place(fascistPolicyTwo);
        underTest.place(fascistPolicyThree);
    }

    @Test(expected = Execution.class)
    public void shouldThrowExecutionWhenThereAreFivePlayersAndTheFourthFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive);
        underTest.place(fascistPolicyOne);
        underTest.place(fascistPolicyTwo);
        try {
            underTest.place(fascistPolicyThree);
        } catch (PresidentialPower ignored) {
        }
        underTest.place(fascistPolicyFour);
    }

    @Test(expected = Execution.class)
    public void shouldThrowExecutionWhenThereAreFivePlayersAndTheFifthFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(game, playerOne, playerTwo, playerThree, playerFour, playerFive);
        underTest.place(fascistPolicyOne);
        underTest.place(fascistPolicyTwo);
        try {
            underTest.place(fascistPolicyThree);
        } catch (PresidentialPower ignored) {
        }
        try {
            underTest.place(fascistPolicyFour);
        } catch (PresidentialPower ignored) {
        }
        underTest.place(fascistPolicyFive);
    }
}
