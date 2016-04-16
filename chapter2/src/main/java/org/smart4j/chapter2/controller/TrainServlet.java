package org.smart4j.chapter2.controller;

import org.smart4j.chapter2.model.Station;
import org.smart4j.chapter2.service.StationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/train")
public class TrainServlet extends HttpServlet {
    private StationService stationService;

    @Override
    public void init() throws ServletException {
        this.stationService = new StationService();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final Station stationStart = this.stationService.getStation(req.getParameter("stationStart")
                .toCharArray()[0] - 65);
        final Station stationEnd = this.stationService.getStation(req.getParameter("stationEnd")
                .toCharArray()[0] - 65);
        final List<Integer> trainList = getTrainList(stationStart, stationEnd);

//        req.setAttribute("stationList", stationList);
//        req.getRequestDispatcher("/WEB-INF/view/station_show.jsp").forward(req, resp);
    }

    private List<Integer> getTrainList(final Station stationStart, final Station stationEnd) {
        final List<Integer> startID = getTrainID(stationStart);
        final List<Integer> endID = getTrainID(stationEnd);
        final List<Date> startTime = getTrainData(stationStart);
        final List<Date> endTime = getTrainData(stationEnd);

        final List<Integer> trainList = new ArrayList<>();
        final int sSize = startID.size();
        final int eSize = endID.size();
        for (int i = 0; i < sSize; i++) {
            for (int j = 0; j < eSize; j++) {
                if (startID.get(i).equals(endID.get(j)) && startTime.get(i).before(
                        endTime.get(j))) {
                    trainList.add(startID.get(i));
                }
            }
        }
        return trainList;
    }

    private List<Integer> getTrainID(final Station station) {
        final List<Integer> trainId = new ArrayList<>();
        for (final String cID : station.getTrainNameList().split(" ")) {
            trainId.add(cID.toCharArray()[0] - 97);
        }
        return trainId;
    }

    private List<Date> getTrainData(final Station station) {
        final List<Date> trainData = new ArrayList<>();
        for (final String data : station.getTrainTimeList().split(" ")) {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
                final Date dt = sdf.parse(data);
                trainData.add(dt);
            } catch (final ParseException e) {
                e.printStackTrace();
            }
        }
        return trainData;
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
//        final String stationName = req.getParameter("stationName");
//        final String trainNameList = req.getParameter("trainNameList");
//        final String trainTimeList = req.getParameter("trainTimeList");
//        final long id = stationName.toCharArray()[0] - 65;
//
//        resp.setContentType("text/html; charset=UTF-8");
//        if (this.stationService.getStation(id) != null) {
//            final Map<String, Object> stationInfo = new HashMap<>();
//            stationInfo.put("trainNameList", trainNameList);
//            stationInfo.put("trainTimeList", trainTimeList);
//            if (this.stationService.updateStation(id, stationInfo)) {
//                resp.getWriter().println("更新站点信息成功");
//            } else {
//                resp.getWriter().println("创建站点信息失败");
//            }
//        } else {
//            final Map<String, Object> stationInfo = new HashMap<>();
//            stationInfo.put("id", id);
//            stationInfo.put("stationName", stationName);
//            stationInfo.put("trainNameList", trainNameList);
//            stationInfo.put("trainTimeList", trainTimeList);
//            if (this.stationService.createStation(stationInfo)) {
//                resp.getWriter().println("创建新站成功");
//            } else {
//                resp.getWriter().println("创建新站失败");
//            }
//        }
    }
}
