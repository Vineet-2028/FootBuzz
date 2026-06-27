package com.backend.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.entity.GoalLeader;

public interface GoalLeaderRepository extends JpaRepository<GoalLeader, String> {

    List<GoalLeader> findByLeagueNameContainingIgnoreCase(String leagueName);

    List<GoalLeader> findByPlayerNameContainingIgnoreCase(String playerName);

    List<GoalLeader> findByTeamNameContainingIgnoreCase(String teamName);

    List<GoalLeader> findByLeagueNameContainingIgnoreCaseAndSeasonYearOrderByRankNoAsc(
            String leagueName,
            Integer seasonYear
    );
}