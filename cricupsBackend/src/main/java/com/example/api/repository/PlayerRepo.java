package com.example.api.repository;

import com.example.api.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlayerRepo extends JpaRepository<Player,String> {
    Optional<Player> findById(String playerKey);
}
