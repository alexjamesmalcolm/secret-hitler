package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.game.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
}
