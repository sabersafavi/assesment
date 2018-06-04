
package com.activeperform.saber.assesment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinates {

    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("human_address")
    @Expose
    private String humanAddress;
    @SerializedName("needs_recoding")
    @Expose
    private Boolean needsRecoding;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Coordinates withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getHumanAddress() {
        return humanAddress;
    }

    public void setHumanAddress(String humanAddress) {
        this.humanAddress = humanAddress;
    }

    public Coordinates withHumanAddress(String humanAddress) {
        this.humanAddress = humanAddress;
        return this;
    }

    public Boolean getNeedsRecoding() {
        return needsRecoding;
    }

    public void setNeedsRecoding(Boolean needsRecoding) {
        this.needsRecoding = needsRecoding;
    }

    public Coordinates withNeedsRecoding(Boolean needsRecoding) {
        this.needsRecoding = needsRecoding;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Coordinates withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

}
