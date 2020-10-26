package Naloga3;

import java.util.Scanner;

class ArrayDeque<T> {
    private T[] items;
    private int size = 0;
    private int capacity = 10;

    @SuppressWarnings("unchecked")
    public ArrayDeque(int capacity) {
        this.capacity = capacity;
        this.items = (T[]) new Object[this.capacity];
    }

    @SuppressWarnings("unchecked")
    public ArrayDeque() {
        this.items = (T[]) new Object[this.capacity];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public T front() {
        return this.items[0];
    }

    public T back() {
        return this.items[this.size - 1];
    }

    public void push(T item) {
        if (size() >= this.capacity) {
            resize(this.capacity * 2);
        }

        this.items[this.size++] = item;
    }

    public void pushSorted(T item) {
        if (size() >= this.capacity) {
            resize(this.capacity * 2);
        }

        if (size() == 0) {
            this.items[this.size++] = item;
        } else {
            int size = this.size;
            for (int i = 0; i < size; ++i) {
                if ((int) this.items[i] > (int) item) {
                    System.arraycopy(this.items, i, this.items, i + 1, this.size++ - i);
                    this.items[i] = item;
                    break;
                } else if (i == this.size - 1) {
                    this.items[this.size++] = item;
                }
            }
        }
    }

    public T pop() {
        T back = this.items[--this.size];
        this.items[this.size] = null;
        return back;
    }

    public void enqueue(T item) {
        this.push(item);
    }

    public T dequeue() {
        T front = this.items[0];
        this.items[0] = null;

        if (this.size - 1 >= 0) {
            System.arraycopy(this.items, 1, this.items, 0, this.size - 1);
            this.items[--this.size] = null;
        }

        return front;
    }

    public void clear() {
        for (int i = 0; i < this.size; ++i) {
            this.items[i] = null;
        }
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder items = new StringBuilder();
        for (int i = 0; i < this.size; ++i) {
            items.append(this.items[i]).append(" ");
        }
        return items.toString();
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        this.capacity = newCapacity;
        T[] newArray = (T[]) new Object[this.capacity];
        if (size() >= 0) {
            System.arraycopy(this.items, 0, newArray, 0, size());
        }
        this.items = newArray;
    }
}

class Graph {
    private int[][] matrix;
    private int size;
    private int edges;
    private boolean directed;

    private boolean[] visited;

    int[] hamiltonianPath;

    public Graph(int size, boolean directed) {
        this.size = size;
        this.matrix = new int[this.size][this.size];
        this.visited = new boolean[this.size];
        this.directed = directed;
    }

    public boolean isDirected() {
        return this.directed;
    }

    public void addEdge(int a, int b) {
        if (this.matrix[a][b] == 0) {
            ++this.edges;
            this.matrix[a][b] = 1;
            if (!this.directed) {
                this.matrix[b][a] = 1;
            }
        }
    }

    public void info() {
        System.out.println(this.size + " " + this.edges);
        for (int i = 0; i < this.size; ++i) {
            if (this.directed) {
                System.out.println(i + " " + this.getLevel(i, false) + " " + this.getLevel(i, true));
            } else {
                System.out.println(i + " " + this.getLevel(i, false));
            }
        }
    }

    public void walk(int k) {
        int[][] result = this.matrix;
        for (int i = 0; i < k - 1; ++i) {
            result = this.multiply(result, this.matrix);
        }
        this.matrix = result;
        this.printMatrix();
    }

    public void dfs() {
        ArrayDeque<Integer> entry = new ArrayDeque<Integer>(this.size);
        ArrayDeque<Integer> exit = new ArrayDeque<Integer>(this.size);
        this.visited = new boolean[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.dfs(i, entry, exit);
        }
        System.out.println(entry);
        System.out.println(exit);
    }

    public void bfs() {
        this.visited = new boolean[this.size];
        ArrayDeque<Integer> entry = new ArrayDeque<Integer>(this.size);
        for (int i = 0; i < this.size; ++i) {
            if (!this.visited[i]) {
                this.bfs(i, entry);
            }
        }
        System.out.println(entry);
    }

    public void sp(int k) {
        this.visited = new boolean[this.size];

        int[] distance = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            distance[i] = -1;
        }
        this.sp(k, distance);
        for (int i = 0; i < this.size; ++i) {
            System.out.println(i + " " + distance[i]);
        }
    }

    public void comp() {
        this.visited = new boolean[this.size];
        if (this.directed) {
            ArrayDeque<Integer> entry = new ArrayDeque<Integer>(this.size);
            ArrayDeque<Integer> exit = new ArrayDeque<Integer>(this.size);

            // Kosaraju-ov algoritem
            // Izračunaj izstopni red obiskovanja
            for (int i = 0; i < this.size; ++i) {
                this.dfs(i, entry, exit);
            }

            // Transponiraj graf (obrni povezave)
            this.matrix = this.transpose();

            // Zaporedoma izvajaj DFS
            ArrayDeque<Integer> group = new ArrayDeque<Integer>(this.size);
            this.visited = new boolean[this.size];
            String[] components = new String[this.size];
            while (!exit.isEmpty()) {
                int k = exit.pop();
                this.comp(k, group);
                if (group.size() > 0) {
                    components[group.front()] = group.toString();
                    group.clear();
                }
            }
            for (int i = 0; i < this.size; ++i) {
                if (components[i] != null) {
                    System.out.println(components[i]);
                }
            }
        } else {
            ArrayDeque<Integer> group = new ArrayDeque<Integer>(this.size);
            for (int i = 0; i < this.size; ++i) {
                this.comp(i, group);
                if (group.size() > 0) {
                    System.out.println(group);
                    group.clear();
                }
            }
        }
    }

