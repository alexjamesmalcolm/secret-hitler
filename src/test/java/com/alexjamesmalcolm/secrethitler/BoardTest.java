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
        playerOne = new Player();
        playerTwo = new Player();
        playerThree = new Player();
        playerFour = new Player();
        playerFive = new Player();
        playerSix = new Player();
        playerSeven = new Player();
        playerEight = new Player();
        playerNine = new Player();
    }

    @Test(expected = LiberalsWin.class)
    public void shouldHaveLiberalsWinWithFiveLiberalPolicies() throws Victory, PresidentialPower {
        underTest = new Board();
        underTest.place(liberalPolicyOne);
        underTest.place(liberalPolicyTwo);
        underTest.place(liberalPolicyThree);
        underTest.place(liberalPolicyFour);
        underTest.place(liberalPolicyFive);
    }

    @Test
    public void shouldHaveLiberalsNotWinWithOnlyFourLiberalPolicies() throws Victory, PresidentialPower {
        underTest = new Board();
        underTest.place(liberalPolicyOne);
        underTest.place(liberalPolicyTwo);
        underTest.place(liberalPolicyThree);
        underTest.place(liberalPolicyFour);
    }

    @Test(expected = FascistsWin.class)
    public void shouldHaveFascistsWinWithSixFascistPolicies() throws Victory, PresidentialPower {
        underTest = new Board();
        underTest.place(fascistPolicyOne);
        underTest.place(fascistPolicyTwo);
        underTest.place(fascistPolicyThree);
        underTest.place(fascistPolicyFour);
        underTest.place(fascistPolicyFive);
        underTest.place(fascistPolicySix);
    }

    @Test(expected = InvestigateLoyalty.class)
    public void shouldForcePresidentToInvestigateAPartyCardWhenThereAreNinePlayersAndOneFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
        underTest.place(new FascistPolicy());
    }

    @Test(expected = InvestigateLoyalty.class)
    public void shouldThrowInvestigateLoyaltyWhenThereAreNinePlayersAndTheSecondFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
        underTest = new Board(playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
        try {
        	underTest.place(new FascistPolicy());
        } catch (InvestigateLoyalty e) {
        	//Handled
        }
        underTest.place(new FascistPolicy());
    }
    
    @Test(expected = SpecialElection.class)
    public void shouldThrowSpecialElectionWhenThereAreNinePlayersAndTheThirdFascistPolicyHasBeenPlaced() throws Victory, PresidentialPower {
    	underTest = new Board(playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
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
    	underTest = new Board(playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
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
    	underTest = new Board(playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
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
}
