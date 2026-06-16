package com.backend.backend.service;

import org.springframework.stereotype.Service;

import com.backend.backend.repository.PlayerNationalPerformanceRepository;
import com.backend.backend.entity.PlayerNationalPerformance;
import java.util.List;

@Service
public class PlayerNationalPerformanceService {
    
    private final PlayerNationalPerformanceRepository playerNationalPerformanceRepository;

    public PlayerNationalPerformanceService(PlayerNationalPerformanceRepository playerNationalPerformanceRepository){
        this.playerNationalPerformanceRepository = playerNationalPerformanceRepository;
    }

    public List<PlayerNationalPerformance> getAllNationalPerformances(){
        return playerNationalPerformanceRepository.findAll();
    }

    public List<PlayerNationalPerformance> searchByTeamId(Long teamId){
        return playerNationalPerformanceRepository.findByTeamId(teamId);
    }
    
    public List<PlayerNationalPerformance> searchByPlayerId(Long playerId){
        return playerNationalPerformanceRepository.findByPlayerId(playerId);
    }
}
