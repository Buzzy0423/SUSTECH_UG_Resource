//import java.io.*;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.StringTokenizer;
//
//public class B {
//    private static class node {
//        int cnt;
//        node l_child;
//        node r_child;
//
//        node(int c) {
//            cnt = c;
//            l_child = this;
//            r_child = this;
//        }
//
//        node(node copy) {
//            if (copy != null) {
//                cnt = copy.cnt + 1;
//                l_child = copy.l_child;
//                r_child = copy.r_child;
//            }
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        QReader reader = new QReader();
//        QWriter writer = new QWriter();
//        BufferedReader r = new BufferedReader(new FileReader("/Users/lee/课件/CS208_AlgorithmDesignandAnalysis/Lab/Lab8/additional_file_46_2/2.out"));
//        int n = reader.nextInt();
//        int q = reader.nextInt();
//        long[] arr = new long[n];
//        for (int i = 0; i < n; i++) {
//            arr[i] = reader.nextLong();
//        }
//        con cc = dis(arr);
//        int max = cc.max;
//        arr = cc.arr;
//        Map<Integer, Long> map = cc.map;
//        node[] con = new node[n + 1];
//        con[0] = new node(0);
//        for (int i = 1; i < n + 1; i++) {
//            con[i] = build(con[i], con[i - 1], 0, max, arr[i - 1]);
//        }
//        for (int i = 0; i < q; i++) {
//            int a = reader.nextInt();
//            int b = reader.nextInt();
//            int rank = (b - a + 2) / 2;
//            int tmp = Integer.parseInt(r.readLine());
//            long ans = map.get(query(con[a - 1], con[b], 0, max, rank));
//            int aa = query(con[a - 1], con[b], 0, max, rank);
////            if (tmp != ans) {
////                writer.println(i);
////                writer.println(a + " " + b);
////                writer.println(tmp);
////                writer.println(ans);
////                break;
////            }
//            writer.println(map.get(query(con[a - 1], con[b], 1, max, rank)));
//        }
//        writer.close();
//    }
//
//    private static node build(node t, node pre, long min, long max, long val) {
//        t = new node(pre);
//        if (min == max) {
//            return t;
//        }
//        long mid = (min + max) / 2;
//        if (val > mid) {
//            t.r_child = build(t.r_child, pre.r_child, mid + 1, max, val);
//        } else {
//            t.l_child = build(t.l_child, pre.l_child, min, mid, val);
//        }
//        return t;
//    }
//
//    private static int query(node l, node r, int min, int max, int rank) {
//        if (min == max) {
//            return min;
//        }
//        int mid = (min + max) / 2;
//        int l_diff = r.l_child.cnt - l.l_child.cnt;
//        if (rank <= l_diff) {
//            return query(l.l_child, r.l_child, min, mid, rank);
//        } else {
//            return query(l.l_child, r.l_child, mid + 1, max, rank - l_diff);
//        }
//    }
//
//    private static con dis(long[] arr) {
//        long[] tmp = Arrays.copyOfRange(arr, 0, arr.length);
//        Arrays.sort(tmp);
//        Map<Long, Integer> map = new HashMap<>();
//        Map<Integer, Long> map2 = new HashMap<>();
//        int n = 0;
//        for (int i = 0; i < tmp.length; i++) {
//            if (map.get(tmp[i]) == null) {
//                map.put(tmp[i], n++);
//                map2.put(n, tmp[i]);
//            }
//        }
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = map.get(arr[i]);
//        }
//        return new con(arr, n - 1, map2);
//    }
//
//    private static class con {
//        long[] arr;
//        int max;
//        Map<Integer, Long> map;
//
//        con(long[] tmp, int i, Map<Integer, Long> m) {
//            arr = tmp;
//            max = i;
//            map = m;
//        }
//    }
//}
//
//class QReader {
//    //private BufferedReader reader = new BufferedReader(new FileReader("/Users/lee/课件/CS208_AlgorithmDesignandAnalysis/Lab/Lab8/additional_file_46_2/2.in"));
//    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//    private StringTokenizer tokenizer = new StringTokenizer("");
//
//    QReader() throws FileNotFoundException {
//    }
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