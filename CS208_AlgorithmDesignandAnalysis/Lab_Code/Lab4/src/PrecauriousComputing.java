import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class interval {
    int start;
    int end;
    int sum;
    int base;

    interval(int start, int end, int sum, int base) {
        this.start = start;
        this.end = end;
        this.sum = sum;
        this.base = base;
    }
}


public class PrecauriousComputing {
    static int[] log = new int[100011];

    public static void main(String[] args) {
        QReader input = new QReader();
        QWriter out = new QWriter();
        int n = input.nextInt();
        int m = input.nextInt();
        int[] income_prefix = new int[n + 1];
        int[] computer = new int[n + 1];
        pre();
        int[][] st_table = new int[n + 1][log[n] + 2];
        PriorityQueue<interval> heap=new PriorityQueue<>(((o1, o2) -> o2.sum - o1.sum));
        for (int i = 1; i <= n; i++) {
            int a = input.nextInt();
            computer[i] = a;
            st_table[i][0] = i;
        }
        for (int j = 1; j <= log[n] + 1; j++) {
            for (int i = 1; i + (1 << j) - 1 <= n; i++) {
                if (computer[st_table[i][j - 1]] < computer[st_table[i + (1 << (j - 1))][j - 1]]) {
                    st_table[i][j] = st_table[i][j - 1];
                } else {
                    st_table[i][j] = st_table[i + (1 << (j - 1))][j - 1];
                }
            }
        }
        int pre = 0;
        for (int i = 1; i <= n; i++) {
            int b = input.nextInt();
            pre = pre + b;
            income_prefix[i] = pre;
        }
        int s = log[n - 1 + 1];
        int min;
        int min_index;
        if (computer[st_table[1][s]] < computer[st_table[n - (1 << s) + 1][s]]) {
            min = computer[st_table[1][s]];
            min_index = st_table[1][s];
        } else {
            min = computer[st_table[n - (1 << s) + 1][s]];
            min_index = st_table[n - (1 << s) + 1][s];
        }
        long result = 0;
        if (min < m) {
            int sum1 = sum(1, min_index - 1, income_prefix);
            int sum2 = sum(min_index + 1, n, income_prefix);
            result = result + (long)min * (long) sum(1, n, income_prefix);
            m=m-min;
            if(1<=min_index-1) {
                interval temp1 = new interval(1, min_index - 1, sum1, min);
                heap.add(temp1);
            }
            if(min_index+1<=n) {
                interval temp2 = new interval(min_index + 1, n, sum2, min);
                heap.add(temp2);
            }

        } else {
            result = result + (long)m * (long)sum(1, n, income_prefix);
            out.print(result);
            out.close();
            return;
        }
        while (m > 0 && !heap.isEmpty()) {
            interval max_interval = heap.poll();
            int y = max_interval.end;
            int x = max_interval.start;
            int s_inter = log[y - x + 1];
            int index_inter;
            if (computer[st_table[x][s_inter]] < computer[st_table[y - (1 << s_inter) + 1][s_inter]]) {
                index_inter = st_table[x][s_inter];
            } else {
                index_inter = st_table[y - (1 << s_inter)  + 1][s_inter];
            }
            int min_inter = computer[index_inter];
            if(m>min_inter-max_interval.base) {
                result = result + (long)(min_inter - max_interval.base) * (long)sum(x, y, income_prefix);
                m = m - (min_inter - max_interval.base);
                if (x <= index_inter - 1) {
                    int sum1 = sum(x, index_inter - 1, income_prefix);
                    interval temp1 = new interval(x, index_inter - 1, sum1, min_inter);
                    heap.add(temp1);
                }
                if (y >= index_inter + 1) {
                    int sum2 = sum(index_inter + 1, y, income_prefix);
                    interval temp2 = new interval(index_inter + 1, y, sum2, min_inter);
                    heap.add(temp2);
                }
            }else {
                result = result + (long)m * (long)sum(x, y, income_prefix);
                break;
            }
        }
        out.print(result);
        out.close();
    }

    static int sum(int start, int end, int[] array) {
        return array[end] - array[start - 1];
    }

    static void pre() {
        log[1] = 0;
        log[2] = 1;
        for (int i = 3; i < 100010; i++) {
            log[i] = log[i / 2] + 1;
        }
    }
}

class Untitled {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        while (in.hasNext()) {
            int x = in.nextInt();
            out.println(x);
        }
        out.close();
    }
}

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