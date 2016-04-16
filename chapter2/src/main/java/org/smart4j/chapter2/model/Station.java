package org.smart4j.chapter2.model;

public class Station {
    private long id;
    private String stationName;
    private String trainNameList;
    private String trainTimeList;

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getStationName() {
        return this.stationName;
    }

    public void setStationName(final String stationName) {
        this.stationName = stationName;
    }

    public String getTrainNameList() {
        return this.trainNameList;
    }

    public void setTrainNameList(final String trainNameList) {
        this.trainNameList = trainNameList;
    }

    public String getTrainTimeList() {
        return this.trainTimeList;
    }

    public void setTrainTimeList(final String trainTimeList) {
        this.trainTimeList = trainTimeList;
    }
}
