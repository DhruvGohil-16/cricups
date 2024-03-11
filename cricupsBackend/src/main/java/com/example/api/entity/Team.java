package com.example.api.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team_info")
public class Team {

    @Id
    private String id;

    private String teamName;

    private String teamFlag;

    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<>();

    @OneToMany(mappedBy = "team1Id")
    @JsonIgnore
    private List<Match> match1 = new ArrayList<>();

    @OneToMany(mappedBy = "team2Id")
    @JsonIgnore
    private List<Match> match2 = new ArrayList<>();

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Match> getMatch1() {
        return match1;
    }

    public void setMatch1(List<Match> match1) {
        this.match1 = match1;
    }

    public List<Match> getMatch2() {
        return match2;
    }

    public void setMatch2(List<Match> match2) {
        this.match2 = match2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamFlag() {
        return teamFlag;
    }

    public void setTeamFlag(String teamFlag) {
        this.teamFlag = teamFlag;
    }
}
