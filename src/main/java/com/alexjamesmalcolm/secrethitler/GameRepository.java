package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.game.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
}
