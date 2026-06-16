package com.backend.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Player {

    @Id
    private Long playerId;

    private String playerName;

    private String playerImageUrl;

    private LocalDate dateOfBirth;

    private Integer age;

    private String placeOfBirth;

    private String countryOfBirth;

    private Double height;

    private String citizenship;

    private Boolean isEu;

    private String position;

    private String mainPosition;

    private String foot;

    private Long currentClubId;

    private String currentClubName;

    private LocalDate joined;

    public Player() {
    }

    public Long getPlayer_id() {
        return playerId;
    }

    public void setPlayer_id(Long player_id) {
        this.playerId = player_id;
    }

    public String getPlayer_name() {
        return playerName;
    }

    public void setPlayer_name(String player_name) {
        this.playerName = player_name;
    }

    public String getPlayer_image_url() {
        return playerImageUrl;
    }

    public void setPlayer_image_url(String player_image_url) {
        this.playerImageUrl = player_image_url;
    }

    public LocalDate getDate_of_birth() {
        return dateOfBirth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.dateOfBirth = date_of_birth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPlace_of_birth() {
        return placeOfBirth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.placeOfBirth = place_of_birth;
    }

    public String getCountry_of_birth() {
        return countryOfBirth;
    }

    public void setCountry_of_birth(String country_of_birth) {
        this.countryOfBirth = country_of_birth;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public Boolean getIs_eu() {
        return isEu;
    }

    public void setIs_eu(Boolean is_eu) {
        this.isEu = is_eu;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMain_position() {
        return mainPosition;
    }

    public void setMain_position(String main_position) {
        this.mainPosition = main_position;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
    }

    public Long getCurrent_club_id() {
        return currentClubId;
    }

    public void setCurrent_club_id(Long current_club_id) {
        this.currentClubId = current_club_id;
    }

    public String getCurrent_club_name() {
        return currentClubName;
    }

    public void setCurrent_club_name(String current_club_name) {
        this.currentClubName = current_club_name;
    }

    public LocalDate getJoined() {
        return joined;
    }

    public void setJoined(LocalDate joined) {
        this.joined = joined;
    }
}