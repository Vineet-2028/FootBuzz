package com.backend.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.Player;
import com.backend.backend.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    public List<Player> searchPlayersByName(String name) {
        return playerRepository.findByPlayerNameContainingIgnoreCase(name);
    }

    public List<Player> searchPlayersByClub(String club) {
        return playerRepository.findByCurrentClubNameContainingIgnoreCase(club);
    }

    public List<Player> searchPlayersByPosition(String position) {
        return playerRepository.findByPositionContainingIgnoreCase(position);
    }

    public Page<Player> getPlayersPaginated(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return playerRepository.findAll(pageable);
    }

    
}