package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Station;

import java.util.List;
import java.util.Map;

public class StationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationService.class);

    public List<Station> getStationList() {
        final String sql = "SELECT * FROM station";
        return DatabaseHelper.queryEntityList(Station.class, sql);
    }

    public Station getStation(final long id) {
        final String sql = "SELECT * FROM station WHERE id = ?";
        return DatabaseHelper.queryEntity(Station.class, sql, id);
    }

    public boolean createStation(final Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Station.class, fieldMap);
    }

    public boolean updateStation(final long id, final Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Station.class, id, fieldMap);
    }

    public boolean deleteStation(final long id) {
        return DatabaseHelper.deleteEntity(Station.class, id);
    }
}
