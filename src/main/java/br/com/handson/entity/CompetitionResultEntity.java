package br.com.handson.entity;

import java.io.Serializable;

public class CompetitionResultEntity implements Serializable{
    private int id;
    private String teamA;
    private String teamB;
    private String step;
    private String stadium;
    private String modality;
    private String startDate;
    private String finalDate;

    public CompetitionResultEntity() {
    }

    public CompetitionResultEntity(int id, String teamA, String teamB, String step, String stadium, String modality, String startDate, String finalDate) {
        this.id = id;
        this.teamA = teamA;
        this.teamB = teamB;
        this.step = step;
        this.stadium = stadium;
        this.modality = modality;
        this.startDate = startDate;
        this.finalDate = finalDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }
}
