package com.example.volleyball;

public class MyModel {
    private String Team1,Team2;

    private MyModel(){
    }

    private MyModel(String Team1,String Team2){
        this.Team1 = Team1;
        this.Team2 = Team2;
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

}
