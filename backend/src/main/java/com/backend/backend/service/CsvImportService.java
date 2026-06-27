package com.backend.backend.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.LeagueTable;
import com.backend.backend.entity.Match;
import com.backend.backend.entity.Player;
import com.backend.backend.entity.PlayerNationalPerformance;
import com.backend.backend.entity.PlayerPerformance;
import com.backend.backend.repository.LeagueTableRepository;
import com.backend.backend.repository.MatchRepository;
import com.backend.backend.repository.PlayerNationalPerformanceRepository;
import com.backend.backend.repository.PlayerPerformanceRepository;
import com.backend.backend.repository.PlayerRepository;
import com.backend.backend.entity.GoalLeader;
import com.backend.backend.repository.GoalLeaderRepository;
import com.backend.backend.entity.AssistLeader;
import com.backend.backend.repository.AssistLeaderRepository;
import com.backend.backend.entity.TeamDiscipline;
import com.backend.backend.repository.TeamDisciplineRepository;
import com.opencsv.CSVReader;

@Service
public class CsvImportService {

    private final PlayerRepository playerRepository;
    private final PlayerPerformanceRepository playerPerformanceRepository;
    private final PlayerNationalPerformanceRepository playerNationalPerformanceRepository;
    private final MatchRepository matchRepository;
    private final LeagueTableRepository leagueTableRepository;
    private final GoalLeaderRepository goalLeaderRepository;
    private final AssistLeaderRepository assistLeaderRepository;
    private final TeamDisciplineRepository teamDisciplineRepository;

    public CsvImportService(
            PlayerRepository playerRepository,
            PlayerPerformanceRepository playerPerformanceRepository,
            PlayerNationalPerformanceRepository playerNationalPerformanceRepository,
            MatchRepository matchRepository,
            LeagueTableRepository leagueTableRepository,
            GoalLeaderRepository goalLeaderRepository,
            AssistLeaderRepository assistLeaderRepository,
            TeamDisciplineRepository teamDisciplineRepository) {

        this.playerRepository = playerRepository;
        this.playerPerformanceRepository = playerPerformanceRepository;
        this.playerNationalPerformanceRepository = playerNationalPerformanceRepository;
        this.matchRepository = matchRepository;
        this.leagueTableRepository = leagueTableRepository;
        this.goalLeaderRepository = goalLeaderRepository;
        this.assistLeaderRepository = assistLeaderRepository;
        this.teamDisciplineRepository = teamDisciplineRepository;
    }

