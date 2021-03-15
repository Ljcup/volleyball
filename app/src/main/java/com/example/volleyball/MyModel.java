package com.example.volleyball;

public class MyModel {
    private String Team1,Team2;
    private int Team1score,Team2score;

    private MyModel(){
    }

    private MyModel(String Team1,String Team2,int Team1score,int Team2score){
        this.Team1 = Team1;
        this.Team2 = Team2;
        this.Team1score = Team1score;
        this.Team2score = Team2score;
    }

    public String getTeam1() {
        return Team1;
    }

    public void setTeam1(String team1) {
        Team1 = team1;
    }

    public String getTeam2() {
        return Team2;
    }

    public void setTeam2(String team2) {
        Team2 = team2;
    }

    public int getTeam1score() {
        return Team1score;
    }

    public void setTeam1score(int team1score) {
        Team1score = team1score;
    }

    public int getTeam2score() {
        return Team2score;
    }

    public void setTeam2score(int team2score) {
        Team2score = team2score;
    }
}