import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B {
    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        int m = reader.nextInt();
        int c = reader.nextInt();
        int t = reader.nextInt();
        int rabbit[] = new int[n];
        boolean in[] = new boolean[n];
        int hole[] = new int[m];
        for (int i = 0; i < n; i++) {
            rabbit[i] = reader.nextInt();
        }
        for (int i = 0; i < m; i++) {
            hole[i] = reader.nextInt();
        }
        Arrays.sort(rabbit);
        Arrays.sort(hole);
        int loc = 0;
        int ans = 0;
        for (int i = 0; i < m; i++) {
            loc = binary_search(rabbit, loc, rabbit.length - 1, hole[i]);
            int tmp = loc;
            int tmp2 = 0;
            int l = tmp - 1;
            int r = tmp + 1;
            while (tmp2 < c) {
                if (Math.abs(rabbit[tmp] - hole[i]) > t) {
                    break;
                }
                if (!in[tmp]) {
                    in[tmp] = true;
                    tmp2++;
                    ans++;
                }
                if (l < 0 && r < n) {
                    tmp = r;
                    r++;
                } else if (l >= 0 && r > n - 1) {
                    tmp = l;
                    l--;
                } else if (l < 0 && r > n - 1) {
                    break;
                } else if (Math.abs(rabbit[l] - hole[i]) > Math.abs(rabbit[r] - hole[i]) || in[l] == true) {
                    tmp = r;
                    r++;
                } else {
                    tmp = l;
                    l--;
                }
            }
            if (ans == n) {
                break;
            }
        }
        writer.print(ans);
        writer.close();
    }

    static int binary_search(int arr[], int start, int end, int key) {
        int ret = 0;
        int mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            ret = mid;
            if (arr[mid] < key && mid + 1 < arr.length && arr[mid + 1] > key) {
                if (key - arr[mid] > arr[mid + 1] - key) {
                    ret = mid + 1;
                } else {
                    ret = mid;
                }
                break;
            } else if (arr[mid] > key) {
                end = mid - 1;
            } else if (arr[mid] < key) {
                start = mid + 1;
            } else {
                ret = mid;
                break;
            }
        }
        return ret;
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