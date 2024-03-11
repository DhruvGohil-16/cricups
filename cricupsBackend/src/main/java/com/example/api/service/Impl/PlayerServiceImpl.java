package com.example.api.service.Impl;

import com.example.api.entity.Player;
import com.example.api.entity.Team;
import com.example.api.repository.PlayerRepo;
import com.example.api.service.PlayerService;
import com.example.api.service.TeamService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PlayerServiceImpl implements PlayerService {

    @Value("${cricapi.apikey}")
    private String apiKey;

    final private PlayerRepo playerRepo;

    final private TeamService teamService;

    @Autowired
    public PlayerServiceImpl(PlayerRepo playerRepo, TeamService teamService){

        this.playerRepo = playerRepo;
        this.teamService = teamService;
    }

    @Override
    public List<Player> getPlayerInfo() {

        String baseUrl = "https://api.cricapi.com/v1";
        String offset = "0";
        String url = "/players?apikey="+apiKey+"&offset="+offset;

        List<Player> playerList = new ArrayList<>();
        WebClient webClient;

        try {
            webClient = WebClient.builder()
                    .baseUrl(baseUrl)
                    .defaultHeader("apikey", apiKey)
                    .build();


            playerList = webClient.get()
                    .uri(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMapMany(resp -> {
                        System.out.println("JSON Response: " + resp); // Print JSON response for debugging
                        List<Player> players = new ArrayList<>();
                        JSONObject responseJson = new JSONObject(resp);
                        JSONArray jsonArray = responseJson.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Player player = new Player();
                            String name = jsonObject.getString("name");
                            String country = jsonObject.getString("country");
                            player.setId(jsonObject.getString("id"));
                            if(name.isBlank() || country.isBlank())
                                continue;
                            player.setName(name);
                            player.setCountry(country);
                            players.add(player);
                        }
                        return Flux.fromIterable(players);
                    })
                    .collectList()
                    .block(); // Wait for all players to be emitted and collected into a list

            // Save players to the database
            playerList = updateDbInfo(webClient,playerList);

        }catch (Exception e){
            e.printStackTrace();
        }

        Flux<Player> playerFlux = Flux.fromIterable(playerList);

        return playerList;
    }

    public List<Player> updateDbInfo(WebClient webClient, List<Player> players) {
        String id;
        List<Player> playerList = new ArrayList<>();
        for (Player player : players) {
            try {
                // Check if player exists in the database using getPlayerKey method
                Player player1 = this.playerRepo.findById(player.getId()).orElse(null);
                id = player.getId();
                String url = "/players_info?apikey="+apiKey+"&id="+id;
                Mono<Player> player2 = webClient.get()
                        .uri(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(String.class)
                        .flatMap(resp -> {
                                System.out.println(resp);
                                Player player3 = new Player();
                                JSONObject jsonObject = new JSONObject(resp);
                                JSONObject data = jsonObject.getJSONObject("data");
                                player3.setId(data.getString("id"));
                                player3.setName(data.getString("name"));
                                if (data.has("dateOfBirth")) {
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                    Date dateOfBirth;
                                    try {
                                        dateOfBirth = dateFormat.parse(data.getString("dateOfBirth"));
                                    } catch (ParseException e) {
                                        return Mono.error(new RuntimeException(e));
                                    }
                                    player3.setDateOfBirth(dateOfBirth);
                                }
                                if (data.has("role"))
                                    player3.setRole(data.getString("role"));
                                if (data.has("battingStyle"))
                                    player3.setBattingStyle(data.getString("battingStyle"));
                                if (data.has("bowlingStyle"))
                                    player3.setBowlingStyle(data.getString("bowlingStyle"));
                                if (data.has("placeOfBirth"))
                                    player3.setPlaceOfBirth(data.getString("placeOfBirth"));
                                Team team = teamService.findByTeamName(data.getString("country"));
                                if (team == null) {
                                    System.out.println("team not found");
                                }
                                player3.setTeam(team);
                                player3.setCountry(data.getString("country"));
                                return Mono.just(player3);
                        });

                    player1 = player2.block();
                    playerList.add(player1);
                    playerRepo.save(player1);

            } catch (Exception e) {
                // Handle any errors that occur during the process
                System.err.println("Error updating player: " + e.getMessage());
            }
        }
        return playerList;
    }
}
