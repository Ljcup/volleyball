package com.example.volleyball;

public class MyModel {
    private String Team1,Team2;

    private MyModel(){
    }

    private MyModel(String Team1,String Team2,int Team1score,int Team2score,int Team1set1,int Team1set2,int Team1set3,int Team1set4,int Team1set5,int Team2set1,int Team2set2,int Team2set3,int Team2set4,int Team2set5){
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