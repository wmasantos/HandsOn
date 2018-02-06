package br.com.handson.entity;

import java.io.Serializable;
import java.util.List;

public class CheckListEntity implements Serializable {
    private List<PairValueEntity> fedetations;
    private List<PairValueEntity> steps;
    private List<PairValueEntity> stadiums;
    private List<PairValueEntity> modalities;

    public CheckListEntity() {
    }

    public CheckListEntity(List<PairValueEntity> fedetations, List<PairValueEntity> steps, List<PairValueEntity> stadiums, List<PairValueEntity> modalities) {
        this.fedetations = fedetations;
        this.steps = steps;
        this.stadiums = stadiums;
        this.modalities = modalities;
    }

    public List<PairValueEntity> getFedetations() {
        return fedetations;
    }

    public void setFedetations(List<PairValueEntity> fedetations) {
        this.fedetations = fedetations;
    }

    public List<PairValueEntity> getSteps() {
        return steps;
    }

    public void setSteps(List<PairValueEntity> steps) {
        this.steps = steps;
    }

    public List<PairValueEntity> getStadums() {
        return stadiums;
    }

    public void setStadums(List<PairValueEntity> stadiums) {
        this.stadiums = stadiums;
    }

    public List<PairValueEntity> getModalities() {
        return modalities;
    }

    public void setModalities(List<PairValueEntity> modalities) {
        this.modalities = modalities;
    }
}
