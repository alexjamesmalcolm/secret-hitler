package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.LiberalsWin;
import org.junit.Test;

public class BoardTest {

    @Test(expected = LiberalsWin.class)
    public void shouldHaveLiberalsWinWithFiveLiberalPolicies() throws LiberalsWin {
        Board underTest = new Board();
        Policy liberalPolicyOne = new LiberalPolicy();
        Policy liberalPolicyTwo = new LiberalPolicy();
        Policy liberalPolicyThree = new LiberalPolicy();
        Policy liberalPolicyFour = new LiberalPolicy();
        Policy liberalPolicyFive = new LiberalPolicy();
        underTest.place(liberalPolicyOne);
        underTest.place(liberalPolicyTwo);
        underTest.place(liberalPolicyThree);
        underTest.place(liberalPolicyFour);
        underTest.place(liberalPolicyFive);
    }
}
