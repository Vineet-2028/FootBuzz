package com.backend.backend.controller;

import com.backend.backend.entity.PlayerNationalPerformance;
import com.backend.backend.service.PlayerNationalPerformanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/national-performance")
public class PlayerNationalPerformanceController {

    private final PlayerNationalPerformanceService playerNationalPerformanceService;

    public PlayerNationalPerformanceController(PlayerNationalPerformanceService playerNationalPerformanceService) {
        this.playerNationalPerformanceService = playerNationalPerformanceService;
    }

    @GetMapping("/team/{teamId}")
    public List<PlayerNationalPerformance> getNationalPerformanceByTeamId(@PathVariable Long teamId) {
        return playerNationalPerformanceService.searchByTeamId(teamId);
    }

    @GetMapping("/player/{playerId}")
    public List<PlayerNationalPerformance> getNationalPerformanceByPlayerId(@PathVariable Long playerId) {
        return playerNationalPerformanceService.searchByPlayerId(playerId);
    }
}