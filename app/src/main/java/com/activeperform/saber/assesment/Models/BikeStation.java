
package com.activeperform.saber.assesment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BikeStation {

    @SerializedName("nbbikes")
    @Expose
    private String nbbikes;
    @SerializedName("featurename")
    @Expose
    private String featurename;
    @SerializedName("terminalname")
    @Expose
    private String terminalname;
    @SerializedName("uploaddate")
    @Expose
    private Long uploaddate;

    @SerializedName("nbemptydoc")
    @Expose
    private int bikesCount;
    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("id")
    @Expose
    private String id;

    public String getNbbikes() {
        return nbbikes;
    }

    public void setNbbikes(String nbbikes) {
        this.nbbikes = nbbikes;
    }

    public BikeStation withNbbikes(String nbbikes) {
        this.nbbikes = nbbikes;
        return this;
    }

    public String getFeaturename() {
        return featurename;
    }

    public void setFeaturename(String featurename) {
        this.featurename = featurename;
    }

    public BikeStation withFeaturename(String featurename) {
        this.featurename = featurename;
        return this;
    }

    public String getTerminalname() {
        return terminalname;
    }

    public void setTerminalname(String terminalname) {
        this.terminalname = terminalname;
    }

    public BikeStation withTerminalname(String terminalname) {
        this.terminalname = terminalname;
        return this;
    }

    public Long getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(Long uploaddate) {
        this.uploaddate = uploaddate;
    }

    public BikeStation withUploaddate(Long uploaddate) {
        this.uploaddate = uploaddate;
        return this;
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public BikeStation withCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }


    public int getBikesCount() {
        return bikesCount;
    }

    public void setBikesCount(int bikesCount) {
        this.bikesCount = bikesCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BikeStation withId(String id) {
        this.id = id;
        return this;
    }


    @Override
    public String toString() {
        return featurename ;
    }
    public String toString2() {
        return "" +
                "terminal name : " + terminalname + '\n' +
                "feature name :" + featurename + '\n' +
                "nbbikes='" + nbbikes + '\n' +
                "upload date=" + new Date(uploaddate).toLocaleString() +'\n' +
                "bikes Count=" + bikesCount ;
    }
}
