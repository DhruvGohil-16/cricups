package com.example.api.service;

import com.example.api.entity.Team;
import java.util.List;

public interface TeamService {

    //gives info of all players
    List<Team> getTeamInfo();

    Team findTeamByCountry(String country);
}
