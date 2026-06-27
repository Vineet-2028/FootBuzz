package com.backend.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.entity.LeagueTable;

public interface LeagueTableRepository extends JpaRepository<LeagueTable, String> {

    List<LeagueTable> findByLeagueNameContainingIgnoreCase(String leagueName);

    List<LeagueTable> findByTeamNameContainingIgnoreCase(String teamName);

    List<LeagueTable> findByLeagueNameContainingIgnoreCaseAndSeasonYearOrderByPlaceAsc(
            String leagueName,
            Integer seasonYear
    );
}