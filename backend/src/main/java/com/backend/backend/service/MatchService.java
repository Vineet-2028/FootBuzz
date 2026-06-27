package com.backend.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.backend.entity.Match;
import com.backend.backend.repository.MatchRepository;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Page<Match> getMatchesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return matchRepository.findAll(pageable);
    }

    public Match getMatchById(Long id) {
        return matchRepository.findByMatchId(id);
    }

    public List<Match> searchByClub(String clubName) {
        return matchRepository
                .findByHomeTeamContainingIgnoreCaseOrAwayTeamContainingIgnoreCase(
                        clubName,
                        clubName
                );
    }

    public List<Match> searchByLeague(String leagueName) {
        return matchRepository.findByLeagueNameContainingIgnoreCase(leagueName);
    }
}