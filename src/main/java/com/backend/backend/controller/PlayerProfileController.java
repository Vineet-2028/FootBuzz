package com.backend.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.backend.backend.dto.PlayerProfileResponse;
import com.backend.backend.service.PlayerProfileService;

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