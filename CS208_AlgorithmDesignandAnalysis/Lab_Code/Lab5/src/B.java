import java.io.*;
import java.util.*;

class node {
    int num;
    LinkedList<container> list = new LinkedList<>();

    node(int n) {
        this.num = n;
    }
}

class container {
    int index;
    int weight;
    node p;

    container(int i, int w, node n) {
        this.index = i;
        this.weight = w;
        this.p = n;
    }
}

public class B {
    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        int m = reader.nextInt();
        node graph[] = new node[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new node(i);
        }
        for (int i = 0; i < m; i++) {
            int x = reader.nextInt() - 1;
            int y = reader.nextInt() - 1;
            int w = reader.nextInt();
            graph[x].list.add(new container(i, w, graph[y]));
            graph[y].list.add(new container(i, w, graph[x]));
        }
        int p = reader.nextInt();
        int t[][] = new int[p][2];
        for (int i = 0; i < p; i++) {
            t[i] = new int[]{reader.nextInt() - 1, reader.nextInt() - 1};
        }
        Map<Integer, int[]> dij = new HashMap<>();
        int count[] = new int[m];
        for (int[] tmp : t) {
            if (dij.get(tmp[0]) == null) {
                int arr[] = dij(graph, tmp[0]);
                dij.put(tmp[0], arr);
                count = bfs_r(arr, count, graph, tmp[1], tmp[0]);
            } else {
                int arr[] = dij.get(tmp[0]);
                count = bfs_r(arr, count, graph, tmp[1], tmp[0]);
            }
        }
        for (int i : count) {
            writer.println(i);
        }
        writer.close();
    }

    static int[] bfs_r(int[] dist, int[] count, node[] graph, int n, int m) {
        Queue<node> queue = new LinkedList<>();
        queue.add(graph[n]);
        boolean check[] = new boolean[graph.length];
        while (!queue.isEmpty()) {
            node p = queue.poll();
            for (container c : p.list) {
                if (dist[c.p.num] + c.weight == dist[p.num]) {
                    count[c.index]++;
                    if (c.p.num != m && !check[c.p.num]) {
                        queue.add(c.p);
                        check[c.p.num] = true;
                    }
                }
            }
        }
        return count;
    }

    static int[] dij(node[] graph, int start) {
        int n = graph.length;
        int arr[] = new int[n];
        boolean check[] = new boolean[n];
        Arrays.fill(arr, Integer.MAX_VALUE);
        arr[start] = 0;
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        priorityQueue.add(new int[]{start, 0});
        while (!priorityQueue.isEmpty()) {
            int tmp[] = priorityQueue.poll();
            int loc = tmp[0];
            int dist = tmp[1];
            if (check[loc]) continue;
            check[loc] = true;
            for (container c : graph[loc].list) {
                if (arr[c.p.num] > dist + c.weight) {
                    arr[c.p.num] = dist + c.weight;
                    priorityQueue.add(new int[]{c.p.num, arr[c.p.num]});
                }
            }
        }
        return arr;
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