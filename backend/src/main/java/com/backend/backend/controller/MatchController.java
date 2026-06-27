package com.backend.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.entity.Match;
import com.backend.backend.service.MatchService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/matches")
    public Page<Match> getAllMatches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return matchService.getMatchesPaginated(page, size);
    }

    @GetMapping("/matches/{id}")
    public Match getMatchById(@PathVariable Long id) {
        return matchService.getMatchById(id);
    }

    @GetMapping("/matches/club")
    public List<Match> getMatchesByClub(@RequestParam String name) {
        return matchService.searchByClub(name);
    }

    @GetMapping("/matches/league")
    public List<Match> getMatchesByLeague(@RequestParam String league) {
        return matchService.searchByLeague(league);
    }
}