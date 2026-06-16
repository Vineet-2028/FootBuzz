package com.backend.backend.controller;

import com.backend.backend.entity.PlayerPerformance;
import com.backend.backend.service.PlayerPerformanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performance")
public class PlayerPerformanceController {

    private final PlayerPerformanceService playerPerformanceService;

    public PlayerPerformanceController(PlayerPerformanceService playerPerformanceService) {
        this.playerPerformanceService = playerPerformanceService;
    }

    @GetMapping("/team/{teamName}")
    public List<PlayerPerformance> getPerformanceByTeamName(@PathVariable String teamName) {
        return playerPerformanceService.searchPerformanceByName(teamName);
    }

    @GetMapping("/competition/{competitionId}")
    public List<PlayerPerformance> getPerformanceByCompetitionId(@PathVariable String competitionId) {
        return playerPerformanceService.findByCompetitionId(competitionId);
    }
}