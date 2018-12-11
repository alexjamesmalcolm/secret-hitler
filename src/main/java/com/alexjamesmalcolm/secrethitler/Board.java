package com.alexjamesmalcolm.secrethitler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

public class Board {

    Collection<Player> players;
    List<Policy> policies = new ArrayList<>();

    public Board(Player...players) {
        this.players = Arrays.asList(players);
    }

    public void place(Policy policy) throws Victory, PresidentialPower {
        policies.add(policy);
        long numberOfLiberalPolicies = policies.stream().filter(p -> p instanceof LiberalPolicy).count();
        if (numberOfLiberalPolicies == 5) {
            throw new LiberalsWin();
        }
        long numberOfFascistPolicies = policies.stream().filter(p -> p instanceof FascistPolicy).count();
        if (numberOfFascistPolicies == 5) {
            throw new FascistsWin();
        }
        if (players.size() > 0) {
        	System.out.println("Number of fascist policies");
        	System.out.println(numberOfFascistPolicies);
        	if (numberOfFascistPolicies == 4) {
        		throw new Execution();
        	}
        	if (numberOfFascistPolicies == 3) {
        		throw new SpecialElection();
        	}
        	throw new InvestigateLoyalty();
        }
    }
}
