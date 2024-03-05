package com.example.api.repository;

import com.example.api.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepo extends JpaRepository<Match,Integer> {

    Optional<Match> findByTeams(String teams);

}
