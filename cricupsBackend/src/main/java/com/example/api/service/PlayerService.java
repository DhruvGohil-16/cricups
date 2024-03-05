package com.example.api.service;

import com.example.api.entity.Player;
import java.util.List;

public interface PlayerService {

    //gives info of all players
    List<Player> getPlayerInfo();
}
