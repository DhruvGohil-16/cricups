package com.example.api.service.Impl;

import com.example.api.entity.Team;
import com.example.api.repository.TeamRepo;
import com.example.api.service.TeamService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import java.util.ArrayList;
import java.util.List;

@Component
public class TeamServiceImpl implements TeamService {
    @Value("${cricapi.apikey}")
    private String apiKey;

    final private TeamRepo teamRepo;

    @Autowired
    public TeamServiceImpl(TeamRepo teamRepo){
        this.teamRepo = teamRepo;
    }

    @Override
    public List<Team> getTeamInfo() {

        String baseUrl = "https://api.cricapi.com/v1";
        String offset;

        List<Team> teamList = new ArrayList<>();
        WebClient webClient;

        try {
            webClient = WebClient.builder()
                    .baseUrl(baseUrl)
                    .defaultHeader("apikey", apiKey)
                    .build();
            for(int j=219;j<=250;j+=24){
                System.out.println("offset :"+j);
                offset=Integer.toString(j);

                String url = "/countries?apikey="+apiKey+"&offset="+offset;

                teamList = webClient.get()
                        .uri(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(String.class)
                        .flatMapMany(resp -> {
                            System.out.println("JSON Response: " + resp); // Print JSON response for debugging
                            List<Team> teams = new ArrayList<>();
                            JSONObject responseJson = new JSONObject(resp);
                            JSONArray jsonArray = responseJson.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Team team = new Team();
                                String name = jsonObject.getString("name");
                                String teamFlag = jsonObject.getString("genericFlag");
                                team.setId(jsonObject.getString("id"));
                                team.setTeamName(name);
                                team.setTeamFlag(teamFlag);
                                teams.add(team);
                            }
                            return Flux.fromIterable(teams);
                        })
                        .collectList()
                        .block(); // Wait for all teams to be emitted and collected into a list

                // Save teams to the database
                updateDbInfo(teamList);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        Flux<Team> teamFlux = Flux.fromIterable(teamList);

        return teamList;
    }
    @Override
    public Team findTeamByCountry(String country) {
        // Find team by country name
        return teamRepo.findByTeamName(country).orElse(null); // Assuming that the teamName field holds the country name
    }

    public void updateDbInfo(List<Team> teams){

        for (Team team : teams) {
        Team team1 = this.teamRepo.findById(team.getId()).orElse(null);
            if(team1==null){
                teamRepo.save(team);
            }else{
                System.out.println("Team Already exist");
                team.setId(team1.getId());
                teamRepo.save(team);
            }
        }
    }


}
