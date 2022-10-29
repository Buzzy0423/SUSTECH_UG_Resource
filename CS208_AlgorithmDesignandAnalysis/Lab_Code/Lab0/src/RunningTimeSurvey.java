import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.io.File;
import java.lang.reflect.Method;

import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class RunningTimeSurvey {
    //             task name            function name             run times upper
    static String[][] taskList = {
            {"LinearTimeTest", "linearTime", "10000000"},
            {"LinearTimeTest", "linearTimeCollections", "10000000"},
            {"NlognTimeTest", "NlognTime", "1000000"},
            {"QuadraticTimeTest", "QuadraticTime", "100000"},
            {"CubicTimeTest", "CubicTime", "1000"},
            {"ExponentialTimeTest", "ExponentialTime", "29"},
            { "FactorialTimeTest",   "FactorialTime",          "10"}
    };

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String osName = System.getProperty("os.name");
        System.out.println(osName);
        try {
            File xlsFile = new File("RunningTimeSurvey.xls");
            // Create a workbook
            WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);
            // Create a worksheet
            WritableSheet sheet = workbook.createSheet("RunningTime", 0);
            // the first row
            int max_upper = 0, max_giant_upper = 0;
            for (String[] taskInfo : taskList) {
                max_upper = Math.max(max_upper, Integer.parseInt(taskInfo[2]));
                if (taskInfo[0].equals("ExponentialTimeTest") || taskInfo[0].equals("FactorialTimeTest")) {
                    max_giant_upper = Math.max(max_giant_upper, Integer.parseInt(taskInfo[2]));
                }
            }
            for (int j = 1, n = 10; n <= max_upper; j++, n *= 10) {
                sheet.addCell(new Label(j + 1, 0, "n = " + n));
            }
            int next_row = 1;
            for (int i = 0; i < taskList.length; i++) {
                String[] taskInfo = taskList[i];
                if (taskInfo[0].equals("ExponentialTimeTest") || taskInfo[0].equals("FactorialTimeTest")) {
                    continue;
                }
                Class<?> me = Class.forName(Thread.currentThread().getStackTrace()[1].getClassName());
                Method method = me.getMethod(taskInfo[1], int.class);
                int upper = Integer.parseInt(taskInfo[2]);
                sheet.addCell(new Label(0, i + 1, taskInfo[0]));
                sheet.addCell(new Label(1, i + 1, taskInfo[1]));
                next_row = i + 1;
                for (int j = 1, n = 1; Math.pow(10, j) <= upper; j++) {
                    n = 10 * n;
                    Long time = (Long) method.invoke(null, n);
                    // add data to sheet
                    sheet.addCell(new Label(j + 1, i + 1, time.toString()));
                }
            }
            ++next_row;
            for (int j = 1, n = 10; n <= max_giant_upper; j++, ++n) {
                sheet.addCell(new Label(j + 1, next_row, "n = " + n));
            }
            ++next_row;
            for (String[] taskInfo : taskList) {
                if (!(taskInfo[0].equals("ExponentialTimeTest") || taskInfo[0].equals("FactorialTimeTest"))) {
                    continue;
                }
                Class<?> me = Class.forName(Thread.currentThread().getStackTrace()[1].getClassName());
                Method method = me.getMethod(taskInfo[1], int.class);
                int upper = Integer.parseInt(taskInfo[2]);
                sheet.addCell(new Label(0, next_row, taskInfo[0]));
                sheet.addCell(new Label(1, next_row, taskInfo[1]));
                for (int j = 1, n = 10; n <= upper; j++) {
                    ++n;
                    Long time = (Long) method.invoke(null, n);
                    // add data to sheet
                    sheet.addCell(new Label(j + 1, next_row, time.toString()));
                }
                ++next_row;
            }
            workbook.write();
            workbook.close();
            System.out.println("Finish");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static long linearTimeCollections(int n) {
        ArrayList<Long> arrayList = new ArrayList<Long>(n);
        generateArrayList(n, arrayList);
        long timeStart = System.currentTimeMillis();
        getMax(n, arrayList);
        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long linearTime(int n) {
        long[] list = new long[n];
        generateList(n, list);
        long timeStart = System.currentTimeMillis();
        getMax(n, list);
        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long getMax(long n, long[] list) {
        long max = list[0];
        for (int i = 1; i < n; i++) {
            if (list[i] > max) {
                max = list[i];
            }
        }
        return max;
    }

    public static void generateList(int n, long[] list) {
        for (int i = 0; i < n; i++) {
            list[i] = i;
        }
        // shuffle
        Random rnd = new Random();
        for (int i = list.length; i > 1; i--) {
            int j = rnd.nextInt(i);
            long temp = list[j];
            list[j] = list[i - 1];
            list[i - 1] = temp;
        }
    }

    public static void generateArrayList(int n, ArrayList<Long> arrayList) {
        for (long i = 0; i < n; i++) {
            arrayList.add(i);
        }
        // shuffle
        Collections.shuffle(arrayList);
    }

    public static long getMax(long n, ArrayList<Long> arrayList) {
        long max = arrayList.get(0);
        for (int i = 1; i < n; i++) {
            if (arrayList.get(i) > max) {
                max = arrayList.get(i);
            }
        }
        return max;
    }

    public static long NlognTime(int n) {
        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        long timeStart = System.currentTimeMillis();
        Arrays.sort(arr);
        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long QuadraticTime(int n) {
        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        long timeStart = System.currentTimeMillis();
        if (n > 1) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n - 1 - i; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int tmp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = tmp;
                    }
                }
            }
        }
        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long CubicTime(int n) {
        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        int a = 0;
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    a += arr[k];
                }
            }
        }
        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long ExponentialTime(int n) {
        long timeStart = System.currentTimeMillis();
        long a = func(n);
        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long func(long n) {
        if (n == 0) {
            return 1;
        }
        return func(n - 1) + func(n - 1);
    }

    public static long FactorialTime(int n) {
        int f = 1;
        for (int i = 1; i <= n; i++) {
            f *= i;
        }
        int arr[] = new int[f];
        for (int i = 0; i < f; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        int a = 0;
        long timeStart = System.currentTimeMillis();
        for (int tmp : arr) {
            if (tmp % 3 == 1) {
                a++;
            }
        }
        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }
}