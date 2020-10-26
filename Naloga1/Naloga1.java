package Naloga1;

// a1047eab1035d58682a53557e0b2a75edbfd15fd

import java.util.Scanner;

interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";
    static final String ERR_MSG_FULL = "Collection is full.";

    boolean isEmpty();
    boolean isFull();
    int size();
    String toString();
}

class CollectionException extends Exception {
    CollectionException(String msg) {
        super(msg);
    }
}

interface Stack<T> extends Collection {
    T top() throws CollectionException;
    void push(T x) throws CollectionException;
    T pop() throws CollectionException;
}

interface Sequence<T> extends Collection {
    static final String ERR_MSG_INDEX = "Wrong index in sequence.";

    T get(int i) throws CollectionException;
    void add(T x) throws CollectionException;

    T set(int i, T x) throws CollectionException;
    int indexOf(T x) throws CollectionException;
    //T remove(int i) throws CollectionException;
    //T remove(T x) throws CollectionException;
}

interface Deque<T> extends Collection {
    T front() throws CollectionException;
    T back() throws CollectionException;
    void enqueue(T x) throws CollectionException;
    void enqueueFront(T x) throws CollectionException;
    T dequeue() throws CollectionException;
    T dequeueBack() throws CollectionException;
}

class ArrayDeque<T> implements Deque<T>, Stack<T>, Sequence<T> {
    private static final int DEFAULT_CAPACITY = 64;
    private T[] array;
    private int size, front, back;

    // ArrayDeque

    @SuppressWarnings("unchecked")
    ArrayDeque() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    ArrayDeque(int capacity) {
        array = (T[]) new Object[capacity];
    }

    // Deque

    @Override
    public T front() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
        return array[front];
    }

    @Override
    public T back() throws CollectionException {
        return top();
    }

    @Override
    public void enqueue(T x) throws CollectionException {
        push(x);
    }

    @Override
    public void enqueueFront(T x) throws CollectionException {
        if (isFull()) {
            throw new CollectionException(Collection.ERR_MSG_FULL);
        }
        front = prev(front);
        array[front] = x;
        size++;
    }

    @Override
    public T dequeue() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
        T object = array[front];
        array[front] = null;
        front = next(front);
        size--;
        return object;
    }

    @Override
    public T dequeueBack() throws CollectionException {
        return pop();
    }

    // Sequence

    @Override
    public T get(int i) throws CollectionException {
        if (i < 0 || i >= size) {
            throw new CollectionException(Sequence.ERR_MSG_INDEX);
        }
        return array[index(i)];
    }

    @Override
    public void add(T x) throws CollectionException {
        push(x);
    }

    // Implementacija dodatnih metod
    @Override
    public T set(int i, T x) throws CollectionException {
        if (i < 0 || i >= size) {
            throw new CollectionException(Sequence.ERR_MSG_INDEX);
        }
        T object = array[index(i)];
        array[index(i)] = x;
        return object;
    }

    @Override
    public int indexOf(T x) throws CollectionException {
        int i = 0;
        while (index(i) < size) {
            if (x == array[index(i)]) {
                return index(i);
            }
            i++;
        }
        return -1;
    }
    // Konec implementacije dodatnih metod

    private int index(int i) {
        return (front + i) % DEFAULT_CAPACITY;
    }

    // Stack

    @Override
    public T top() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
        return array[prev(back)];
    }

    @Override
    public void push(T x) throws CollectionException {
        if (isFull()) {
            throw new CollectionException(Collection.ERR_MSG_FULL);
        }
        array[back] = x;
        back = next(back);
        size++;
    }

    @Override
    public T pop() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
        back = prev(back);
        T object = array[back];
        array[back] = null;
        size--;
        return object;
    }

    // Collection

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == DEFAULT_CAPACITY;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder objects = new StringBuilder();
        if (size > 0) {
            objects.append(array[front].toString());
        }
        for (int i = 0; i < size - 1; i++) {
            objects.append(" ").append(array[next(front + i)]);
        }
        return objects.toString();
    }

    private int next(int i) {
        return (i + 1) % DEFAULT_CAPACITY;
    }

    private int prev(int i) {
        return (DEFAULT_CAPACITY + i - 1) % DEFAULT_CAPACITY;
    }
}

class Utils {
    static int factorial(int x) {
        int r = 1;

        for (int i = 2; i <= x; i++) {
            r *= i;
        }

        return r;
    }
}

@SuppressWarnings("unchecked")
class Interpreter {
    private Sequence<Stack<String>> stacks;
    private Scanner input;
    private boolean condition;

    Interpreter(int capacity) throws CollectionException {
        stacks = new ArrayDeque<Stack<String>>(capacity);
        for (int i = 0; i < capacity; i++) {
            stacks.add(new ArrayDeque<String>());
        }
    }

    void parse(String line) throws CollectionException {
        input = new Scanner(line);
        while (input.hasNext()) {
            token(input.next());
        }
        input.close();
    }

