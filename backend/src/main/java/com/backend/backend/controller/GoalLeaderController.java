package com.backend.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.entity.GoalLeader;
import com.backend.backend.service.GoalLeaderService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class GoalLeaderController {

    private final GoalLeaderService goalLeaderService;

    public GoalLeaderController(GoalLeaderService goalLeaderService) {
        this.goalLeaderService = goalLeaderService;
    }

    @GetMapping("/goal-leaders")
    public List<GoalLeader> getAllGoalLeaders() {
        return goalLeaderService.getAllGoalLeaders();
    }

    @GetMapping("/goal-leaders/league")
    public List<GoalLeader> getByLeague(@RequestParam String league) {
        return goalLeaderService.searchByLeague(league);
    }

    @GetMapping("/goal-leaders/player")
    public List<GoalLeader> getByPlayer(@RequestParam String player) {
        return goalLeaderService.searchByPlayer(player);
    }

    @GetMapping("/goal-leaders/team")
    public List<GoalLeader> getByTeam(@RequestParam String team) {
        return goalLeaderService.searchByTeam(team);
    }

    @GetMapping("/goal-leaders/season")
    public List<GoalLeader> getBySeason(
            @RequestParam String league,
            @RequestParam Integer year
    ) {
        return goalLeaderService.getGoalLeadersBySeason(league, year);
    }
}