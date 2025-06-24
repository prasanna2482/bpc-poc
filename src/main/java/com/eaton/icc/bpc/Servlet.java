package com.example;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class VulnerableServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username"); // Source: user input

        try {
            // Dummy connection string for demonstration
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "password");
            Statement stmt = conn.createStatement();

            // Vulnerability: SQL Injection
            String query = "SELECT * FROM users WHERE username = '" + username + "'";

            ResultSet rs = stmt.executeQuery(query); // Sink

            while (rs.next()) {
                response.getWriter().println("User: " + rs.getString("username"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}
