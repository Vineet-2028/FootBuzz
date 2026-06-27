package com.backend.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.TeamDiscipline;
import com.backend.backend.repository.TeamDisciplineRepository;

@Service
public class TeamDisciplineService {

    private final TeamDisciplineRepository teamDisciplineRepository;

    public TeamDisciplineService(TeamDisciplineRepository teamDisciplineRepository) {
        this.teamDisciplineRepository = teamDisciplineRepository;
    }

    public List<TeamDiscipline> getAllTeamDiscipline() {
        return teamDisciplineRepository.findAll();
    }

    public List<TeamDiscipline> searchByLeague(String leagueName) {
        return teamDisciplineRepository.findByLeagueNameContainingIgnoreCase(leagueName);
    }

    public List<TeamDiscipline> searchByTeam(String teamName) {
        return teamDisciplineRepository.findByTeamNameContainingIgnoreCase(teamName);
    }

    public List<TeamDiscipline> getBySeason(String leagueName, Integer seasonYear) {
        return teamDisciplineRepository
                .findByLeagueNameContainingIgnoreCaseAndSeasonYearOrderByRankNoAsc(
                        leagueName,
                        seasonYear
                );
    }
}