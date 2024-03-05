package com.example.api.repository;

import com.example.api.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TeamRepo extends JpaRepository<Team,String> {
    Optional<Team> findById(String teamKey);

    Optional<Team> findByTeamName(String teamName);
}
