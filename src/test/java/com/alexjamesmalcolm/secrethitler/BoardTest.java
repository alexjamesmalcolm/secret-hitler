package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.victories.FascistsWin;
import com.alexjamesmalcolm.secrethitler.exceptions.victories.LiberalsWin;
import com.alexjamesmalcolm.secrethitler.exceptions.victories.Victory;
import com.alexjamesmalcolm.secrethitler.policies.FascistPolicy;
import com.alexjamesmalcolm.secrethitler.policies.LiberalPolicy;
import com.alexjamesmalcolm.secrethitler.policies.Policy;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

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
    public void shouldHaveLiberalsWinWithFiveLiberalPolicies() throws LiberalsWin, FascistsWin, InvestigateLoyalty {
        underTest = new Board();
        underTest.place(liberalPolicyOne);
        underTest.place(liberalPolicyTwo);
        underTest.place(liberalPolicyThree);
        underTest.place(liberalPolicyFour);
        underTest.place(liberalPolicyFive);
    }

    @Test
    public void shouldHaveLiberalsNotWinWithOnlyFourLiberalPolicies() throws LiberalsWin, FascistsWin, InvestigateLoyalty {
        underTest = new Board();
        underTest.place(liberalPolicyOne);
        underTest.place(liberalPolicyTwo);
        underTest.place(liberalPolicyThree);
        underTest.place(liberalPolicyFour);
    }

    @Test(expected = FascistsWin.class)
    public void shouldHaveFascistsWinWithSixFascistPolicies() throws LiberalsWin, FascistsWin, InvestigateLoyalty {
        underTest = new Board();
        underTest.place(fascistPolicyOne);
        underTest.place(fascistPolicyTwo);
        underTest.place(fascistPolicyThree);
        underTest.place(fascistPolicyFour);
        underTest.place(fascistPolicyFive);
        underTest.place(fascistPolicySix);
    }

    @Test(expected = InvestigateLoyalty.class)
    public void shouldForcePresidentToInvestigateAPartyCardWhenThereAreNinePlayersAndOneFascistPolicyHasBeenPlaced() throws Victory, InvestigateLoyalty {
        underTest = new Board(playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
        underTest.place(new FascistPolicy());
    }

    @Test(expected = InvestigateLoyalty.class)
    public void shouldThrowInvestigateLoyaltyWhenThereAreNinePlayersAndTheSecondFascistPolicyHasBeenPlaced() {
        underTest = new Board(playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix, playerSeven, playerEight, playerNine);
        // TODO FINISH THIS TEST
    }
}
