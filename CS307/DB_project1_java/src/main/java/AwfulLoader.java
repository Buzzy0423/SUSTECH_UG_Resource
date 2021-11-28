import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.Properties;
import java.sql.*;
import java.net.URL;

public class AwfulLoader {

    private static Connection con = null;
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
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    private static void loadData(String class_id, int[] weekList, String location, String classTime, int weekday)
            throws SQLException {
        Statement stmt;
        if (con != null) {
            stmt = con.createStatement();
            stmt.execute("insert into class_detail(CLASS_ID, LOCATION, CLASS_TIME, WEEKDAY) values('"
                    + class_id + "','"
                    + location + "','" + classTime + "','" + weekday + "')");
        }
    }

    public static void main(String[] args) {
        String content = null;
        try {
            content = Files.readString(Path.of("class_detail.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Gson is an example
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Type type = new TypeToken<List<BadLoader.class_detail>>() {
        }.getType();
        List<BadLoader.class_detail> courses = gson.fromJson(content, type);
        long start;
        long end;
        start = System.currentTimeMillis();
        try {
            int i = 1;
            for (BadLoader.class_detail c : courses) {
                openDB("localhost", "db_project1",
                        "lee", "buzz10161");
                loadData(c.class_id, c.weekList, c.location, c.classTime, c.weekday);
                closeDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        end = System.currentTimeMillis();
        System.out.printf("Speed : %d ", (969 * 1000) / (end - start));
        closeDB();
    }
}

