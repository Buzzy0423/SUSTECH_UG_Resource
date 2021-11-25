package GoodLoader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
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

public class GoodLoader_student {
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
            stmt = con.prepareStatement("insert into student values (?,?,?,?)");
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

    private static void loadData(int student_id, String student_name, String sex, String college)
            throws SQLException {
        if (con != null) {
            stmt.setInt(1,student_id);
            stmt.setString(2,student_name);
            stmt.setString(3,sex);
            stmt.setString(4, college);
            stmt.addBatch();
        }
    }

    public static class teacher2 {
        public String teacher_id;
        public String teacher;
    }

    public static void main(String[] args) {
        long start;
        long end;
        // Empty target table
        openDB("localhost", "db_project1",
                "lee", "buzz10161");
        start = System.currentTimeMillis();
        try {
            FileReader fw = new FileReader("clean_data.csv");
            BufferedReader bf = new BufferedReader(fw);
            bf.readLine();
            String line;
            while((line=bf.readLine())!=null){
                String item[] = line.split(",");
                loadData(Integer.parseInt(item[3]), item[0], item[1], item[2]);
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
            stmt.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeDB();
        end = System.currentTimeMillis();
        System.out.printf("Speed : %d ", (3999920 / (end - start)) * 1000);
        closeDB();
    }
}

