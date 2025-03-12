package in.kgg.sendRedirectMethod;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/nameform")
public class sendmethod extends HttpServlet {
    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/userinput?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String JDBC_USER = "root"; 
    private static final String JDBC_PASSWORD = "khushi"; 

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchQuery = req.getParameter("search1");

        System.out.println("Received input: " + searchQuery);

        if (searchQuery != null && !searchQuery.isEmpty()) {
            boolean isSaved = saveSearchQuery(searchQuery);

            if (isSaved) {
                System.out.println("Query saved to database successfully.");
            } else {
                System.err.println("Failed to save query to database.");
            }

            resp.sendRedirect("https://www.google.com/search?q=" + searchQuery);
        } else {
            resp.sendRedirect("https://www.google.com");
        }
    }

    private boolean saveSearchQuery(String query) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO usersearch (searchis) VALUES (?)")) {
                 
                pstmt.setString(1, query);
                pstmt.executeUpdate();
                return true;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection or query execution failed.");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("NewFile.html");
    }
}
