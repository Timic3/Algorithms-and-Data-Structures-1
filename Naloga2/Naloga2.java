package Naloga2;

// a1047eab1035d58682a53557e0b2a75edbfd15fd

import java.util.Scanner;

enum Algorithm {
    INSERT, SELECT, BUBBLE, HEAP, MERGE, QUICK,
    RADIX, BUCKET
}

enum Order {
    ASCENDING(1), DESCENDING(-1);

    private final int numerical;

    Order(int numerical) {
        this.numerical = numerical;
    }

    public int getNumerical() {
        return numerical;
    }
}

enum Mode {
    TRACE, COUNT;
}

class Sort {
    private int[] numbers;
    private Mode mode;
    private Order order;
    private int shifts;
    private int comparisons;

    public Sort(String[] input) {
        this.numbers = new int[input.length];
        for (int i = 0; i < input.length; ++i) {
            this.numbers[i] = Integer.parseInt(input[i]);
        }
    }

    // Insertion sort
    public void insert() {
        for (int i = 1; i < this.numbers.length; ++i) {
            int k = this.numbers[i];
            int j = i;
            ++this.shifts;
            while (j > 0 && this.order.getNumerical() * Integer.compare(k, this.numbers[j - 1]) < (++this.comparisons & 0)) {
                this.numbers[j] = this.numbers[j - 1];
                ++this.shifts;
                --j;
            }
            this.numbers[j] = k;
            ++this.shifts;
            this.printTrace(i);
        }
    }

    // Selection sort
    public void select() {
        for (int i = 0; i < this.numbers.length - 1; ++i) {
            int m = i;
            for (int j = i + 1; j < this.numbers.length; ++j) {
                if (this.order.getNumerical() * Integer.compare(this.numbers[j], this.numbers[m]) < (++this.comparisons & 0)) {
                    m = j;
                }
            }
            this.swap(i, m);
            this.printTrace(i);
        }
    }

    // Bubble sort
    public void bubble() {
        int m;
        for (int i = 0; i < this.numbers.length - 1; i = m) {
            m = this.numbers.length - 1;
            for (int j = this.numbers.length - 1; j > i; --j) {
                if (this.order.getNumerical() * Integer.compare(this.numbers[j], this.numbers[j - 1]) < (++this.comparisons & 0)) {
                    this.swap(j, j - 1);
                    m = j;
                }
            }
            this.printTrace(m - 1);
        }
    }

    // Heap sort
    public void heap() {
        // Zgradi kopico od starša
        for (int i = this.numbers.length / 2 - 1; i >= 0; --i) {
            siftDown(i, this.numbers.length - 1);
        }

        // Urejanje
        int last = this.numbers.length - 1;
        while (last > 0) {
            this.printTrace(last);
            this.swap(0, last);
            siftDown(0, --last);
        }
        this.printTrace(last);
    }

    private void siftDown(int i, int heapSize) {
        int branch = 2 * i + 1; // Leva
        while (branch <= heapSize) {
            if (branch < heapSize && this.order.getNumerical() * Integer.compare(this.numbers[branch], this.numbers[branch + 1]) < (++this.comparisons & 0)) {
                ++branch; // Pojdi na desno
            }
            if (this.order.getNumerical() * Integer.compare(this.numbers[branch], this.numbers[i]) <= (++this.comparisons & 0)) {
                break;
            }
            this.swap(i, branch);
            i = branch;
            branch = 2 * i + 1;
        }
    }

