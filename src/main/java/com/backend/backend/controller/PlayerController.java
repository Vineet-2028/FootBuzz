package com.backend.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.entity.Player;
import com.backend.backend.service.PlayerService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public Page<Player> getAllPlayers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return playerService.getPlayersPaginated(page, size);
    }

    @GetMapping("/players/{id}")
    public Player getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @GetMapping("/players/search")
    public List<Player> searchPlayers(@RequestParam String name) {
        return playerService.searchPlayersByName(name);
    }

    @GetMapping("/players/club")
    public List<Player> searchByClub(@RequestParam String club) {
        return playerService.searchPlayersByClub(club);
    }

    @GetMapping("/players/position")
    public List<Player> searchByPosition(@RequestParam String position) {
        return playerService.searchPlayersByPosition(position);
    }
}