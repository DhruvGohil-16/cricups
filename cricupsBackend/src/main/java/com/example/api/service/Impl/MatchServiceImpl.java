package com.example.api.service.Impl;

import com.example.api.entity.Match;
import com.example.api.entity.Team;
import com.example.api.repository.MatchRepo;
import com.example.api.service.MatchService;
import com.example.api.service.TeamService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;
import java.util.*;

@Component    //specialization of @Component
public class MatchServiceImpl implements MatchService {

    private final MatchRepo matchRepo;
    private final TeamService teamService;

    @Autowired
    public MatchServiceImpl(MatchRepo matchRepo, TeamService teamService) {
        this.matchRepo = matchRepo;
        this.teamService = teamService;
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
                String matchInfo = match.select("span").text();
//                String matchDate = match.select("div.text-gray").text();
                Elements matchBatTeamInfo = match.select("div.cb-hmscg-bat-txt");
                String battingTeam = matchBatTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String score = matchBatTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                Elements bowlTeamInfo = match.select("div.cb-hmscg-bwl-txt");
                String bowlTeam = bowlTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String bowlTeamScore = bowlTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                String textLive = match.select("div.cb-text-live").text();
                String textComplete = match.select("div.cb-text-complete").text();

                String[] parts = teamsHeading.split("vs");
                String firstTeam = parts[0].trim();
                String secondTeam = parts[1].trim();
                if(secondTeam.contains(","))
                    secondTeam = secondTeam.substring(0, secondTeam.length() - 1);

                System.out.println("first team name : "+firstTeam);
                System.out.println("second team name : "+secondTeam);

                Team team1 = teamService.findByTeamName(firstTeam);
                Team team2 = teamService.findByTeamName(secondTeam);

                if(team1==null || team2==null)
                    continue;
                System.out.println("first team : "+team1);
                System.out.println("second team : "+team2);

                //getting match link
                String matchLink = match.select("a.cb-lv-scrs-well.cb-lv-scrs-well-live").attr("href").toString();

                Match match1 = new Match();
                match1.setTeams(teamsHeading);
                match1.setMatchInfo(matchInfo);
                match1.setTeam1(battingTeam);
                match1.setTeam1Score(score);
                match1.setTeam2(bowlTeam);
                match1.setTeam2Score(bowlTeamScore);
                match1.setLiveText(textLive);
                match1.setMatchLink(matchLink);
                match1.setTextComplete(textComplete);
                match1.setMatchStatus();
                match1.setTeam1Id(team1);
                match1.setTeam2Id(team2);
                match1.setMatchKey(teamsHeading+matchInfo);

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
            // Set the path to chromedriver executable
            System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver-win64\\chromedriver.exe");
            // Initialize ChromeDriver
            WebDriver driver = new ChromeDriver();

            // Navigate to the webpage
            driver.get("https://www.cricbuzz.com/cricket-match/live-scores/recent-matches");

            // Find all match elements
            List<WebElement> matchElements = driver.findElements(By.cssSelector("div.cb-mtch-lst.cb-tms-itm"));

            for (WebElement matchElement : matchElements) {

                Match match = new Match();

                // Extracting teams heading
                String teamsHeading = matchElement.findElement(By.cssSelector("h3.cb-lv-scr-mtch-hdr > a")).getText();
                String matchInfo = matchElement.findElement(By.cssSelector("div.text-gray")).getText();
                String textComplete = matchElement.findElement(By.cssSelector("div.cb-text-complete")).getText();
                String matchLink = matchElement.findElement(By.cssSelector("a.cb-lv-scrs-well.cb-lv-scrs-well-complete")).getAttribute("href");

                String[] parts;
                if (teamsHeading.contains("vs")) {
                    parts = teamsHeading.split("vs");
                } else {
                    // Handle the case when "vs" is not found
                    // For example, set both teams as unknown
                    parts = new String[]{"Unknown", "Unknown"};
                }
                String firstTeam = parts[0].trim();
                String secondTeam = parts.length > 1 ? parts[1].trim() : "Unknown";
                if (secondTeam.contains(","))
                    secondTeam = secondTeam.substring(0, secondTeam.length() - 1);

                // Setting teams
                Team team1 = teamService.findByTeamName(firstTeam);
                Team team2 = teamService.findByTeamName(secondTeam);
                if (team1 == null || team2 == null) {
                    continue;
                }

                String batTeam = team1.getTeamName();
                String bowlTeam = team2.getTeamName();

                match.setMatchLink(matchLink);
                match.setTextComplete(textComplete);
                match.setMatchStatus();
                match.setMatchKey(teamsHeading + matchInfo);
                match.setTeam1Id(team1);
                match.setTeam2Id(team2);
                match.setTeams(teamsHeading);
                match.setMatchInfo(matchInfo);
                match.setTeam1(batTeam);
                match.setTeam2(bowlTeam);

                if(!textComplete.contains("won")){
                    match.setTeam1Score("");
                    match.setTeam2Score("");

                    matches.add(match);
                    System.out.println(match);
                    //  update the match in database
                    updateInDb(match);
                    continue;
                }
                // Extracting other match information
                String batTeamScore = matchElement.findElement(By.cssSelector("div.cb-hmscg-bat-txt > div.cb-hmscg-tm-nm+div")).getText();
                String bowlTeamScore = matchElement.findElement(By.cssSelector("div.cb-hmscg-bwl-txt > div.cb-hmscg-tm-nm+div")).getText();

                match.setTeam1Score(batTeamScore);
                match.setTeam2Score(bowlTeamScore);

                matches.add(match);

                //  update the match in database
                updateInDb(match);
            }

            // Close the WebDriver
            driver.quit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }

    @Override
    public List<Map<String, String>> getPointTable() {
        return null;
    }

    public void updateInDb(Match match1){
        Match match = this.matchRepo.findByMatchKey(match1.getMatchKey()).orElse(null);

        if(match==null){
            matchRepo.save(match1);
        }else{
            match1.setMatchId(match.getMatchId());
            matchRepo.save(match1);
        }
    }
}
