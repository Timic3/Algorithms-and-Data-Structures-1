package Vaje07;

import java.util.Scanner;

class Sudoku {
    int[][] matrix;
    boolean[][] bold;

    public Sudoku(int k) {
        this.matrix = new int[9][9];
        this.bold = new boolean[9][9];
        this.generate(k);
    }

    private void generate(int k) {
        while (k > 0) {
            int x = (int) (Math.random() * 9);
            int y = (int) (Math.random() * 9);
            int z = (int) (Math.random() * 9) + 1;

            if (this.isValidPlacement(x, y, z)) {
                this.matrix[y][x] = z;
                this.bold[y][x] = true;
                --k;
            }
        }
    }

    public boolean solve() {
        // Ali je že rešen?
        boolean isSolved = true;
        int i, j = 0;

        outerLoop: // Labele - šele zdaj sem našel, da lahko to naredimo - zanimivo :)
        for (i = 0; i < 9; ++i) {
            for (j = 0; j < 9; ++j) {
                if (this.matrix[i][j] == 0) {
                    isSolved = false;
                    break outerLoop;
                }
            }
        }

        if (isSolved) {
            return true;
        }

        for (int k = 1; k < 10; ++k) {
            if (isValidPlacement(j, i, k)) {
                this.matrix[i][j] = k;
                if (this.solve()) {
                    return true;
                }

                // Backtrack
                this.matrix[i][j] = 0;
            }
        }

        return false;
    }

    private boolean isValidPlacement(int x, int y, int k) {
        // Osi so obrnjene v poljih!

        // Preveri, če je polje prazno
        if (this.matrix[y][x] != 0) {
            return false;
        }

        // Preveri X in Y os
        for (int i = 0; i < 9; ++i) {
            if (this.matrix[y][i] == k || this.matrix[i][x] == k) {
                return false;
            }
        }

        // Preveri kvadrante
        int quadrantX = x - x % 3;
        int quadrantY = y - y % 3;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (this.matrix[quadrantY + i][quadrantX + j] == k) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();
        for (int i = 0; i < 9; ++i) {
            if (i == 0) {
                matrix.append("-------------------------");
                matrix.append("\n");
            }
            for (int j = 0; j < 9; ++j) {
                int k = this.matrix[i][j];
                if (j == 0) {
                    matrix.append("| ");
                }

                if (this.bold[i][j]) {
                    matrix.append("\033[1;33m\033[1m"); // Odebeljeno, deluje v IntelliJ
                }
                matrix.append(k == 0 ? " " : k).append(" ");
                if (this.bold[i][j]) {
                    matrix.append("\033[0m"); // Nazaj normalno
                }

                if (j % 3 == 2) {
                    matrix.append("| ");
                }
            }
            if (i % 3 == 2) {
                matrix.append("\n");
                matrix.append("-------------------------");
            }
            matrix.append("\n");
        }
        return matrix.toString();
    }
}

public class Izziv7 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Z rumeno so odebeljene fiksne (generirane) številke.");
        System.out.print("Vnesi koliko fiksnih številk napolnimo: ");
        int k = scanner.nextInt();

        Sudoku sudoku = new Sudoku(k);
        System.out.println(sudoku);

        if (sudoku.solve()) {
            System.out.println();
            System.out.println("Sudoku je bil uspešno rešen.");
            System.out.println(sudoku);
        } else {
            System.out.println("Sudoku je nerešljiv.");
        }

        scanner.close();
    }
}
