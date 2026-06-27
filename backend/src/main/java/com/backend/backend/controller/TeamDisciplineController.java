package com.backend.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.entity.TeamDiscipline;
import com.backend.backend.service.TeamDisciplineService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class TeamDisciplineController {

    private final TeamDisciplineService teamDisciplineService;

    public TeamDisciplineController(TeamDisciplineService teamDisciplineService) {
        this.teamDisciplineService = teamDisciplineService;
    }

    @GetMapping("/team-discipline")
    public List<TeamDiscipline> getAllTeamDiscipline() {
        return teamDisciplineService.getAllTeamDiscipline();
    }

    @GetMapping("/team-discipline/league")
    public List<TeamDiscipline> getByLeague(@RequestParam String league) {
        return teamDisciplineService.searchByLeague(league);
    }

    @GetMapping("/team-discipline/team")
    public List<TeamDiscipline> getByTeam(@RequestParam String team) {
        return teamDisciplineService.searchByTeam(team);
    }

    @GetMapping("/team-discipline/season")
    public List<TeamDiscipline> getBySeason(
            @RequestParam String league,
            @RequestParam Integer year
    ) {
        return teamDisciplineService.getBySeason(league, year);
    }
}