package br.com.handson.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class CompetitionEntity implements Serializable{
    private int fedaration1Id;
    private int federation2Id;
    private int stepId;
    private int stadiumId;
    private int modalityId;
    private Date startDate;
    private Date finalDate;

    public CompetitionEntity() {
    }

    public CompetitionEntity(int fedaration1Id, int federation2Id, int stepId, int stadiumId, int modalityId, Date startDate, Date finalDate) {
        this.fedaration1Id = fedaration1Id;
        this.federation2Id = federation2Id;
        this.stepId = stepId;
        this.stadiumId = stadiumId;
        this.modalityId = modalityId;
        this.startDate = startDate;
        this.finalDate = finalDate;
    }

    public int getFedaration1Id() {
        return fedaration1Id;
    }

    public void setFedaration1Id(int fedaration1Id) {
        this.fedaration1Id = fedaration1Id;
    }

    public int getFederation2Id() {
        return federation2Id;
    }

    public void setFederation2Id(int federation2Id) {
        this.federation2Id = federation2Id;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
    }

    public int getModalityId() {
        return modalityId;
    }

    public void setModalityId(int modalityId) {
        this.modalityId = modalityId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }
}