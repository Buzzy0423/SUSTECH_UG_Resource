package GoodLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.Properties;
import java.sql.*;
import java.net.URL;

public class GoodLoader_info {
    private static final int BATCH_SIZE = 597;
    private static URL propertyURL;

    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static boolean verbose = false;


    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {
            //
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            con = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        try {
            stmt = con.prepareStatement("insert into course"
                    + " values(?,?,?,?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    private static void loadData(String course_id, String course_name, int total_capacity, int course_hour, float course_credit, String course_dept)
            throws SQLException {
        if (con != null) {
            stmt.setString(1, course_id);
            stmt.setString(2, course_name);
            stmt.setInt(3, total_capacity);
            stmt.setInt(4, course_hour);
            stmt.setFloat(5, course_credit);
            stmt.setString(6, course_dept);
            stmt.addBatch();
        }
    }

    private static final String pathToJSONFile = "Course_info.json";

    class Course {
        private int totalCapacity;
        private String courseId;
        private String courseName;
        private float courseCredit;
        private int courseHour;
        private String courseDept;
        private String prerequisite;
    }

    public static void main(String[] args) {
        String content = null;
        try {
            content = Files.readString(Path.of(pathToJSONFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Gson is an example
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type type = new TypeToken<List<Course>>() {
        }.getType();
        List<Course> courses = gson.fromJson(content, type);
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "lee");
        defprop.put("password", "buzz10161");
        defprop.put("database", "db_project1");
        Properties prop = new Properties(defprop);
        long start;
        long end;
        int cnt = 0;
        // Empty target table
        openDB("localhost", "db_project1",
                "lee", "buzz10161");
        start = System.currentTimeMillis();
        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));
        try {
            for (Course c : courses) {
                loadData(c.courseId, c.courseName, c.totalCapacity, c.courseHour, c.courseCredit, c.courseDept);
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        end = System.currentTimeMillis();
        System.out.printf("Time : %dms", end - start);
        closeDB();
    }
}

