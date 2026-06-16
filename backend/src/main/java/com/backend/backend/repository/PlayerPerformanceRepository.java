package com.backend.backend.repository;

import com.backend.backend.entity.PlayerPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlayerPerformanceRepository extends JpaRepository<PlayerPerformance, Long> {

    List<PlayerPerformance> findByPlayerId(Long playerId);

    List<PlayerPerformance> findByTeamNameIgnoreCase(String teamName);

    List<PlayerPerformance> findByCompetitionId(String competitionId);

    
}