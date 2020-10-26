package Vaje03;

class CompleteBinaryTreeDrawer {
    int size;
    int x[];
    int y[];

    CompleteBinaryTreeDrawer(int size) {
        this.x = new int[size];
        this.y = new int[size];
        this.size = size;
        traverse(0, 0, 0);
    }

    int traverse(int i, int x, int y) {
        if (2 * i + 1 < size) {
            x = traverse(2 * i + 1, x, y + 1);
        }
        this.x[i] = x;
        this.y[i] = y;
        if (2 * i + 2 < size) {
            x = traverse(2 * i + 2, x + 1, y + 1) - 1;
        }
        return x + 1;
    }

    void drawNode(int i) {
        StdDraw.filledCircle(x[i], y[i], 0.2);
    }

    void drawEdgeToParent(int i) {
        int toX = x[(i - 1) / 2], toY = y[(i - 1) / 2];
        int fromX = x[i], fromY = y[i];
        StdDraw.line(fromX, fromY, toX, toY);
    }

    void drawLevelorder() {
        for (int i = 0; i < size; i++) {
            drawEdgeToParent(i);
            drawNode(i);
        }
    }

    void drawPreorder(int i) {
        drawEdgeToParent(i);
        drawNode(i);
        if (2 * i + 1 < size) {
            drawInorder(2 * i + 1);
        }
        if (2 * i + 2 < size) {
            drawInorder(2 * i + 2);
        }
    }

    void drawInorder(int i) {
        if (2 * i + 1 < size) {
            drawInorder(2 * i + 1);
        }
        drawEdgeToParent(i);
        drawNode(i);
        if (2 * i + 2 < size) {
            drawInorder(2 * i + 2);
        }
    }

    void drawPostorder(int i) {
        if (2 * i + 1 < size) {
            drawInorder(2 * i + 1);
        }
        if (2 * i + 2 < size) {
            drawInorder(2 * i + 2);
        }
        drawEdgeToParent(i);
        drawNode(i);
    }
}

public class Izziv3 {
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        StdDraw.setCanvasSize(1500, 800);
        StdDraw.setXscale(-1, size);
        StdDraw.setYscale((int) (Math.log(size + 1) / Math.log(2)) + 1, -1);
        CompleteBinaryTreeDrawer ctd = new CompleteBinaryTreeDrawer(size);
        // ctd.drawLevelorder();
        // ctd.drawPreorder(0);
        ctd.drawInorder(0);
        // ctd.drawPostorder(0);
    }
}
