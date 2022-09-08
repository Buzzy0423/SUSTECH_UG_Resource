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

public class GoodLoader_prerequisite {
    private static final int BATCH_SIZE = 598;
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
            stmt = con.prepareStatement("insert into course_prerequisite values (?,?,?)");
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

    private static void loadData(String course_id,int groupnum,String pre_course)
            throws SQLException {
        if (con != null) {
            stmt.setString(1, course_id);
            stmt.setInt(2, groupnum);
            stmt.setString(3,pre_course);
            stmt.addBatch();
        }
    }

    private static final String pathToJSONFile = "Pre2.json";

    static class Pre_course {
        String courseId;
        int group_num;
        String course;
    }

    public static void main(String[] args) {
        String content = null;
        try {
            content = Files.readString(Path.of(pathToJSONFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Gson is an example
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Type type = new TypeToken<List<Pre_course>>() {
        }.getType();
        List<Pre_course> courses = gson.fromJson(content, type);
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "lee");
        defprop.put("password", "buzz10161");
        defprop.put("database", "db_project1");
        Properties prop = new Properties(defprop);
        long start;
        long end;
        // Empty target table
        openDB("localhost", "db_project1",
                "lee", "buzz10161");
        start = System.currentTimeMillis();
        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));
        try {
            for (Pre_course c : courses) {
                loadData(c.courseId,c.group_num,c.course);
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

