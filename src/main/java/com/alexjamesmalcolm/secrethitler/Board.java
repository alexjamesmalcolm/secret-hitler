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
	private Game game;

    public Board(Game game, Player... players) {
    	this.game = game;
        this.players = Arrays.asList(players);
    }

    public void place(Policy policy) throws Victory, PresidentialPower {
        policies.add(policy);
        long numberOfLiberalPolicies = policies.stream().filter(p -> p instanceof LiberalPolicy).count();
        if (numberOfLiberalPolicies == 5) {
            throw new LiberalsWin();
        }
        long numberOfFascistPolicies = policies.stream().filter(p -> p instanceof FascistPolicy).count();
        if (numberOfFascistPolicies == 6) {
            throw new FascistsWin();
        }
        if (numberOfFascistPolicies >= 4) {
            throw new Execution();
        }
        if (numberOfFascistPolicies == 3) {
            throw new SpecialElection();
        }
        if (players.size() >= 9) {
            throw new InvestigateLoyalty();
        }
        if (players.size() >= 7) {
            if (numberOfFascistPolicies == 2) {
                throw new InvestigateLoyalty();
            }
        }
        game.getPlayers().forEach(player -> {
        	player.removeTermLimit();
        });
        game.getPresident().ifPresent(president -> {
        	president.limitTerm();
        });
        game.getChancellor().ifPresent(chancellor -> {
        	chancellor.limitTerm();
        });
    }
}
