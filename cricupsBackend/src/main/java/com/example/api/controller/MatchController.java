package com.example.api.controller;

import com.example.api.entity.Match;
import com.example.api.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller //  specialization of @Controller (directly sends data in json format in http resp) : RESTful api
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
//        this.matchService.updateLiveMatches();
//        this.matchService.updateRecentMatches();
    }

    @GetMapping("/live")
    public ResponseEntity<List<Match>> getLiveMatches(){
        System.out.println("getLiveMatches called");
        return new ResponseEntity<>(this.matchService.getLiveMatches(), HttpStatus.OK);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Match>> getRecentMatches(){
        System.out.println("getRecentMatches called");
        return new ResponseEntity<>(this.matchService.getRecentMatches(), HttpStatus.OK);
    }

    @RequestMapping("/all-matches")
    public ResponseEntity<List<Match>> getAllMatches(){
        return new ResponseEntity<>(this.matchService.getAllMatches(),HttpStatus.OK);
    }
}
