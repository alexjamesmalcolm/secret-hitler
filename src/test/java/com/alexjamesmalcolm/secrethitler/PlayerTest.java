package com.alexjamesmalcolm.secrethitler;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PlayerTest {

    private Player underTest;

    @Before
    public void setup() {
        underTest = new Player("");
    }

    @Test
    public void shouldHaveLiberalRole() {
        underTest.setAsLiberal();
        String role = underTest.getSecretRole();
        assertThat(role, is("liberal"));
    }

    @Test
    public void shouldHaveFascistRole() {
        underTest.setAsFascist();
        String role = underTest.getSecretRole();
        assertThat(role, is("fascist"));
    }

    @Test
    public void shouldHaveHitlerRole() {
        underTest.setAsHitler();
        String role = underTest.getSecretRole();
        assertThat(role, is("hitler"));
    }

    @Test
    public void shouldHaveLiberalParty() {
        underTest.setAsLiberal();
        String party = underTest.getPartyMembership();
        assertThat(party, is("liberal"));
    }
}
