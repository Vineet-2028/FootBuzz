package com.backend.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.AssistLeader;
import com.backend.backend.repository.AssistLeaderRepository;

@Service
public class AssistLeaderService {

    private final AssistLeaderRepository assistLeaderRepository;

    public AssistLeaderService(AssistLeaderRepository assistLeaderRepository) {
        this.assistLeaderRepository = assistLeaderRepository;
    }

    public List<AssistLeader> getAllAssistLeaders() {
        return assistLeaderRepository.findAll();
    }

    public List<AssistLeader> searchByLeague(String leagueName) {
        return assistLeaderRepository.findByLeagueNameContainingIgnoreCase(leagueName);
    }

    public List<AssistLeader> searchByPlayer(String playerName) {
        return assistLeaderRepository.findByPlayerNameContainingIgnoreCase(playerName);
    }

    public List<AssistLeader> searchByTeam(String teamName) {
        return assistLeaderRepository.findByTeamNameContainingIgnoreCase(teamName);
    }

    public List<AssistLeader> getAssistLeadersBySeason(String leagueName, Integer seasonYear) {
        return assistLeaderRepository
                .findByLeagueNameContainingIgnoreCaseAndSeasonYearOrderByRankNoAsc(
                        leagueName,
                        seasonYear
                );
    }
}