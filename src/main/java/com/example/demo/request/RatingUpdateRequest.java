package com.example.demo.request;

public class RatingUpdateRequest {
    private String averageRating;
    private int votes;

    public RatingUpdateRequest() {
    }

    public RatingUpdateRequest(String averageRating, int votes) {
        this.averageRating = averageRating;
        this.votes = votes;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
