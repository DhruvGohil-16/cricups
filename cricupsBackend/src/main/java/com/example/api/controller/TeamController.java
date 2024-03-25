package com.example.api.controller;

import com.example.api.entity.Team;
import com.example.api.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller //  specialization of @Controller (directly sends data in json format in http resp) : RESTful api
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
        System.out.println("teamController called");
    }

    @RequestMapping("/all-teams")
    public ResponseEntity<List<Team>> getAllTeams(){
        return new ResponseEntity<>(this.teamService.getTeamInfo(),HttpStatus.OK);
    }
}
