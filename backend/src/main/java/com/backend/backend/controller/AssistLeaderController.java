package com.backend.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.entity.AssistLeader;
import com.backend.backend.service.AssistLeaderService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class AssistLeaderController {

    private final AssistLeaderService assistLeaderService;

    public AssistLeaderController(AssistLeaderService assistLeaderService) {
        this.assistLeaderService = assistLeaderService;
    }

    @GetMapping("/assist-leaders")
    public List<AssistLeader> getAllAssistLeaders() {
        return assistLeaderService.getAllAssistLeaders();
    }

    @GetMapping("/assist-leaders/league")
    public List<AssistLeader> getByLeague(@RequestParam String league) {
        return assistLeaderService.searchByLeague(league);
    }

    @GetMapping("/assist-leaders/player")
    public List<AssistLeader> getByPlayer(@RequestParam String player) {
        return assistLeaderService.searchByPlayer(player);
    }

    @GetMapping("/assist-leaders/team")
    public List<AssistLeader> getByTeam(@RequestParam String team) {
        return assistLeaderService.searchByTeam(team);
    }

    @GetMapping("/assist-leaders/season")
    public List<AssistLeader> getBySeason(
            @RequestParam String league,
            @RequestParam Integer year
    ) {
        return assistLeaderService.getAssistLeadersBySeason(league, year);
    }
}