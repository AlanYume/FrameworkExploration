package org.smart4j.chapter2.controller;

import org.smart4j.chapter2.model.Station;
import org.smart4j.chapter2.service.StationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/station")
public class StationServlet extends HttpServlet {
    private StationService stationService;

    @Override
    public void init() throws ServletException {
        this.stationService = new StationService();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final List<Station> stationList = this.stationService.getStationList();
        req.setAttribute("stationList", stationList);
        req.getRequestDispatcher("/WEB-INF/view/station_show.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final String stationName = req.getParameter("stationName");
        final String trainNameList = req.getParameter("trainNameList");
        final String trainTimeList = req.getParameter("trainTimeList");
        final long id = stationName.toCharArray()[0] - 65;

        resp.setContentType("text/html; charset=UTF-8");
        if (this.stationService.getStation(id) != null) {
            final Map<String, Object> stationInfo = new HashMap<>();
            stationInfo.put("trainNameList", trainNameList);
            stationInfo.put("trainTimeList", trainTimeList);
            if (this.stationService.updateStation(id, stationInfo)) {
                resp.getWriter().println("更新站点信息成功");
            } else {
                resp.getWriter().println("创建站点信息失败");
            }
        } else {
            final Map<String, Object> stationInfo = new HashMap<>();
            stationInfo.put("id", id);
            stationInfo.put("stationName", stationName);
            stationInfo.put("trainNameList", trainNameList);
            stationInfo.put("trainTimeList", trainTimeList);
            if (this.stationService.createStation(stationInfo)) {
                resp.getWriter().println("创建新站成功");
            } else {
                resp.getWriter().println("创建新站失败");
            }
        }
    }
}
