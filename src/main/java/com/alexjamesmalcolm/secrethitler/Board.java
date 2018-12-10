package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.victories.FascistsWin;
import com.alexjamesmalcolm.secrethitler.exceptions.victories.LiberalsWin;
import com.alexjamesmalcolm.secrethitler.policies.FascistPolicy;
import com.alexjamesmalcolm.secrethitler.policies.LiberalPolicy;
import com.alexjamesmalcolm.secrethitler.policies.Policy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Board {

    Collection<Player> players;
    List<Policy> policies = new ArrayList<>();

    public Board(Player...players) {
        this.players = Arrays.asList(players);
    }

    public void place(Policy policy) throws LiberalsWin, FascistsWin, InvestigateLoyalty {
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
            if (numberOfFascistPolicies == 1) {
                throw new InvestigateLoyalty();
            }
        }
    }
}
