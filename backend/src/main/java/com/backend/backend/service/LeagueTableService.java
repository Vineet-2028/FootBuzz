package com.backend.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.LeagueTable;
import com.backend.backend.repository.LeagueTableRepository;

@Service
public class LeagueTableService {

    private final LeagueTableRepository leagueTableRepository;

    public LeagueTableService(LeagueTableRepository leagueTableRepository) {
        this.leagueTableRepository = leagueTableRepository;
    }

    public List<LeagueTable> getAllTables() {
        return leagueTableRepository.findAll();
    }

    public List<LeagueTable> searchByLeague(String leagueName) {
        return leagueTableRepository.findByLeagueNameContainingIgnoreCase(leagueName);
    }

    public List<LeagueTable> searchByTeam(String teamName) {
        return leagueTableRepository.findByTeamNameContainingIgnoreCase(teamName);
    }

    public List<LeagueTable> getLeagueTableBySeason(String leagueName, Integer seasonYear) {
        return leagueTableRepository
                .findByLeagueNameContainingIgnoreCaseAndSeasonYearOrderByPlaceAsc(
                        leagueName,
                        seasonYear
                );
    }
}