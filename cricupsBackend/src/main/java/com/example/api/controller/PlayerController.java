package com.example.api.controller;

import com.example.api.entity.Player;
import com.example.api.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller //  specialization of @Controller (directly sends data in json format in http resp) : RESTful api
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
        System.out.println("playerController called");
    }

    @RequestMapping("/all-players")
    public ResponseEntity<List<Player>> getAllPlayers(){
        return new ResponseEntity<>(this.playerService.getPlayerInfo(),HttpStatus.OK);
    }
}
