package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.throwable.events.presidentialpower.Execution;
import com.alexjamesmalcolm.secrethitler.throwable.events.presidentialpower.InvestigateLoyalty;
import com.alexjamesmalcolm.secrethitler.throwable.events.presidentialpower.PresidentialPower;
import com.alexjamesmalcolm.secrethitler.throwable.events.presidentialpower.SpecialElection;
import com.alexjamesmalcolm.secrethitler.throwable.events.victories.FascistsWin;
import com.alexjamesmalcolm.secrethitler.throwable.events.victories.LiberalsWin;
import com.alexjamesmalcolm.secrethitler.throwable.events.victories.Victory;
import com.alexjamesmalcolm.secrethitler.game.Game;
import com.alexjamesmalcolm.secrethitler.game.Player;
import com.alexjamesmalcolm.secrethitler.policies.FascistPolicy;
import com.alexjamesmalcolm.secrethitler.policies.LiberalPolicy;
import com.alexjamesmalcolm.secrethitler.policies.Policy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
public class Board {

    @Id
    private long id;

    @OneToMany
    private Collection<Player> players;
    @OneToMany
    private List<Policy> policies = new ArrayList<>();
    @OneToOne
	private Game game;

    private Board() {}

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

    public List<Policy> getPolicies() {
        return policies;
    }
}
