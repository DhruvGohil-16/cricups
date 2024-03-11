package com.example.api.service;

import com.example.api.entity.Match;

import java.util.List;
import java.util.Map;

public interface MatchService {

    //gives all the matches
    List<Match> getAllMatches();

    //gives all the live matches
    List<Match> getLiveMatches();


    //gives all the recent matches
    List<Match> getRecentMatches();

    void updateMatches();

    //gives the point table
    List<Map<String,String>> getPointTable();
}
