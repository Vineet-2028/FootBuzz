package com.backend.backend.dto;

import java.util.List;

import com.backend.backend.entity.Player;
import com.backend.backend.entity.PlayerPerformance;
import com.backend.backend.entity.PlayerNationalPerformance;

public class PlayerProfileResponse {

    private Player player;

    private List<PlayerPerformance> clubPerformance;

    private List<PlayerNationalPerformance> nationalPerformance;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<PlayerPerformance> getClubPerformance() {
        return clubPerformance;
    }

    public void setClubPerformance(List<PlayerPerformance> clubPerformance) {
        this.clubPerformance = clubPerformance;
    }

    public List<PlayerNationalPerformance> getNationalPerformance() {
        return nationalPerformance;
    }

    public void setNationalPerformance(
            List<PlayerNationalPerformance> nationalPerformance) {
        this.nationalPerformance = nationalPerformance;
    }
}