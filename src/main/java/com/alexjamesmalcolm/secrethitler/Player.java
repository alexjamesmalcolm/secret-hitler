package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.exceptions.GameNotStartedException;
import com.alexjamesmalcolm.secrethitler.exceptions.IdentityAlreadyAssigned;
import com.alexjamesmalcolm.secrethitler.exceptions.NotChancellorCannotPlacePolicies;
import com.alexjamesmalcolm.secrethitler.exceptions.NotOwnerOfPolicy;
import com.alexjamesmalcolm.secrethitler.events.presidentialpower.PresidentialPower;
import com.alexjamesmalcolm.secrethitler.events.victories.Victory;
import com.alexjamesmalcolm.secrethitler.policies.Policy;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Entity
public class Player {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return getId() == player.getId();
    }

    private long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String role;
    private String party;
    private boolean termLimited;
    @OneToMany(cascade = ALL)
    private List<Policy> hand;

    @ManyToOne
    private Game game;

    private Player() {}

    Player(long id) {
        this("Player with id " + id);
        this.id = id;
    }

    public Player(String name) {
        this.name = name;
        role = "";
        party = "";
    }

    public void setAsLiberal() throws IdentityAlreadyAssigned {
        if (!role.isEmpty()) {
            throw new IdentityAlreadyAssigned();
        }
        role = "liberal";
        party = "liberal";
    }

    public void setAsFascist() throws IdentityAlreadyAssigned {
        if (!role.isEmpty()) {
            throw new IdentityAlreadyAssigned();
        }
        role = "fascist";
        party = "fascist";
    }

    public void setAsHitler() throws IdentityAlreadyAssigned {
        if (!role.isEmpty()) {
            throw new IdentityAlreadyAssigned();
        }
        role = "hitler";
        party = "fascist";
    }

    public String getSecretRole() {
        return role;
    }

    public String getPartyMembership() {
        return party;
    }

    @Override
    public String toString() {
        return "Player@" + name;
    }

    public void limitTerm() {
        termLimited = true;
    }

    public boolean isTermLimited() {
        return termLimited;
    }

	public void removeTermLimit() {
		termLimited = false;
	}

    public List<Policy> getPolicyHand() {
        return hand;
    }

    public void giveCards(List<Policy> policies) {
        hand = new LinkedList<>(policies);
    }

    public void giveTwoCardsToChancellor(Policy firstPolicy, Policy secondPolicy) {
        Policy discardedPolicy = hand.stream().filter(policy -> !policy.equals(firstPolicy) && !policy.equals(secondPolicy)).findFirst().get();
        game.giveTwoCardsToChancellor(firstPolicy, secondPolicy, discardedPolicy);
        hand.clear();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void placeOnBoard(Policy policy) throws GameNotStartedException, Victory, PresidentialPower, NotOwnerOfPolicy, NotChancellorCannotPlacePolicies {
        if (!game.getChancellor().get().equals(this)) {
            throw new NotChancellorCannotPlacePolicies();
        }
        if (!hand.contains(policy)) {
            throw new NotOwnerOfPolicy();
        }
        game.getBoard().place(policy);
        hand.remove(policy);
        hand.forEach(p -> game.discardCard(p));
        hand.clear();
    }
}
