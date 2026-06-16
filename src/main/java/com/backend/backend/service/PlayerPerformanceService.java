package com.backend.backend.service;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.PlayerPerformance;
import com.backend.backend.repository.PlayerPerformanceRepository;

import java.util.List;

@Service
public class PlayerPerformanceService {
    
    private final PlayerPerformanceRepository playerPerformanceRepository;

    public PlayerPerformanceService(PlayerPerformanceRepository playerPerformanceRepository){
        this.playerPerformanceRepository = playerPerformanceRepository;
    }

    public List<PlayerPerformance> getAllPerformances(){
        return playerPerformanceRepository.findAll();
    }

    public List<PlayerPerformance> getPlayerPerformance(Long playerId) {
        return playerPerformanceRepository.findByPlayerId(playerId);
    }

    public List<PlayerPerformance> searchPerformanceByName(String teamName) {
        return playerPerformanceRepository.findByTeamNameIgnoreCase(teamName);
    }

    public List<PlayerPerformance> findByCompetitionId(String competitionId) {
        return playerPerformanceRepository.findByCompetitionId(competitionId);
    }
}
