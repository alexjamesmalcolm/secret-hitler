package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.game.Game;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @MessageMapping("/")
    @SendTo("/topic/game")
    public Game receiveUserDesicion(Decision decision) {
        return null;
    }
}
