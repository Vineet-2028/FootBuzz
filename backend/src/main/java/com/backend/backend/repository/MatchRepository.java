package com.backend.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.entity.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Match findByMatchId(Long matchId);

    List<Match> findByLeagueNameContainingIgnoreCase(String leagueName);

    List<Match> findByHomeTeamContainingIgnoreCaseOrAwayTeamContainingIgnoreCase(
            String homeTeam,
            String awayTeam
    );
}