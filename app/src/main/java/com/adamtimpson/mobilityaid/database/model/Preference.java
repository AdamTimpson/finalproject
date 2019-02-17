package com.adamtimpson.mobilityaid.database.model;

public class Preference {

    private Integer id;
    private Integer userId;

    private String places;

    public Preference() {

    }

    public Preference(Integer id, Integer userId, String places) {
        this.id = id;
        this.userId = userId;
        this.places = places;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "Preference{" +
                "id=" + id +
                ", userId=" + userId +
                ", places='" + places + '\'' +
                '}';
    }
}
