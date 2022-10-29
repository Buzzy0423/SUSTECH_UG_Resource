import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B {
    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        int[] color = new int[n];
        int counter = 1;
        color[0] = reader.nextInt();
        for (int i = 1; i < n; i++) {
            int tmp = reader.nextInt();
            if (color[counter - 1] != tmp){
                color[counter] = tmp;
                counter++;
            }
        }
        int[][] arr = new int[counter][counter];
        for (int i = 1; i < counter; i++) {
            for (int j = 0; j + i < counter; j++) {
                if (color[j] == color[i + j]) {
                    arr[j][i + j] = arr[j + 1][i + j - 1] + 1;
                } else {
                    arr[j][i + j] = Math.min(arr[j + 1][i + j] + 1, arr[j][i + j - 1] + 1);
                }
            }
        }
        writer.print(arr[0][counter - 1]);
        writer.close();
    }
}

class QReader {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private StringTokenizer tokenizer = new StringTokenizer("");

    private String innerNextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean hasNext() {
        while (!tokenizer.hasMoreTokens()) {
            String nextLine = innerNextLine();
            if (nextLine == null) {
                return false;
            }
            tokenizer = new StringTokenizer(nextLine);
        }
        return true;
    }

    public String nextLine() {
        tokenizer = new StringTokenizer("");
        return innerNextLine();
    }

    public String next() {
        hasNext();
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }
}

class QWriter implements Closeable {
    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(Object object) {
        try {
            writer.write(object.toString());
        } catch (IOException e) {
            return;
        }
    }

    public void println(Object object) {
        try {
            writer.write(object.toString());
            writer.write("\n");
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            return;
        }
    }
}