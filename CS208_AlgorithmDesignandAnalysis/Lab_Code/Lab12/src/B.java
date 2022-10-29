//import java.io.*;
//import java.util.*;
//
//public class B {
//    static int Max = 2 * 2500 + 10;
//    static int MMax = 6 * 2500;
//    static int start = 0;
//    static int target = Max + 9;
//    static int cnt = -1;
//    static int N, M, A, B;
//    static int[] cur = new int[Max + 10];
//    static int[] lv = new int[Max + 10];
//    static int[] head = new int[Max + 10];
//    static int[] nxt = new int[MMax];
//    static int[] to = new int[MMax];
//    static int[] weight = new int[MMax];
//    static boolean[][] G = new boolean[Max + 10][Max + 10];
//
//    static void init() {
//        Arrays.fill(head, -1);
//        Arrays.fill(nxt, -1);
//        Arrays.fill(weight, 0);
//        for (boolean[] booleans : G) {
//            Arrays.fill(booleans, false);
//        }
//    }
//
//    static int trans(int y, int x, boolean col) {
//        if (col) {
//            return ((y - 1) * (M << 1)) + ((x - 1) << 1) + 1;
//        } else {
//            return ((y - 1) * (M << 1)) + ((x - 1) << 1) + 2;
//        }
//    }
//
//    static void add_edge(int u, int v, int w) {
//        cnt++;
//        nxt[cnt] = head[u];
//        head[u] = cnt;
//        to[cnt] = v;
//        weight[cnt] = w;
//    }
//
//    static void build() {
//        for (int i = 1; i <= N; i++) {
//            for (int j = 1; j <= M; j++) {
//                if (G[i][j]) {
//                    add_edge(trans(i, j, true), trans(i, j, false), 1);
//                    add_edge(trans(i, j, false), trans(i, j, true), 0);
//                    add_edge(trans(i, j, false), trans(i, j, true), 1);
//                    add_edge(trans(i, j, true), trans(i, j, false), 0);
//                    if (G[i][j - 1]) {
//                        add_edge(trans(i, j, false), trans(i, j - 1, false), 1);
//                        add_edge(trans(i, j - 1, false), trans(i, j, false), 0);
//                        add_edge(trans(i, j - 1, false), trans(i, j, false), 1);
//                        add_edge(trans(i, j, false), trans(i, j - 1, false), 0);
//                    }
//                    if (G[i + 1][j]) {
//                        add_edge(trans(i, j, true), trans(i + 1, j, true), 1);
//                        add_edge(trans(i + 1, j, true), trans(i, j, true), 0);
//                        add_edge(trans(i + 1, j, true), trans(i, j, true), 1);
//                        add_edge(trans(i, j, true), trans(i + 1, j, true), 0);
//                    }
//                }
//            }
//        }
//    }
//
//    static boolean bfs() {
//        Queue<Integer> queue = new LinkedList<>();
//        queue.add(start);
//        Arrays.fill(lv, -1);
//        lv[start] = 0;
//        while (!queue.isEmpty()) {
//            int i = queue.poll();
//            for (int k = head[i]; k != -1; k = nxt[k]) {
//                if (lv[to[k]] == -1 && weight[k] > 0) {
//                    lv[to[k]] = lv[i] + 1;
//                    queue.add(to[k]);
//                }
//            }
//        }
//        return lv[target] != -1;
//    }
//
//    static long dfs(int p, long flow) {
//        if (p == target) return flow;
//        long ans = 0;
//        for (int i = cur[p]; i != -1; i = nxt[i]) {
//            cur[p] = i;
//            if (lv[to[i]] == lv[p] + 1 && weight[i] != 0) {
//                long tmp = dfs(to[i], Math.min(flow, weight[i]));
//                if (tmp > 0) {
//                    ans += tmp;
//                    flow -= tmp;
//                    weight[i] -= tmp;
//                    weight[i ^ 1] += tmp;
//                }
//                if (flow == 0) {
//                    lv[p] = -1;
//                    return ans;
//                }
//            }
//        }
//        if (flow == 0) lv[p] = -1;
//        return ans;
//    }
//
//    static long dinic() {
//        long ans = 0;
//        while (bfs()) {
//            System.arraycopy(head, 0, cur, 0, head.length);
//            ans += dfs(start, Long.MAX_VALUE);
//        }
//        return ans;
//    }
//
//    public static void main(String[] args) {
//        QReader reader = new QReader();
//        QWriter writer = new QWriter();
//        int num = reader.nextInt();
//        for (int i = 0; i < num; i++) {
//            N = reader.nextInt();
//            M = reader.nextInt();
//            A = reader.nextInt();
//            B = reader.nextInt();
//            init();
//            for (int j = 1; j <= N; j++) {
//                String s = reader.nextLine();
//                for (int k = 1; k <= M; k++) {
//                    G[j][k] = (s.charAt(k - 1) == '0');
//                }
//            }
//            for (int j = 0; j < A; j++) {
//                int tmp = reader.nextInt();
//                if (G[1][tmp]){
//                    add_edge(start, trans(1, tmp, true), 1);
//                    add_edge(trans(1, tmp, true), start, 0);
//                }
//            }
//            for (int j = 0; j < B; j++) {
//                int tmp = reader.nextInt();
//                if (G[N][tmp]){
//                    add_edge(trans(N, tmp, true), target, 1);
//                    add_edge(target, trans(N, tmp, true), 0);
//                }
//            }
//            build();
//            if (dinic() < A) {
//                writer.println("No");
//            } else {
//                writer.println("Yes");
//            }
//        }
//        writer.close();
//    }
//}
//
//class QReader {
//    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//    private StringTokenizer tokenizer = new StringTokenizer("");
//
//    private String innerNextLine() {
//        try {
//            return reader.readLine();
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    public boolean hasNext() {
//        while (!tokenizer.hasMoreTokens()) {
//            String nextLine = innerNextLine();
//            if (nextLine == null) {
//                return false;
//            }
//            tokenizer = new StringTokenizer(nextLine);
//        }
//        return true;
//    }
//
//    public String nextLine() {
//        tokenizer = new StringTokenizer("");
//        return innerNextLine();
//    }
//
//    public String next() {
//        hasNext();
//        return tokenizer.nextToken();
//    }
//
//    public int nextInt() {
//        return Integer.parseInt(next());
//    }
//
//    public long nextLong() {
//        return Long.parseLong(next());
//    }
//}
//
//class QWriter implements Closeable {
//    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
//
//    public void print(Object object) {
//        try {
//            writer.write(object.toString());
//        } catch (IOException e) {
//            return;
//        }
//    }
//
//    public void println(Object object) {
//        try {
//            writer.write(object.toString());
//            writer.write("\n");
//        } catch (IOException e) {
//            return;
//        }
//    }
//
//    @Override
//    public void close() {
//        try {
//            writer.close();
//        } catch (IOException e) {
//            return;
//        }
//    }
//}