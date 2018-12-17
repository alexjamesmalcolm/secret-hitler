package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.IdentityAlreadyAssigned;
import com.alexjamesmalcolm.secrethitler.game.Player;
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
    public void shouldHaveLiberalRole() throws IdentityAlreadyAssigned {
        underTest.setAsLiberal();
        String role = underTest.getSecretRole();
        assertThat(role, is("liberal"));
    }

    @Test
    public void shouldHaveFascistRole() throws IdentityAlreadyAssigned {
        underTest.setAsFascist();
        String role = underTest.getSecretRole();
        assertThat(role, is("fascist"));
    }

    @Test
    public void shouldHaveHitlerRole() throws IdentityAlreadyAssigned {
        underTest.setAsHitler();
        String role = underTest.getSecretRole();
        assertThat(role, is("hitler"));
    }

    @Test
    public void shouldHaveLiberalParty() throws IdentityAlreadyAssigned {
        underTest.setAsLiberal();
        String party = underTest.getPartyMembership();
        assertThat(party, is("liberal"));
    }

    @Test
    public void shouldHaveFascistParty() throws IdentityAlreadyAssigned {
        underTest.setAsFascist();
        String party = underTest.getPartyMembership();
        assertThat(party, is("fascist"));
    }

    @Test
    public void shouldHaveFascistPartyAsHitler() throws IdentityAlreadyAssigned {
        underTest.setAsHitler();
        String party = underTest.getPartyMembership();
        assertThat(party, is("fascist"));
    }

    @Test(expected = IdentityAlreadyAssigned.class)
    public void shouldNotBeAbleToAssignLiberalTwice() throws IdentityAlreadyAssigned {
        underTest.setAsLiberal();
        underTest.setAsLiberal();
    }

    @Test(expected = IdentityAlreadyAssigned.class)
    public void shouldNotBeAbleToAssignFascistTwice() throws IdentityAlreadyAssigned {
        underTest.setAsFascist();
        underTest.setAsFascist();
    }

    @Test(expected = IdentityAlreadyAssigned.class)
    public void shouldNotBeAbleToAssignHitlerTwice() throws IdentityAlreadyAssigned {
        underTest.setAsHitler();
        underTest.setAsHitler();
    }
}
