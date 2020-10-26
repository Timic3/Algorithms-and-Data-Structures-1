package Vaje06;

import java.util.Scanner;

class CollectionException extends Exception {
    public CollectionException(String msg) {
        super(msg);
    }
}

// 3 metode
interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";
    // static final String ERR_MSG_FULL = "Collection is full.";
    boolean isEmpty();
    // boolean isFull();
    int count();
    String toString();
}

// 4 metode
interface Sequence<T extends Comparable> extends Collection {
    static final String ERR_MSG_INDEX = "Wrong index in sequence.";
    T get(int i) throws CollectionException;
    T set(int i, T x) throws CollectionException;
    void insert(int i, T x) throws CollectionException;
    T delete(int i) throws CollectionException;
}

// 4 metode
interface ComparableSequence<T extends Comparable> extends Sequence<T> {

}

class LinkedSequence<T extends Comparable> implements ComparableSequence<T> {
    class Node {
        T value;
        Node next, prev;
    }

    Node first;
    int size;

    private int shifts;
    private int comparisons;

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int count() {
        return size;
    }

    @Override
    public T get(int i) throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
        if (i >= size) {
            throw new CollectionException(Sequence.ERR_MSG_INDEX);
        }
        if (i == 0) {
            return first.value;
        }
        Node current = first;
        while (--i > 0 && current.next != null) {
            current = current.next;
        }
        return current.next.value;
    }

    @Override
    public T set(int i, T x) throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
        if (i >= size) {
            throw new CollectionException(Sequence.ERR_MSG_INDEX);
        }

        T oldValue;
        if (i == 0) {
            oldValue = first.value;
            first.value = x;
        } else {
            Node current = first;
            while (--i > 0 && current.next != null) {
                current = current.next;
            }
            oldValue = current.next.value;
            current.next.value = x;
        }
        return oldValue;
    }

    @Override
    public void insert(int i, T x) throws CollectionException {
        if (i > size) {
            throw new CollectionException(Sequence.ERR_MSG_INDEX);
        }
        Node node = new Node();
        node.value = x;
        if (first == null) {
            first = node;
        } else {
            Node current = first;
            if (i == 0) {
                current.prev = node;
                first = node;
                first.next = current;
            } else {
                while (--i > 0 && current.next != null) {
                    current = current.next;
                }
                Node temp = current.next;
                if (temp != null) {
                    temp.prev = node;
                }
                current.next = node;
                current.next.prev = current;
                current.next.next = temp;
            }
        }
        ++size;
    }

    @Override
    public T delete(int i) throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
        if (i >= size) {
            throw new CollectionException(Sequence.ERR_MSG_INDEX);
        }
        Node current = first;
        Node temp;
        if (i == 0) {
            temp = first;
            if (temp.next != null) {
                first = temp.next;
                first.prev = null;
            } else {
                first = null;
            }
        } else {
            while (--i > 0 && current.next != null) {
                current = current.next;
            }
            temp = current.next;
            current.next = temp.next;
            temp.prev = current.next;
            if (current.next != null) {
                current.next.prev = current;
            }
        }
        --size;
        return temp.value;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return Collection.ERR_MSG_EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        Node current = first;
        while (current != null) {
            if (current.prev != null) {
                sb.append(current.value).append(" (").append(current.prev.value).append(")").append(" --> ");
            } else {
                sb.append(current.value).append(" --> ");
            }
            current = current.next;
        }
        return sb.toString();
    }

    public void quick(int left, int right) throws CollectionException {
        if (left < right) {
            int pivot = this.partition(left, right);
            if (right - left < Oseba.getV()) {
                insert(left, right + 1);
                return;
            }

            quick(left, pivot - 1);
            quick(pivot + 1, right);
        }
    }

    private int partition(int left, int right) throws CollectionException {
        Oseba pivot = (Oseba) this.get(left);
        ++this.shifts;
        int l = left, r = right + 1;
        while (true) {
            do {
                l++;
            } while (Oseba.getSmer() * this.get(l).compareTo(pivot) < (++this.comparisons & 0) && l < right);
            do {
                r--;
            } while (Oseba.getSmer() * this.get(r).compareTo(pivot) > (++this.comparisons & 0));
            if (l >= r) {
                break;
            }
            this.swap(l, r);
        }
        this.swap(left, r);
        return r;
    }

    public void insert(int left, int right) throws CollectionException {
        for (int i = left; i < right; ++i) {
            T k = this.get(i);
            int j = i;
            ++this.shifts;
            while (j > 0 && Oseba.getSmer() * k.compareTo((Oseba) this.get(j - 1)) < (++this.comparisons & 0)) {
                this.set(j, this.get(j - 1));
                ++this.shifts;
                --j;
            }
            this.set(j, k);
            ++this.shifts;
        }
    }

    public void swap(int a, int b) throws CollectionException {
        T A = this.get(a);
        T B = this.get(b);
        this.set(a, B);
        this.set(b, A);
    }

    public int getShifts() {
        return this.shifts;
    }

    public int getComparisons() {
        return this.comparisons;
    }
}

