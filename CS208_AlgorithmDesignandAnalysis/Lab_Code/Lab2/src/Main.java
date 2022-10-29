import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        int[] portal = new int[n];
        int[] dis = new int[n];
        boolean[] check = new boolean[n];
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        for (int i = 0; i < n; i++) {
            portal[i] = reader.nextInt() - 1;
        }
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[0] = 0;
        queue.add(new int[]{0, portal[0], dis[0]});
        while (!queue.isEmpty()) {
            int[] tmp = queue.poll();
            if (check[tmp[0]]) continue;
            check[tmp[0]] = true;
            if (tmp[2] + 1 < dis[tmp[1]]) {
                dis[tmp[1]] = tmp[2] + 1;
                queue.add(new int[]{tmp[1], portal[tmp[1]], dis[tmp[1]]});
            }//portal
            if (tmp[0] != 0 && (tmp[2] + 1 < dis[tmp[0] - 1])) {
                dis[tmp[0] - 1] = tmp[2] + 1;
                queue.add(new int[]{tmp[0] - 1, portal[tmp[0] - 1], dis[tmp[0] - 1]});
            }//prev
            if (tmp[0] != n - 1 && (tmp[2] + 1 < dis[tmp[0] + 1])) {
                dis[tmp[0] + 1] = tmp[2] + 1;
                queue.add(new int[]{tmp[0] + 1, portal[tmp[0] + 1], dis[tmp[0] + 1]});
            }//next
        }
        for (int i = 0; i < n; i++) {
            writer.print(dis[i] + " ");
        }
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
