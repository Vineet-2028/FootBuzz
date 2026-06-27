package com.backend.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.entity.TeamDiscipline;

public interface TeamDisciplineRepository extends JpaRepository<TeamDiscipline, String> {

    List<TeamDiscipline> findByLeagueNameContainingIgnoreCase(String leagueName);

    List<TeamDiscipline> findByTeamNameContainingIgnoreCase(String teamName);

    List<TeamDiscipline> findByLeagueNameContainingIgnoreCaseAndSeasonYearOrderByRankNoAsc(
            String leagueName,
            Integer seasonYear
    );
}