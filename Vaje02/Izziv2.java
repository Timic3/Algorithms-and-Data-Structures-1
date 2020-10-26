package Vaje02;

public class Izziv2 {

    public static void main(String[] args) {
        // System.out.println(Arrays.toString(a));
        // System.out.println(findLinear(a, s));
        // System.out.println(findBinary(a, 0, a.length - 1, s));
        // System.out.println("Time: " + timeLinear(12200));
        // System.out.println("Time: " + timeBinary(12200));
        System.out.println("+------------+-----------------+-----------------+");
        System.out.printf("| %10s | %15s | %15s |\n", "n", "linearno", "dvojisko");
        System.out.println("+------------+-----------------+-----------------+");
        long fullLinearTime = 0;
        long fullBinaryTime = 0;
        for (int i = 100_000; i <= 1_000_000; i += 10_000) {
            long linearTime = timeLinear(i);
            long binaryTime = timeBinary(i);
            fullLinearTime += linearTime;
            fullBinaryTime += binaryTime;
            System.out.printf("| %10d | %15d | %15d |\n", i, linearTime, binaryTime);
        }
        System.out.println("+------------+-----------------+-----------------+");
        System.out.printf("| %-10s | %15s | %15s |\n", "avg:", fullLinearTime / 90, fullBinaryTime / 90);
        System.out.println("+------------+-----------------+-----------------+");
    }

    static long timeLinear(int n) {
        int[] a = generateTable(n);
        long fullTime = 0;
        for (int i = 0; i < 1000; i++) {
            long startTime = System.nanoTime();
            int r = (int)(Math.random() * n) + 1;
            findLinear(a, r);
            fullTime += System.nanoTime() - startTime;
        }
        return fullTime / 1000;
    }

    static long timeBinary(int n) {
        int[] a = generateTable(n);
        long fullTime = 0;
        for (int i = 0; i < 1000; i++) {
            long startTime = System.nanoTime();
            int r = (int)(Math.random() * n) + 1;
            findBinary(a, 0, a.length - 1, r);
            fullTime += System.nanoTime() - startTime;
        }
        return fullTime / 1000;
    }

    static int findLinear(int[] a, int v) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == v) {
                return i;
            }
        }
        return -1;
    }

    static int findBinary(int[] a, int l, int r, int v) {
        if (r >= l) {
            int m = (r + l) / 2;
            if (a[m] == v) {
                return m;
            }
            if (a[m] < v) {
                return findBinary(a, m + 1, r, v);
            } else {
                return findBinary(a, l, m - 1, v);
            }
        }
        return -1;
    }

    static int[] generateTable(int n) {
        int[] t = new int[n];
        for (int i = 0; i < n; i++) {
            t[i] = i + 1;
        }
        return t;
    }
}
