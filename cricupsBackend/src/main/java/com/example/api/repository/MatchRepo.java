package com.example.api.repository;

import com.example.api.entity.Match;
import com.example.api.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatchRepo extends JpaRepository<Match,Integer> {

    @Query("SELECT m FROM Match m WHERE m.matchKey = :matchKey")
    Optional<Match> findByMatchKey(@Param("matchKey") String matchKey);

    @Query("SELECT m FROM Match m WHERE m.status != 1")
    Optional<List<Match>> findLiveMatches();

    @Query("SELECT m FROM Match m WHERE m.status = 1 ORDER BY m.date ASC")
    List<Match> findRecentMatches();

}
