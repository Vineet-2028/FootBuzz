package com.backend.backend.repository;

import com.backend.backend.entity.PlayerNationalPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlayerNationalPerformanceRepository
        extends JpaRepository<PlayerNationalPerformance, Long> {

    List<PlayerNationalPerformance> findByPlayerId(Long playerId);

    List<PlayerNationalPerformance> findByTeamId(Long teamId);

}