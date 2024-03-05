package com.example.api.entity;


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
