package org.smart4j.chapter2.controller;

import org.smart4j.chapter2.model.User;
import org.smart4j.chapter2.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final String name = req.getParameter("username");
        final String pass = req.getParameter("password");

        resp.setContentType("text/html; charset=UTF-8");
        if (this.userService.getUserByNameAndPassWord(name, pass) != null) {
            resp.getWriter().println("欢迎");
            resp.sendRedirect("welcome");
        } else {
            req.setAttribute("user", new User(name, pass));
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
//            resp.sendRedirect("reg");
        }
    }
}