    private void token(String token) throws CollectionException {
        String top, bottom;
        int x, y;

        if (token.charAt(0) == '?') {
            if (!condition) {
                return;
            }
            token = token.substring(1);
        }

        switch (token) {
            // Glavne metode sklada
            case "echo":
                if (!stacks.get(0).isEmpty()) {
                    System.out.println(stacks.get(0).top());
                } else {
                    System.out.println();
                }
                break;
            case "pop":
                stacks.get(0).pop();
                break;
            case "dup":
                stacks.get(0).push(stacks.get(0).top());
                break;
            case "dup2":
                top = stacks.get(0).pop();
                bottom = stacks.get(0).top();
                stacks.get(0).push(top);
                stacks.get(0).push(bottom);
                stacks.get(0).push(top);
                break;
            case "swap":
                top = stacks.get(0).pop();
                bottom = stacks.get(0).pop();
                stacks.get(0).push(top);
                stacks.get(0).push(bottom);
                break;
            // Metode za pomožne sklade
            case "print":
                if (!stacks.get(0).isEmpty()) {
                    x = Integer.parseInt(stacks.get(0).pop());
                    System.out.println(stacks.get(x));
                } else {
                    System.out.println();
                }
                break;
            case "clear":
                x = Integer.parseInt(stacks.get(0).pop());
                stacks.set(x, new ArrayDeque<String>());
                break;
            case "reverse":
                x = Integer.parseInt(stacks.get(0).pop());
                Stack<String> newStack = new ArrayDeque<String>();
                while (!stacks.get(x).isEmpty()) {
                    newStack.push(stacks.get(x).pop());
                }
                stacks.set(x, newStack);
                break;
            case "len":
                if (!stacks.get(0).isEmpty()) {
                    top = stacks.get(0).pop();
                    stacks.get(0).push(String.valueOf(top.length()));
                }
                break;
            // Operacije
            case "+":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(y + x));
                break;
            case "-":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(y - x));
                break;
            case "*":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(y * x));
                break;
            case "/":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                try {
                    stacks.get(0).push(String.valueOf(y / x));
                } catch (ArithmeticException ignored) {

                }
                break;
            case "%":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                try {
                    stacks.get(0).push(String.valueOf(y % x));
                } catch (ArithmeticException ignored) {

                }
                break;
            case ".":
                top = stacks.get(0).pop();
                bottom = stacks.get(0).pop();
                stacks.get(0).push(bottom + top);
                break;
            case "!":
                x = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(Utils.factorial(x)));
                break;
            case "==":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(y == x ? 1 : 0));
                break;
            case "<=":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(y <= x ? 1 : 0));
                break;
            case "<":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(y < x ? 1 : 0));
                break;
            case ">=":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(y >= x ? 1 : 0));
                break;
            case ">":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(y > x ? 1 : 0));
                break;
            case "<>":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(y != x ? 1 : 0));
                break;
            case "even":
                x = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(Math.abs(x) % 2 == 0 ? 1 : 0));
                break;
            case "odd":
                x = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(Math.abs(x) % 2 == 1 ? 1 : 0));
                break;
            case "rnd":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf(y + (int) (Math.random() * ((x - y) + 1))));
                break;
            case "char":
                x = Integer.parseInt(stacks.get(0).pop());
                stacks.get(0).push(String.valueOf((char) x));
                break;
            case "fun":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                while (y-- > 0) {
                    stacks.get(x).push(input.next());
                }
                break;
            case "move":
                x = Integer.parseInt(stacks.get(0).pop());
                y = Integer.parseInt(stacks.get(0).pop());
                while (y-- > 0) {
                    stacks.get(x).push(stacks.get(0).pop());
                }
                break;
            case "run":
            case "loop":
                x = Integer.parseInt(stacks.get(0).pop());
                if (token.equals("loop")) {
                    y = Integer.parseInt(stacks.get(0).pop());
                } else {
                    y = 1;
                }
                Sequence<String> d = (Sequence<String>) stacks.get(x);
                while (y-- > 0) {
                    for (int i = 0; i < d.size(); i++) {
                        String t = d.get(i);
                        this.token(t);
                    }
                }
                break;
            // Pogojni stavki
            case "then":
                top = stacks.get(0).pop();
                condition = !top.equals("0");
                break;
            case "else":
                condition = !condition;
                break;
            default:
                stacks.get(0).push(token);
                break;
        }
    }

    void clear() throws CollectionException {
        int capacity = stacks.size();
        stacks = new ArrayDeque<Stack<String>>(capacity);
        for (int i = 0; i < capacity; i++) {
            stacks.add(new ArrayDeque<String>());
        }
        condition = false;
    }
}

public class Naloga1 {

    public static void main(String[] args) throws Exception {
        Interpreter ip = new Interpreter(42);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // Menim, da je tu najbolje uporabiti dva Scanner-ja (enega
            // tukaj in enega v parser), zaradi razširljivosti.
            // Interpreter razred lahko nato kopiramo kamor hočemo.
            ip.parse(line);
            // System.out.println("Line parsed!");

            // Vsakič moramo izprazniti!
            ip.clear();
        }
        scanner.close();
    }
}
