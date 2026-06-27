package com.backend.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.entity.LeagueTable;
import com.backend.backend.service.LeagueTableService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class LeagueTableController {

    private final LeagueTableService leagueTableService;

    public LeagueTableController(LeagueTableService leagueTableService) {
        this.leagueTableService = leagueTableService;
    }

    @GetMapping("/league-tables")
    public List<LeagueTable> getAllTables() {
        return leagueTableService.getAllTables();
    }

    @GetMapping("/league-tables/league")
    public List<LeagueTable> getByLeague(@RequestParam String league) {
        return leagueTableService.searchByLeague(league);
    }

    @GetMapping("/league-tables/team")
    public List<LeagueTable> getByTeam(@RequestParam String team) {
        return leagueTableService.searchByTeam(team);
    }

    @GetMapping("/league-tables/season")
    public List<LeagueTable> getLeagueTableBySeason(
            @RequestParam String league,
            @RequestParam Integer year
    ) {
        return leagueTableService.getLeagueTableBySeason(league, year);
    }
}