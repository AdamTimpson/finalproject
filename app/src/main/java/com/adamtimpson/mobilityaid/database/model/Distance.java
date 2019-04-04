package com.adamtimpson.mobilityaid.database.model;

public class Distance {

    private Integer id;
    private Integer userId;

    private Integer distance;

    public Distance() {

    }

    public Distance(Integer id, Integer userId, Integer distance) {
        this.id = id;
        this.userId = userId;
        this.distance = distance;
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

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "id=" + id +
                ", userId=" + userId +
                ", distance='" + distance + '\'' +
                '}';
    }
}
