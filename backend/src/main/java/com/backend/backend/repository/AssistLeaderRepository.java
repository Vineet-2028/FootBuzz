package com.backend.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.entity.AssistLeader;

public interface AssistLeaderRepository extends JpaRepository<AssistLeader, String> {

    List<AssistLeader> findByLeagueNameContainingIgnoreCase(String leagueName);

    List<AssistLeader> findByPlayerNameContainingIgnoreCase(String playerName);

    List<AssistLeader> findByTeamNameContainingIgnoreCase(String teamName);

    List<AssistLeader> findByLeagueNameContainingIgnoreCaseAndSeasonYearOrderByRankNoAsc(
            String leagueName,
            Integer seasonYear
    );
}