    // Merge sort
    public void merge(int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            this.printTrace(middle, left, right + 1);

            // Razpolovi polje
            this.merge(left, middle);
            this.merge(middle + 1, right);

            // Združi polovici
            this.mergeHalves(left, middle, right);

            this.printTrace(-1, left, right + 1);
        }
    }

    private void mergeHalves(int left, int middle, int right) {
        int k = middle - left + 1;
        int l = right - middle;
        int x = 0, y = 0, t = left;

        int[] a = new int[k];
        int[] b = new int[l];
        for (int i = 0; i < a.length; i++) {
            a[i] = this.numbers[left + i];
            ++this.shifts;
        }
        for (int i = 0; i < b.length; i++) {
            b[i] = this.numbers[middle + 1 + i];
            ++this.shifts;
        }

        while (x < k && y < l) {
            this.numbers[t++] = (this.order.getNumerical() * Integer.compare(a[x], b[y]) <= (++this.comparisons & 0)) ? a[x++] : b[y++];
            ++this.shifts;
        }

        while (x < k) {
            this.numbers[t] = a[x++];
            ++this.shifts;
            t++;
        }

        while (y < l) {
            this.numbers[t] = b[y++];
            ++this.shifts;
            t++;
        }
    }

    // Quick sort
    public void quick(int left, int right) {
        if (left < right) {
            int pivot = this.partition(left, right);

            this.printTrace(pivot, left, right + 1, true);

            quick(left, pivot - 1);
            quick(pivot + 1, right);
        }
    }

    private int partition(int left, int right) {
        int pivot = this.numbers[left];
        ++this.shifts;
        int l = left, r = right + 1;
        while (true) {
            do {
                l++;
            } while (this.order.getNumerical() * Integer.compare(this.numbers[l], pivot) < (++this.comparisons & 0) && l < right);
            do {
                r--;
            } while (this.order.getNumerical() * Integer.compare(this.numbers[r], pivot) > (++this.comparisons & 0));
            if (l >= r) {
                break;
            }
            this.swap(l, r);
        }
        this.swap(left, r);
        return r;
    }

    // Count sort
    public void count(int exponent) {
        int[] result = new int[this.numbers.length];
        int[] bucket = new int[10]; // Štetje števk

        // Preštej števke, glede na eksponent
        for (int i = 0; i < this.numbers.length; ++i) {
            ++bucket[this.slot(i, exponent)];
        }

        // Spremeni v kumulativno tabelo števk
        for (int i = 1; i < bucket.length; ++i) {
            bucket[i] += bucket[i - 1];
        }

        // Zgradi novo tabelo glede na preštete vrednosti
        for (int i = this.numbers.length - 1; i >= 0; --i) {
            result[--bucket[this.slot(i, exponent)]] = this.numbers[i];
        }

        this.numbers = result;
    }

    private int slot(int i, int exponent) {
        ++this.comparisons;
        ++this.shifts;
        return this.order == Order.DESCENDING ? 9 - (this.numbers[i] / exponent) % 10 : (this.numbers[i] / exponent) % 10;
    }

    // Radix sort
    public void radix() {
        int max = this.numbers[0];
        for (int i = 1; i < this.numbers.length; ++i) {
            if (this.numbers[i] > max) {
                max = this.numbers[i];
            }
        }
        this.printTrace(-1);
        for (int exponent = 1; max / exponent > 0; exponent *= 10) {
            this.count(exponent);
            this.printTrace(-1);
        }
    }

    // Bucket sort
    public void bucket() {
        int k = this.numbers.length / 2;
        int min = this.numbers[0];
        int max = this.numbers[0];
        for (int i = 1; i < this.numbers.length; ++i) {
            if (Integer.compare(this.numbers[i], min) < (++this.comparisons & 0)) {
                min = this.numbers[i];
            } else if (Integer.compare(this.numbers[i], max) > (++this.comparisons & 0)) {
                max = this.numbers[i];
            }
        }
        double v = (double) (max - min + 1) / k;

        this.printTrace(-1);

        int[] bucket = new int[k];
        int[] result = new int[this.numbers.length];

        for (int number : this.numbers) {
            int slot = (int) ((number - min) / v);
            if (this.order == Order.DESCENDING) {
                slot = (k - 1) - (int) ((number - min) / v);
            }
            ++this.shifts;
            ++this.comparisons;
            ++bucket[slot];
        }

        // Spremeni v kumulativno tabelo števk
        for (int i = 1; i < bucket.length; ++i) {
            bucket[i] += bucket[i - 1];
        }

        // Zgradi novo tabelo glede na preštete vrednosti
        for (int i = this.numbers.length - 1; i >= 0; --i) {
            int slot = (int) ((this.numbers[i] - min) / v);
            if (this.order == Order.DESCENDING) {
                slot = (k - 1) - (int) ((this.numbers[i] - min) / v);
            }
            ++this.shifts;
            ++this.comparisons;
            result[--bucket[slot]] = this.numbers[i];
        }

        this.numbers = result;

        this.printTrace(bucket);

        this.insert();
    }

    public void execute(Algorithm algorithm) {
        this.algorithm(algorithm);
        if (this.mode == Mode.COUNT) {
            this.printBenchmarks();
            this.algorithm(algorithm);
            this.printBenchmarks();
            this.order = this.order == Order.ASCENDING ? Order.DESCENDING : Order.ASCENDING;
            this.algorithm(algorithm);
            System.out.print(this.shifts + " " + this.comparisons);
        }
    }

    public void algorithm(Algorithm algorithm) {
        switch (algorithm) {
            case INSERT:
                this.printTrace(-1);
                this.insert();
                break;
            case SELECT:
                this.printTrace(-1);
                this.select();
                break;
            case BUBBLE:
                this.printTrace(-1);
                this.bubble();
                break;
            case HEAP:
                this.printTrace(-1);
                this.heap();
                break;
            case MERGE:
                this.printTrace(-1);
                this.merge(0, this.numbers.length - 1);
                break;
            case QUICK:
                this.printTrace(-1);
                this.quick(0, this.numbers.length - 1);
                this.printTrace(-1);
                break;
            case RADIX:
                this.radix();
                break;
            case BUCKET:
                this.bucket();
                break;
            default:
                System.out.println("Algorithm not implemented");
        }
    }

    private void resetBenchmarks() {
        this.shifts = 0;
        this.comparisons = 0;
    }

    private void printBenchmarks() {
        System.out.print(this.shifts + " " + this.comparisons + " | ");
        this.resetBenchmarks();
    }

    private void printTrace(int divider, int start, int end, boolean isPivot) {
        if (this.mode == Mode.TRACE) {
            StringBuilder trace = new StringBuilder();
            for (int i = start; i < end; ++i) {
                if (isPivot && i == divider) {
                    trace.append("| ");
                }
                trace.append(this.numbers[i]).append(" ");
                if (i == divider) {
                    trace.append("| ");
                }
            }
            System.out.println(trace.toString().trim());
        }
    }

    private void printTrace(int[] bucket) {
        if (this.mode == Mode.TRACE) {
            StringBuilder trace = new StringBuilder();
            int last = bucket[0];
            for (int i = 1; i < bucket.length; ++i) {
                while (last < bucket[i]) {
                    trace.append(this.numbers[last++]).append(" ");
                }
                trace.append("| ");
            }
            for (int i = last; i < this.numbers.length; ++i) {
                trace.append(this.numbers[i]).append(" ");
            }
            System.out.println(trace.toString().trim());
        }
    }

    private void printTrace(int divider, int start, int end) {
        printTrace(divider, start, end, false);
    }

    private void printTrace(int divider) {
        printTrace(divider, 0, this.numbers.length, false);
    }

    private void swap(int a, int b) {
        int tmp = this.numbers[a];
        this.numbers[a] = this.numbers[b];
        this.numbers[b] = tmp;
        this.shifts += 3;
    }

    void setMode(String mode) {
        this.mode = mode.equals("count") ? Mode.COUNT : Mode.TRACE;
    }

    void setOrder(String order) {
        this.order = order.equals("down") ? Order.DESCENDING : Order.ASCENDING;
    }
}

public class Naloga2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] arguments = scanner.nextLine().split("\\s+");
        if (arguments.length != 3) {
            System.out.println("Not enough arguments! (Required 3, but got " + arguments.length + ")");
            System.out.println("Syntax: mode algorithm order");
            return;
        }

        String[] input = scanner.nextLine().split("\\s+");
        Sort sort = new Sort(input);
        sort.setMode(arguments[0]); // Nastavi način delovanja
        sort.setOrder(arguments[2]); // Nastavi smer urejanja

        switch (arguments[1]) {
            case "insert":
                sort.execute(Algorithm.INSERT);
                break;
            case "select":
                sort.execute(Algorithm.SELECT);
                break;
            case "bubble":
                sort.execute(Algorithm.BUBBLE);
                break;
            case "heap":
                sort.execute(Algorithm.HEAP);
                break;
            case "merge":
                sort.execute(Algorithm.MERGE);
                break;
            case "quick":
                sort.execute(Algorithm.QUICK);
                break;
            case "radix":
                sort.execute(Algorithm.RADIX);
                break;
            case "bucket":
                sort.execute(Algorithm.BUCKET);
                break;
            default:
                System.out.println("Algorithm not implemented");
        }
        scanner.close();
    }
}
