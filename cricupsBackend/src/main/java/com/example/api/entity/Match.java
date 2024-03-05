package com.example.api.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cric_matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int matchId;

    private String matchKey;

    private String teams;

    private String matchInfo;

    private String team1;

    private String team1Score;

    private String team2;

    private String team2Score;

    private String liveText;

    private String textComplete;


    private String matchLink;

    @Enumerated
    private MatchStatus status;

    private Date date = new Date();

//    public Match(int matchId, String teams, String matchInfo, String team1, String team1Score, String team2, String team2Score, String liveText, String textComplete, String matchLink,MatchStatus status, Date date) {
//        this.matchId = matchId;
//        this.matchKey = teams+matchInfo;
//        this.teams = teams;
//        this.matchInfo = matchInfo;
//        this.team1 = team1;
//        this.team1Score = team1Score;
//        this.team2 = team2;
//        this.team2Score = team2Score;
//        this.liveText = liveText;
//        this.textComplete = textComplete;
//        this.status = status;
//        this.date = date;
//        this.matchLink=matchLink;
//    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getMatchKey() {
        return matchKey;
    }

    public void setMatchKey(String matchKey) {
        this.matchKey = matchKey;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public String getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(String matchInfo) {
        this.matchInfo = matchInfo;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(String team1Score) {
        this.team1Score = team1Score;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(String team2Score) {
        this.team2Score = team2Score;
    }

    public String getLiveText() {
        return liveText;
    }

    public void setLiveText(String liveText) {
        this.liveText = liveText;
    }

    public String getTextComplete() {
        return textComplete;
    }

    public void setTextComplete(String textComplete) {
        this.textComplete = textComplete;
    }

    public String getMatchLink() {
        return matchLink;
    }

    public void setMatchLink(String matchLink) {
        this.matchLink = matchLink;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setMatchStatus(){
        if(textComplete.isBlank()){
            this.status=MatchStatus.LIVE;
        }
        else{
            this.status=MatchStatus.COMPLETED;
        }
    }
    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}