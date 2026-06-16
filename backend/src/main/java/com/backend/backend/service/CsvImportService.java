package com.backend.backend.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.Player;
import com.backend.backend.entity.PlayerPerformance;
import com.backend.backend.entity.PlayerNationalPerformance;
import com.backend.backend.repository.PlayerNationalPerformanceRepository;
import com.backend.backend.repository.PlayerPerformanceRepository;
import com.backend.backend.repository.PlayerRepository;
import com.opencsv.CSVReader;

@Service
public class CsvImportService {

    private final PlayerRepository playerRepository;
    private final PlayerPerformanceRepository playerPerformanceRepository;
    private final PlayerNationalPerformanceRepository playerNationalPerformanceRepository;

    public CsvImportService(
            PlayerRepository playerRepository,
            PlayerPerformanceRepository playerPerformanceRepository,
            PlayerNationalPerformanceRepository playerNationalPerformanceRepository) {

        this.playerRepository = playerRepository;
        this.playerPerformanceRepository = playerPerformanceRepository;
        this.playerNationalPerformanceRepository = playerNationalPerformanceRepository;
    }

    public void importPlayers() {

        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/player_profile.csv");

            if (inputStream == null) {
                System.out.println("CSV file not found");
                return;
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

                String[] row;
                reader.readNext();

                List<Player> players = new ArrayList<>();

                while ((row = reader.readNext()) != null) {

                    Player player = new Player();

                    player.setPlayer_id(parseLong(row[0]));
                    player.setPlayer_name(row[1]);
                    player.setPlayer_image_url(row[2]);
                    player.setDate_of_birth(parseDate(row[3]));
                    player.setAge(parseInteger(row[4]));
                    player.setPlace_of_birth(row[5]);
                    player.setCountry_of_birth(row[6]);
                    player.setHeight(parseDouble(row[7]));
                    player.setCitizenship(row[8]);
                    player.setIs_eu(parseBoolean(row[9]));
                    player.setPosition(row[10]);
                    player.setMain_position(row[11]);
                    player.setFoot(row[12]);
                    player.setCurrent_club_id(parseLong(row[13]));
                    player.setCurrent_club_name(row[14]);
                    player.setJoined(parseDate(row[15]));

                    players.add(player);
                }

                playerRepository.saveAll(players);

                System.out.println("Imported Players = " + players.size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importPlayerPerformance() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/player_performances.csv");

            if (inputStream == null) {
                System.out.println("Player performance CSV not found");
                return;
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
                String[] row;
                reader.readNext();

                List<PlayerPerformance> batch = new ArrayList<>();
                int count = 0;

                while ((row = reader.readNext()) != null) {
                    PlayerPerformance p = new PlayerPerformance();

                    p.setPlayerId(parseLong(row[0]));
                    p.setCompetitionId((row[1]));
                    p.setMatchDate(row[2]);
                    p.setTeamId(row[3]);
                    p.setTeamName(row[4]);
                    p.setNbInGroup(parseLong(row[5]));
                    p.setNbOnPitch(row[6]);
                    p.setGoals(parseInteger(row[7]));
                    p.setAssists(parseInteger(row[8]));
                    p.setOwnGoals(parseInteger(row[9]));
                    p.setSubedIn(parseInteger(row[10]));
                    p.setSubedOut(parseInteger(row[11]));
                    p.setYellowCards(parseInteger(row[12]));
                    p.setSecondYellowCards(parseInteger(row[13]));
                    p.setDirectRedCards(parseInteger(row[14]));
                    p.setPenaltyGoals(parseInteger(row[15]));
                    p.setMinutesPlayed(parseInteger(row[16]));
                    p.setGoalsConceded(parseInteger(row[17]));
                    p.setCleanSheets(parseInteger(row[18]));

                    batch.add(p);
                    count++;

                    if (batch.size() == 5000) {
                        playerPerformanceRepository.saveAll(batch);
                        batch.clear();
                        System.out.println("Imported player performance rows = " + count);
                    }
                }

                if (!batch.isEmpty()) {
                    playerPerformanceRepository.saveAll(batch);
                }

                System.out.println("Player performance import completed = " + count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importNationalPerformance() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/player_national_performances.csv");

            if (inputStream == null) {
                System.out.println("National performance CSV not found");
                return;
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
                String[] row;
                reader.readNext();

                List<PlayerNationalPerformance> batch = new ArrayList<>();
                int count = 0;

                while ((row = reader.readNext()) != null) {
                    PlayerNationalPerformance p = new PlayerNationalPerformance();

                    p.setPlayerId(parseLong(row[0]));
                    p.setTeamId(parseLong(row[1]));
                    p.setMatches(parseInteger(row[2]));
                    p.setGoals(parseInteger(row[3]));
                    p.setShirtNumber(parseInteger(row[4]));
                    p.setCareerState(row[5]);

                    batch.add(p);
                    count++;

                    if (batch.size() == 5000) {
                        playerNationalPerformanceRepository.saveAll(batch);
                        batch.clear();
                        System.out.println("Imported national rows = " + count);
                    }
                }

                if (!batch.isEmpty()) {
                    playerNationalPerformanceRepository.saveAll(batch);
                }

                System.out.println("National performance import completed = " + count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LocalDate parseDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(value.trim(), formatter);
    }

    private Integer parseInteger(String value) {
    if (value == null || value.trim().isEmpty()) {
        return null;
    }
    return (int) Double.parseDouble(value.trim());
    }

    private Long parseLong(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return (long) Double.parseDouble(value.trim());
    }

    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return Double.parseDouble(value.trim());
    }

    private Boolean parseBoolean(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return Boolean.parseBoolean(value.trim());
    }
}