    public void importPlayers() {

        if (playerRepository.count() > 0) {
            System.out.println("Players already imported");
            return;
        }

        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/player_profile.csv");

            if (inputStream == null) {
                System.out.println("player_profile.csv not found");
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

        if (playerPerformanceRepository.count() > 0) {
            System.out.println("Player performance already imported");
            return;
        }

        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/player_performances.csv");

            if (inputStream == null) {
                System.out.println("player_performances.csv not found");
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
                    p.setCompetitionId(row[1]);
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

        if (playerNationalPerformanceRepository.count() > 0) {
            System.out.println("National performance already imported");
            return;
        }

        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/player_national_performances.csv");

            if (inputStream == null) {
                System.out.println("player_national_performances.csv not found");
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

    public void importMatches() {

        if (matchRepository.count() > 0) {
            System.out.println("Matches already imported");
            return;
        }

        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/matches_clean.csv");

            if (inputStream == null) {
                System.out.println("matches_clean.csv not found");
                return;
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

                String[] row;
                reader.readNext();

                List<Match> batch = new ArrayList<>();
                int count = 0;

                while ((row = reader.readNext()) != null) {

                    Match match = new Match();

                    match.setMatchId(parseLong(row[0]));
                    match.setMatchDate(parseIsoDate(row[1]));
                    match.setSeasonYear(parseInteger(row[2]));
                    match.setLeagueName(row[3]);
                    match.setHomeTeam(row[4]);
                    match.setAwayTeam(row[5]);
                    match.setHomeScore(parseInteger(row[6]));
                    match.setAwayScore(parseInteger(row[7]));
                    match.setVenue(row[8]);
                    match.setAttendance(parseInteger(row[9]));
                    match.setGameStatus(row[10]);
                    match.setResult(row[11]);
                    match.setTotalGoals(parseInteger(row[12]));
                    match.setMatchTitle(row[13]);

                    batch.add(match);
                    count++;

                    if (batch.size() == 5000) {
                        matchRepository.saveAll(batch);
                        batch.clear();
                        System.out.println("Imported matches = " + count);
                    }
                }

                if (!batch.isEmpty()) {
                    matchRepository.saveAll(batch);
                }

                System.out.println("Match import completed = " + count);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importLeagueTables() {

        if (leagueTableRepository.count() > 0) {
            System.out.println("League tables already imported");
            return;
        }

        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/league_table.csv");

            if (inputStream == null) {
                System.out.println("league_table_clean.csv not found");
                return;
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

                String[] row;
                reader.readNext();

                List<LeagueTable> batch = new ArrayList<>();
                int count = 0;

                while ((row = reader.readNext()) != null) {

                    LeagueTable table = new LeagueTable();

                    table.setPlace(parseInteger(row[0]));
                    table.setTeamName(row[1]);
                    table.setGamesPlayed(parseInteger(row[2]));
                    table.setWins(parseInteger(row[3]));
                    table.setDraws(parseInteger(row[4]));
                    table.setLosses(parseInteger(row[5]));
                    table.setGoalsFor(parseInteger(row[6]));
                    table.setGoalsAgainst(parseInteger(row[7]));
                    table.setGoalDifference(parseInteger(row[8]));
                    table.setPoints(parseInteger(row[9]));
                    table.setSeasonYear(parseInteger(row[10]));
                    table.setLeagueName(row[11]);
                    table.setTableId(row[12]);

                    batch.add(table);
                    count++;

                    if (batch.size() == 5000) {
                        leagueTableRepository.saveAll(batch);
                        batch.clear();
                        System.out.println("Imported league table rows = " + count);
                    }
                }

                if (!batch.isEmpty()) {
                    leagueTableRepository.saveAll(batch);
                }

                System.out.println("League table import completed = " + count);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importGoalLeaders() {

    if (goalLeaderRepository.count() > 0) {
        System.out.println("Goal leaders already imported");
        return;
    }

    try {
        InputStream inputStream = getClass().getResourceAsStream("/data/goal_leaders.csv");

        if (inputStream == null) {
            System.out.println("goal_leaders_clean.csv not found");
            return;
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

            String[] row;
            reader.readNext();

            List<GoalLeader> batch = new ArrayList<>();
            int count = 0;

            while ((row = reader.readNext()) != null) {

                GoalLeader leader = new GoalLeader();

                leader.setGoalLeaderId(row[0]);
                leader.setRankNo(parseInteger(row[1]));
                leader.setPlayerName(row[2]);
                leader.setTeamName(row[3]);
                leader.setAppearances(parseInteger(row[4]));
                leader.setGoals(parseInteger(row[5]));
                leader.setSeasonYear(parseInteger(row[6]));
                leader.setLeagueName(row[7]);

                batch.add(leader);
                count++;

                if (batch.size() == 5000) {
                    goalLeaderRepository.saveAll(batch);
                    batch.clear();
                    System.out.println("Imported goal leaders = " + count);
                }
            }

            if (!batch.isEmpty()) {
                goalLeaderRepository.saveAll(batch);
            }

            System.out.println("Goal leaders import completed = " + count);
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importAssistLeaders() {

    if (assistLeaderRepository.count() > 0) {
        System.out.println("Assist leaders already imported");
        return;
    }

    try {
        InputStream inputStream = getClass().getResourceAsStream("/data/assist_leaders.csv");

        if (inputStream == null) {
            System.out.println("assist_leaders_clean.csv not found");
            return;
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

            String[] row;
            reader.readNext();

            List<AssistLeader> batch = new ArrayList<>();
            int count = 0;

            while ((row = reader.readNext()) != null) {

                AssistLeader leader = new AssistLeader();

                leader.setAssistLeaderId(row[0]);
                leader.setRankNo(parseInteger(row[1]));
                leader.setPlayerName(row[2]);
                leader.setTeamName(row[3]);
                leader.setAppearances(parseInteger(row[4]));
                leader.setAssists(parseInteger(row[5]));
                leader.setSeasonYear(parseInteger(row[6]));
                leader.setLeagueName(row[7]);

                batch.add(leader);
                count++;

                if (batch.size() == 5000) {
                    assistLeaderRepository.saveAll(batch);
                    batch.clear();
                    System.out.println("Imported assist leaders = " + count);
                }
            }

            if (!batch.isEmpty()) {
                assistLeaderRepository.saveAll(batch);
            }

            System.out.println("Assist leaders import completed = " + count);
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importTeamDiscipline() {

    if (teamDisciplineRepository.count() > 0) {
        System.out.println("Team discipline already imported");
        return;
    }

    try {
        InputStream inputStream = getClass().getResourceAsStream("/data/team_discipline.csv");

        if (inputStream == null) {
            System.out.println("team_discipline_clean.csv not found");
            return;
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

            String[] row;
            reader.readNext();

            List<TeamDiscipline> batch = new ArrayList<>();
            int count = 0;

            while ((row = reader.readNext()) != null) {

                TeamDiscipline discipline = new TeamDiscipline();

                discipline.setDisciplineId(row[0]);
                discipline.setRankNo(parseInteger(row[1]));
                discipline.setTeamName(row[2]);
                discipline.setMatchesPlayed(parseInteger(row[3]));
                discipline.setYellowCards(parseInteger(row[4]));
                discipline.setRedCards(parseInteger(row[5]));
                discipline.setDisciplinePoints(parseInteger(row[6]));
                discipline.setSeasonYear(parseInteger(row[7]));
                discipline.setLeagueName(row[8]);

                batch.add(discipline);
                count++;

                if (batch.size() == 5000) {
                    teamDisciplineRepository.saveAll(batch);
                    batch.clear();
                    System.out.println("Imported team discipline = " + count);
                }
            }

            if (!batch.isEmpty()) {
                teamDisciplineRepository.saveAll(batch);
            }

            System.out.println("Team discipline import completed = " + count);

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

    private LocalDate parseIsoDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return LocalDate.parse(value.substring(0, 10));
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