interface Comparable {
    int compareTo(Oseba o);
}

class Oseba implements Comparable {
    private final static String[] IMENA = {"Ana", "Borut", "Cvetka", "Dusan", "Vukomir", "Sabit", "Milanja", "Franc", "Indir", "Anton"};
    private final static String[] PRIIMKI = {"Vulc", "Kolarec", "Luc", "Hlep", "Vutek", "Novak", "Ostanek", "Kregelj", "Krvavica"};
    static int atr = 0;
    static int smer = 1;
    static int v = 1;

    String ime, priimek;
    int letoR;

    public Oseba(String ime) {
        this.ime = ime;
        this.priimek = PRIIMKI[(int) (Math.random() * PRIIMKI.length)];
        this.letoR = (int) (Math.random() * (2019 - 1910 + 1)) + 1910;
    }

    public Oseba() {
        this.ime = IMENA[(int) (Math.random() * IMENA.length)];
        this.priimek = PRIIMKI[(int) (Math.random() * PRIIMKI.length)];
        this.letoR = (int) (Math.random() * (2019 - 1910 + 1)) + 1910;
    }

    public String toString() {
        switch (atr) {
            case 0:
                return this.ime;
            case 1:
                return this.priimek;
            case 2:
                return Integer.toString(this.letoR);
        }
        return "";
    }

    public String podatki() {
        return this.ime + " " + this.priimek + " (" + this.letoR + ")";
    }

    @Override
    public int compareTo(Oseba o) {
        switch (atr) {
            case 0:
                return this.ime.compareTo(o.ime);
            case 1:
                return this.priimek.compareTo(o.priimek);
            case 2:
                return this.letoR - o.letoR;
        }
        return 0;
    }

    public static void setAtr(int a) {
        if (a >= 0 && a <= 2) {
            atr = a;
        }
    }

    public static int getAtr() {
        return atr;
    }

    public static void setSmer(int d) {
        if (d == 1 || d == -1) {
            smer = d;
        }
    }

    public static int getSmer() {
        return smer;
    }

    public static void setV(int d) {
        if (v >= 0) {
            v = d;
        }
    }

    public static int getV() {
        return v;
    }

    public static void print(LinkedSequence<Oseba> a, boolean polniPodatki) throws CollectionException {
        for (int i = 0; i < a.count() - 1; ++i) {
            System.out.print((polniPodatki ? a.get(i).podatki() : a.get(i)) + ", ");
        }
        System.out.println(polniPodatki ? a.get(a.count() - 1).podatki() : a.get(a.count() - 1));
    }

    public static void trace(LinkedSequence<Oseba> a, int divider) throws CollectionException {
        for (int i = 0; i < a.count(); ++i) {
            System.out.print(a.get(i) + " ");
            if (i == divider) {
                System.out.print("| ");
            }
        }
        System.out.println();
    }
}

public class Izziv6 {

    public static void main(String[] args) throws CollectionException {
        Scanner scanner = new Scanner(System.in);

        int n;
        do {
            System.out.print("Vnesi n: ");
            n = scanner.nextInt();
            System.out.println();
        } while (n <= 0);

        LinkedSequence<Oseba> z1 = new LinkedSequence<>();
        LinkedSequence<Oseba> z2 = new LinkedSequence<>();
        for (int i = 0; i < n; ++i) {
            z1.insert(0, new Oseba());
        }

        while (true) {
            for (int i = 0; i < z1.count(); ++i) {
                z2.insert(i, z1.get(i));
            }

            System.out.print("Vnesi atr: ");
            int atr = scanner.nextInt();
            Oseba.setAtr(atr);
            System.out.println();

            System.out.print("Vnesi smer: ");
            int smer = scanner.nextInt();
            Oseba.setSmer(smer);
            System.out.println();

            Oseba.print(z2, true);

            System.out.print("Vnesi v: ");
            int v = scanner.nextInt();
            Oseba.setV(v);

            System.out.println("Sled:");
            z2.quick(0, z2.count() - 1);
            System.out.println();
            System.out.println("Urejeni podatki:");
            Oseba.print(z2, false);

            System.out.println("Premiki: " + z2.getShifts() + ", primerjave: " + z2.getComparisons());

            System.out.println("Vnesi 'konec' za konec, ostalo za nadaljuj.");
            String s = scanner.next();

            if (s.equalsIgnoreCase("konec")) {
                break;
            }
        }
    }
}
