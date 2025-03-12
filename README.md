🔥 Author
👩‍💻 Khushi Gupta

Happy coding! 🚀

=========================================================================================

✅ This **README** explains **setup, database configuration, servlet usage, and execution**.  
Let me know if you want any changes! 🚀😊

===================================================================================
# Google Search Servlet

This Java Servlet project allows users to search on **Google** while logging their queries into a **MySQL database** with timestamps. It uses **JSP/Servlets**, **JDBC**, and **MySQL**.

## 🛠️ Technologies Used
- **Java (Servlets)**
- **HTML**
- **MySQL**
- **Tomcat Server**
- **JDBC (Java Database Connectivity)**

## 📌 Features
✅ Users enter a search term in an HTML form.  
✅ The **search term** is logged into a **MySQL database** with a timestamp.  
✅ The user is then redirected to **Google search results**.  
✅ **Stored searches** can be retrieved from the database.

## 🏗️ Project Setup

### 1️⃣ Install Required Software
- **Java JDK 8+**
- **Apache Tomcat (9 or later)**
- **MySQL Server**
- **MySQL Workbench (Optional for DB management)**

### 2️⃣ Create Database and Table
Run the following SQL script in **MySQL Workbench** or **phpMyAdmin**:

```sql
CREATE DATABASE IF NOT EXISTS userinput;
USE userinput;

CREATE TABLE IF NOT EXISTS usersearch (
    searchis VARCHAR(100),
    search_time DATETIME DEFAULT NOW()
);
3️⃣ Configure Tomcat
1.Place your project inside the Tomcat webapps folder.
2.Start Tomcat Server.
3.Deploy the WAR file (if needed).
4️⃣ Run the Application
1.Open your browser.
2.http://localhost:8080/your-project-name/index.html
3.Enter a search term and click Search.

======================================================================================
 Servlet Code (sendmethod.java)
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

========================================================================
DATABASE
CREATE DATABASE userinput;
USE userinput;
CREATE TABLE usersearch(searchis VARCHAR(100) , search_time DATETIME DEFAULT NOW())
SELECT * FROM usersearch;
