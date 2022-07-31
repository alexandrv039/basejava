package com.urise.webapp.web;

import com.urise.webapp.storage.SqlStorage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        SqlStorage storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes",
                "postgres", "postgres");
        request.setAttribute("resumes", storage.getAllSorted());
        getServletContext().getRequestDispatcher("/resume.jsp").forward(request, response);

    }
}
