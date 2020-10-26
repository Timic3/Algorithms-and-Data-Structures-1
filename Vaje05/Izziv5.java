package Vaje05;

import java.util.Scanner;

interface Comparable {
    int compareTo(Oseba o);
}

class Oseba implements Comparable {
    private final static String[] IMENA = {"Ana", "Borut", "Cvetka", "Dusan", "Vukomir", "Sabit", "Milanja", "Franc", "Indir", "Anton"};
    private final static String[] PRIIMKI = {"Vulc", "Kolarec", "Luc", "Hlep", "Vutek", "Novak", "Ostanek", "Kregelj", "Krvavica"};
    static int atr = 0;
    static int smer = 1;

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

    public static void bubblesort(Oseba[] a) {
        trace(a, -1);
        int m;
        for (int i = 0; i < a.length - 1; i = m) {
            m = a.length - 1;
            for (int j = a.length - 1; j > i; --j) {
                if (smer * a[j].compareTo(a[j - 1]) < 0) {
                    Oseba tmp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = tmp;
                    m = j;
                }
            }
            trace(a, m - 1);
        }
    }

    public static void print(Oseba[] a, boolean polniPodatki) {
        for (int i = 0; i < a.length - 1; ++i) {
            System.out.print((polniPodatki ? a[i].podatki() : a[i]) + ", ");
        }
        System.out.println(polniPodatki ? a[a.length - 1].podatki() : a[a.length - 1]);
    }

    public static void trace(Oseba[] a, int divider) {
        for (int i = 0; i < a.length; ++i) {
            System.out.print(a[i] + " ");
            if (i == divider) {
                System.out.print("| ");
            }
        }
        System.out.println();
    }
}


public class Izziv5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n;
        do {
            System.out.print("Vnesi n: ");
            n = scanner.nextInt();
            System.out.println();
        } while (n <= 0);

        Oseba[] tt = new Oseba[n];
        Oseba[] t = new Oseba[n];
        for (int i = 0; i < n; ++i) {
            tt[i] = new Oseba();
        }

        while (true) {
            System.arraycopy(tt, 0, t, 0, tt.length);
            Oseba.print(t, true);

            System.out.print("Vnesi atr: ");
            int atr = scanner.nextInt();
            Oseba.setAtr(atr);
            System.out.println();

            System.out.print("Vnesi smer: ");
            int smer = scanner.nextInt();
            Oseba.setSmer(smer);
            System.out.println();

            System.out.println("Sled:");
            Oseba.bubblesort(t);
            System.out.println();
            System.out.println("Urejeni podatki:");
            Oseba.print(t, false);

            System.out.println("Vnesi 'konec' za konec, ostalo za nadaljuj.");
            String s = scanner.next();

            if (s.equalsIgnoreCase("konec")) {
                break;
            }
        }
    }
}
