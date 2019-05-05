package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.game.Game;
import com.alexjamesmalcolm.secrethitler.game.Player;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller("/out")
public class GameSocketController {

    @Resource
    private GameRepository gameRepo;

    @MessageMapping("/start-game")
    @SendTo("/game")
    public Game startGame(Player player) {
        Game game = new Game(player);
        game = gameRepo.save(game);
        return game;
    }
}
