package com.example.api.repository;

import com.example.api.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeamRepo extends JpaRepository<Team,String> {
    Optional<Team> findById(String teamKey);

    @Query("SELECT t FROM Team t WHERE LOWER(t.teamName) = LOWER(:teamName)")
    Optional<Team> findByTeamName(@Param("teamName") String teamName);
}
