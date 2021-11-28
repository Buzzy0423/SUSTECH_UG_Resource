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

public class GoodLoader_class_detail {
    private static final int BATCH_SIZE = 598;
    private static URL propertyURL;
    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static boolean verbose = false;

    private static void openDB(String host, String dbname, String user, String pwd) {
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
            stmt = con.prepareStatement("insert into class_detail values (?,?,?,?,?)");
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

    private static void loadData(String class_id, int[] weekList, String location, String classTime, int weekday)
            throws SQLException {
        if (con != null) {
            stmt.setString(1, class_id);
            stmt.setObject(2, weekList);
            stmt.setString(3, location);
            stmt.setString(4, classTime);
            stmt.setInt(5, weekday);
            stmt.addBatch();
        }
    }

    private static final String pathToJSONFile = "class_detail.json";

    public static void main(String[] args) {
        String content = null;
        try {
            content = Files.readString(Path.of(pathToJSONFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Gson is an example
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Type type = new TypeToken<List<class_detail>>() {
        }.getType();
        List<class_detail> courses = gson.fromJson(content, type);
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
            int i = 1;
            for (class_detail c : courses) {
                loadData(c.class_id, c.weekList, c.location, c.classTime,c.weekday);
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
        System.out.printf("Speed : %d ", (969 * 1000) / (end - start));
        closeDB();
    }

    public static class class_detail {
        String class_id;
        int[] weekList;
        String location;
        String classTime;
        int weekday;

        class_detail(String class_id, int[] weekList, String location, String classTime, int weekday) {
            this.class_id = class_id;
            this.weekList = weekList;
            this.classTime = classTime;
            this.location = location;
            this.weekday = weekday;
        }
    }
}

