package org.smart4j.chapter2.controller;

import org.smart4j.chapter2.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final String user = req.getParameter("username");
        final String pass = req.getParameter("password");

        resp.setContentType("text/html; charset=UTF-8");
        if (this.userService.getUser(user) != null) {
            resp.getWriter().println("该用户已经存在");
        } else {
            final Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", user);
            userInfo.put("password", pass);
            if (this.userService.createUser(userInfo)) {
                resp.getWriter().println("注册成功");
            } else {
                resp.getWriter().println("注册失败");
            }
        }
    }
}
