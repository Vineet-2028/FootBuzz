package com.backend.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.GoalLeader;
import com.backend.backend.repository.GoalLeaderRepository;

@Service
public class GoalLeaderService {

    private final GoalLeaderRepository goalLeaderRepository;

    public GoalLeaderService(GoalLeaderRepository goalLeaderRepository) {
        this.goalLeaderRepository = goalLeaderRepository;
    }

    public List<GoalLeader> getAllGoalLeaders() {
        return goalLeaderRepository.findAll();
    }

    public List<GoalLeader> searchByLeague(String leagueName) {
        return goalLeaderRepository.findByLeagueNameContainingIgnoreCase(leagueName);
    }

    public List<GoalLeader> searchByPlayer(String playerName) {
        return goalLeaderRepository.findByPlayerNameContainingIgnoreCase(playerName);
    }

    public List<GoalLeader> searchByTeam(String teamName) {
        return goalLeaderRepository.findByTeamNameContainingIgnoreCase(teamName);
    }

    public List<GoalLeader> getGoalLeadersBySeason(String leagueName, Integer seasonYear) {
        return goalLeaderRepository
                .findByLeagueNameContainingIgnoreCaseAndSeasonYearOrderByRankNoAsc(
                        leagueName,
                        seasonYear
                );
    }
}