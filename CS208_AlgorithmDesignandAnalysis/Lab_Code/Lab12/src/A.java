//import java.io.*;
//import java.util.*;
//
//public class A {
//    static int N,M,S,T;
//    static int Max = 100;
//    static int MMax = 5000;
//    static int cnt = -1;
//    static int[] cur = new int[Max + 1];
//    static int[] lv = new int[Max + 1];
//    static int[] head = new int[Max + 1];
//    static int[] nxt = new int[(MMax + 1) << 1];
//    static int[] to = new int[(MMax + 1) << 1];
//    static long[] weight = new long[(MMax + 1) << 1];
//
//    static void add_edge(int u, int v, long w){
//        cnt++;
//        nxt[cnt] = head[u];
//        head[u] = cnt;
//        to[cnt] = v;
//        weight[cnt] = w;
//    }
//
//    static boolean bfs(){
//        Queue<Integer> queue = new LinkedList<>();
//        queue.add(S);
//        Arrays.fill(lv, -1);
//        lv[S] = 0;
//        while (!queue.isEmpty()){
//            int i = queue.poll();
//            for (int k = head[i];k != -1; k = nxt[k]){
//                if (lv[to[k]] == -1 && weight[k] > 0){
//                    lv[to[k]] = lv[i] + 1;
//                    queue.add(to[k]);
//                }
//            }
//        }
//        return lv[T] != -1;
//    }
//
//    static long dfs(int p, long flow){
//        if (p == T) return flow;
//        long ans = 0;
//        for (int i = cur[p];i != -1; i = nxt[i]){
//            cur[p] = i;
//            if (lv[to[i]] == lv[p] + 1 && weight[i] != 0){
//                long tmp = dfs(to[i], Math.min(flow, weight[i]));
//                if (tmp > 0){
//                    ans += tmp;
//                    flow -= tmp;
//                    weight[i] -= tmp;
//                    weight[i ^ 1] += tmp;
//                }
//                if (flow == 0){
//                    lv[p] = -1;
//                    return ans;
//                }
//            }
//        }
//        if (flow == 0) lv[p] = -1;
//        return ans;
//    }
//
//    static long dinic(){
//        long ans = 0;
//        while (bfs()){
//            if (N >= 0) System.arraycopy(head, 1, cur, 1, N);
//            ans += dfs(S, Long.MAX_VALUE);
//        }
//        return ans;
//    }
//
//    public static void main(String[] args) {
//        QReader reader = new QReader();
//        QWriter writer = new QWriter();
//        N = reader.nextInt();
//        M = reader.nextInt();
//        S = reader.nextInt();
//        T = reader.nextInt();
//        Arrays.fill(head, -1);
//        Arrays.fill(nxt, -1);
//        for (int i = 0;i < M;i++){
//            int u = reader.nextInt();
//            int v = reader.nextInt();
//            long w = reader.nextLong();
//            add_edge(u,v,w);
//            add_edge(v,u,0);
//        }
//        writer.println(dinic());
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