    public void printMatrix() {
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                System.out.print(this.matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int getLevel(int i, boolean exit) {
        int level = 0;
        for (int j = 0; j < this.size; ++j) {
            if ((exit ? this.matrix[j][i] : this.matrix[i][j]) == 1) {
                ++level;
            }
        }
        return level;
    }

    private int[][] multiply(int[][] a, int[][] b) {
        int[][] r = new int[this.size][this.size];
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                for (int k = 0; k < this.size; ++k) {
                    r[i][j] = r[i][j] + a[i][k] * b[k][j];
                }
            }
        }
        return r;
    }

    private int[][] transpose() {
        int[][] r = new int[this.size][this.size];
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                r[j][i] = this.matrix[i][j];
            }
        }
        return r;
    }

    private void dfs(int i, ArrayDeque<Integer> entry, ArrayDeque<Integer> exit) {
        if (!this.visited[i]) {
            this.visited[i] = true;
            entry.push(i);

            for (int j = 0; j < this.matrix[i].length; ++j) {
                if (this.matrix[i][j] == 1 && !this.visited[j]) {
                    this.dfs(j, entry, exit);
                }
            }
            exit.push(i);
        }
    }

    private void bfs(int i, ArrayDeque<Integer> entry) {
        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();

        this.visited[i] = true;
        queue.enqueue(i);

        while (queue.size() != 0) {
            i = queue.dequeue();
            entry.push(i);

            for (int j = 0; j < this.matrix[i].length; ++j) {
                if (this.matrix[i][j] == 1 && !this.visited[j]) {
                    this.visited[j] = true;
                    queue.enqueue(j);
                }
            }
        }
    }

    private void sp(int i, int[] distance) {
        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();

        distance[i] = 0;

        this.visited[i] = true;
        queue.enqueue(i);

        while (queue.size() != 0) {
            i = queue.dequeue();

            for (int j = 0; j < this.matrix[i].length; ++j) {
                if (this.matrix[i][j] == 1 && !this.visited[j]) {
                    this.visited[j] = true;
                    distance[j] = distance[i] + 1;
                    queue.enqueue(j);
                }
            }
        }
    }

    // Različica DFS, ki sortira ob vstavljanju
    public void comp(int i, ArrayDeque<Integer> entry) {
        if (!this.visited[i]) {
            this.visited[i] = true;
            entry.pushSorted(i);

            for (int j = 0; j < this.matrix[i].length; ++j) {
                if (this.matrix[i][j] == 1 && !this.visited[j]) {
                    this.comp(j, entry);
                }
            }
        }
    }

    public int[] ham() {
        this.hamiltonianPath = new int[this.size];
        this.hamiltonianPath[0] = 0;

        backtrackHamilton(1);

        return this.hamiltonianPath;
    }

    private boolean backtrackHamilton(int nextNode) {
        // Imamo že vse točke v poti?
        if (nextNode == this.size) {
            if (this.matrix[this.hamiltonianPath[nextNode - 1]][this.hamiltonianPath[0]] == 1) {
                return true;
            }
            return true;
        }

        for (int i = 1; i < this.size; ++i) {
            // Ali sta povezana && ni še v Hamiltonovem obhodu?
            if (this.matrix[this.hamiltonianPath[nextNode - 1]][i] != 0 && isInHamiltonPath(i, nextNode)) {
                this.hamiltonianPath[nextNode] = i;

                if (this.backtrackHamilton(nextNode + 1)) {
                    return true;
                }

                // Zmotili smo se, pojdimo nazaj; backtrack
                this.hamiltonianPath[nextNode] = 0;
            }
        }

        return false;
    }

    private boolean isInHamiltonPath(int node, int nextNode) {
        for (int i = 0; i < nextNode; ++i) {
            if (this.hamiltonianPath[i] == node) {
                return false;
            }
        }
        return true;
    }
}

public class Naloga3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] arguments = scanner.nextLine().split("\\s+");
        if (arguments.length < 2) {
            System.out.println("Not enough arguments! (Required 2, but got " + arguments.length + ")");
            System.out.println("Syntax: directed/undirected mode");
            return;
        }
        int nodes = scanner.nextInt();
        Graph graph = new Graph(nodes, arguments[0].equals("directed"));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("q")) break;
            String[] edge = line.split("\\s+");
            if (edge.length == 2) {
                graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
            }
        }

        switch (arguments[1]) {
            case "info":
                graph.info();
                break;
            case "walks":
                graph.walk(Integer.parseInt(arguments[2]));
                break;
            case "dfs":
                graph.dfs();
                break;
            case "bfs":
                graph.bfs();
                break;
            case "sp":
                graph.sp(Integer.parseInt(arguments[2]));
                break;
            case "comp":
                graph.comp();
                break;
            case "ham":
                if (!graph.isDirected()) {
                    int j = 0;
                    int first = -1;
                    int last = -1;
                    ArrayDeque<Integer> group = new ArrayDeque<Integer>(nodes);
                    for (int i = 0; i < nodes; ++i) {
                        graph.comp(i, group);
                        if (group.size() > 0) {
                            if (j++ == 0) {
                                first = group.back();
                            } else {
                                graph.addEdge(last, group.back());
                            }
                            last = group.front();
                            group.clear();
                        }
                    }
                    graph.addEdge(last, first);
                    int[] hamiltonPath = graph.ham();
                    for (int node : hamiltonPath) {
                        System.out.print(node + " ");
                    }
                }
                break;
            default:
                System.out.println("Mode not implemented");
                break;
        }

        scanner.close();
    }
}
