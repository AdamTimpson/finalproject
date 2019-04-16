package com.adamtimpson.mobilityaid.database.model;

public class Preference {

    private Integer id;
    private Integer userId;

    private String places;
    private String placeType;

    public Preference() {

    }

    public Preference(Integer id, Integer userId, String placeType, String places) {
        this.id = id;
        this.userId = userId;
        this.placeType = placeType;
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

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
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
                ", placeType=" + placeType +
                ", places='" + places + '\'' +
                '}';
    }
}
