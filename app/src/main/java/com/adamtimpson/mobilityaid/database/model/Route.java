package com.adamtimpson.mobilityaid.database.model;

public class Route {

    private Integer id;
    private Integer userId;

    private String destinations;

    public Route() {

    }

    public Route(Integer id, Integer userId, String destinations) {
        this.id = id;
        this.userId = userId;
        this.destinations = destinations;
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

    public String getDestinations() {
        return destinations;
    }

    public void setDestinations(String destinations) {
        this.destinations = destinations;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", userId=" + userId +
                ", destinations='" + destinations + '\'' +
                '}';
    }
}
