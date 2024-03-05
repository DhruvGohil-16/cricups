package com.example.api.service.Impl;

import com.example.api.entity.Match;
import com.example.api.repository.MatchRepo;
import com.example.api.service.MatchService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;

@Component    //specialization of @Component
public class MatchServiceImpl implements MatchService {

    private MatchRepo matchRepo;

    @Autowired
    public MatchServiceImpl(MatchRepo matchRepo) {
        this.matchRepo = matchRepo;
    }

    //  returns all the previous matches in our database
    @Override
    public List<Match> getAllMatches() {
        return this.matchRepo.findAll();
    }

    @Override
    public List<Match> getLiveMatches() {
        List<Match> matches = new ArrayList<>();
        try {
            String url = "https://www.cricbuzz.com/cricket-match/live-scores";
            Document document = Jsoup.connect(url).get();
            Elements liveScoreElements = document.select("div.cb-mtch-lst.cb-tms-itm");
            for (Element match : liveScoreElements) {
//                HashMap<String, String> RecentMatchInfo = new LinkedHashMap<>();
                String teamsHeading = match.select("h3.cb-lv-scr-mtch-hdr").select("a").text();
                String matchNumberVenue = match.select("span").text();
                Elements matchBatTeamInfo = match.select("div.cb-hmscg-bat-txt");
                String battingTeam = matchBatTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String score = matchBatTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                Elements bowlTeamInfo = match.select("div.cb-hmscg-bwl-txt");
                String bowlTeam = bowlTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String bowlTeamScore = bowlTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                String textLive = match.select("div.cb-text-live").text();
                String textComplete = "";

                //getting match link
                String matchLink = match.select("a.cb-lv-scrs-well.cb-lv-scrs-well-live").attr("href").toString();

                Match match1 = new Match();
                match1.setTeams(teamsHeading);
                match1.setMatchInfo(matchNumberVenue);
                match1.setTeam1(battingTeam);
                match1.setTeam1Score(score);
                match1.setTeam2(bowlTeam);
                match1.setTeam2Score(bowlTeamScore);
                match1.setLiveText(textLive);
                match1.setMatchLink(matchLink);
                match1.setTextComplete(textComplete);
                match1.setMatchStatus();
                match1.setMatchKey(teamsHeading+matchNumberVenue);

                matches.add(match1);

                //  update the match in database
                updateInDb(match1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }

    @Override
    public List<Match> getRecentMatches() {
        List<Match> matches = new ArrayList<>();
        try {
            String url = "https://www.cricbuzz.com/cricket-match/live-scores/recent-matches";
            System.out.println("inside try");
            Document document = Jsoup.connect(url).get();
            Elements RecentMatchElements = document.select("div.cb-mtch-lst.cb-tms-itm");
            for (Element match : RecentMatchElements) {

                HashMap<String, String> RecentMatchInfo = new LinkedHashMap<>();
                String teamsHeading = match.select("h3.cb-lv-scr-mtch-hdr").select("a").text();
                String matchNumberVenue = match.select("span").text();
                Elements matchBatTeamInfo = match.select("div.cb-hmscg-bat-txt");
                String battingTeam = matchBatTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String score = matchBatTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                Elements bowlTeamInfo = match.select("div.cb-hmscg-bwl-txt");
                String bowlTeam = bowlTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String bowlTeamScore = bowlTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                String textLive = "";
                String textComplete = match.select("div.cb-text-complete").text();

                //getting match link
                String matchLink = match.select("a.cb-lv-scrs-well.cb-lv-scrs-well-complete").attr("href").toString();

                Match match1 = new Match();
                match1.setTeams(teamsHeading);
                match1.setMatchInfo(matchNumberVenue);
                match1.setTeam1(battingTeam);
                match1.setTeam1Score(score);
                match1.setTeam2(bowlTeam);
                match1.setTeam2Score(bowlTeamScore);
                match1.setLiveText(textLive);
                match1.setMatchLink(matchLink);
                match1.setTextComplete(textComplete);
                match1.setMatchStatus();
                match1.setMatchKey(teamsHeading+matchNumberVenue);

                matches.add(match1);

                //  update the match in database
                updateInDb(match1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }

    @Override
    public List<Map<String, String>> getPointTable() {
        return null;
    }

    public void updateInDb(Match match1){
        Match match = this.matchRepo.findByTeams(match1.getMatchKey()).orElse(null);

        if(match==null){
            matchRepo.save(match1);
        }else{
            match1.setMatchId(match.getMatchId());
            matchRepo.save(match1);
        }
    }
}
