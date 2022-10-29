import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class testcase {
    public static void main(String[] args) throws IOException {

    }
    private void generate() throws IOException {
        File ff = new File("case.in");
        BufferedWriter f = new BufferedWriter(new FileWriter(ff));
        Random random = new Random();
        f.write("10 10");
        f.append('\n');
        for (int i = 0;i < 10;i++){
            f.write(String.valueOf(random.nextInt(10)));
            f.write(" ");
        }
        f.append('\n');
        for (int i = 0;i < 10;i++){
            f.write(String.valueOf(random.nextInt(10)));
            f.write(" ");
            f.write(String.valueOf(random.nextInt(10)));
            f.append('\n');
        }
        f.close();
    }
}
