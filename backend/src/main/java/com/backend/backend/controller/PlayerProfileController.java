package com.backend.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.dto.PlayerProfileResponse;
import com.backend.backend.service.PlayerProfileService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/player")
public class PlayerProfileController {

    private final PlayerProfileService playerProfileService;

    public PlayerProfileController(
            PlayerProfileService playerProfileService) {

        this.playerProfileService = playerProfileService;
    }

    @GetMapping("/profile/{playerId}")
    public PlayerProfileResponse getPlayerProfile(
            @PathVariable Long playerId) {

        return playerProfileService.getPlayerProfile(playerId);
    }
}