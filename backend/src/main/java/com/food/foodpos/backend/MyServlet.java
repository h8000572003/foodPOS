package com.food.foodpos.backend;

import com.example.DTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 1109001 on 2015/6/10.
 */

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Gson gson = new GsonBuilder().create();

        List<MyDTO> dtos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dtos.add(new MyDTO(i + ""));
        }

        resp.setContentType("text/plain");
        resp.getWriter().println(gson.toJson(dtos));
    }


    private class MyDTO {
        private String name = "";

        public MyDTO(String name) {
            this.name = name;
        }
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {
            resp.getWriter().println("Please enter a name");
        }
        resp.getWriter().println("Hello " + name);
    }
}
