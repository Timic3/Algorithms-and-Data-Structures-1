package Vaje01;

public class Izziv1 {
    public static void main(String[] args) throws CollectionException {
        // Stack tests
        System.out.println("-- Stack tests --");
        Stack<String> s = new ArrayDeque<>();
        s.push("A");
        s.push("B");
        s.push("C");
        System.out.println(s + " = " + s.size());
        System.out.println("Pop: " + s.pop());
        System.out.println(s + " = " + s.size());
        System.out.println("Pop: " + s.pop());
        System.out.println("Pop: " + s.pop());
        System.out.println(s + " = " + s.size());
        s.push("D");
        System.out.println(s + " = " + s.size());
        s.push("E");
        System.out.println(s + " = " + s.size());
        System.out.println("Top: " + s.top());
        System.out.println("Pop: " + s.pop());
        System.out.println(s + " = " + s.size());
        System.out.println("Pop: " + s.pop());

        try {
            System.out.println("Pop: " + s.pop());
        } catch (CollectionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Filling stack (overflow)");
        try {
            for (int i = 0; i <= 64; i++) {
                s.push(String.valueOf(i));
            }
        } catch (CollectionException e) {
            System.out.println(e.getMessage());
        }

        // Sequence tests
        System.out.println();
        System.out.println("-- Sequence tests --");
        Sequence<String> sq = new ArrayDeque<>();
        sq.add("A");
        sq.add("B");
        sq.add("C");
        System.out.println(sq + " = " + sq.size());
        System.out.println(sq.get(2));
        System.out.println(sq.get(1));
        System.out.println(sq.get(0));

        try {
            System.out.println(sq.get(-1));
        } catch (CollectionException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(sq.get(4));
        } catch (CollectionException e) {
            System.out.println(e.getMessage());
        }

        String a = "D";
        sq.add(a);
        System.out.println(sq + " = " + sq.size());
        System.out.println("Index of D" + sq.indexOf(a));
        System.out.println("Set (prejsni): " + sq.set(2, "E"));
        System.out.println(sq + " = " + sq.size());

        // Deque
        System.out.println();
        System.out.println("-- Deque tests --");
        Deque<String> d = new ArrayDeque<>();
        d.enqueue("B");
        d.enqueue("C");
        d.enqueueFront("A");
        d.enqueue("D");
        System.out.println("Front: " + d.front());
        System.out.println("Back: " + d.back());
        System.out.println(d + " = " + d.size());
        System.out.println("Deque: " + d.dequeue());
        System.out.println("Deque: " + d.dequeueBack());
        System.out.println(d + " = " + d.size());
        System.out.println("Front: " + d.front());
        System.out.println("Back: " + d.back());
        System.out.println("Deque: " + d.dequeue());
        System.out.println("Front: " + d.front());
        System.out.println("Back: " + d.back());
        System.out.println("Deque: " + d.dequeue());
        try {
            System.out.println("Deque: " + d.dequeue());
        } catch (CollectionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Filling deque (overflow)");
        try {
            for (int i = 0; i <= 64; i++) {
                d.enqueueFront(String.valueOf(i));
            }
        } catch (CollectionException e) {
            System.out.println(e.getMessage());
        }
        /*
        Stack<String> s = new ArrayDeque<>();
        Deque<String> d = new ArrayDeque<>();
        Sequence<String> z = new ArrayDeque<>();
        s.push("ABC"); s.push("DEF"); s.push("GHI");
        System.out.println("Stack: ");
        while (!s.isEmpty()) {
            System.out.println(s.top() + " ");
            d.enqueueFront(s.pop());
        }
        System.out.println("\n Deque: ");
        while (!d.isEmpty()) {
            System.out.println(d.back() + " ");
            z.add(d.dequeueBack());
        }
        System.out.println("\n Sequence: ");
        for (int i = 0; i < z.size(); i++) {
            System.out.println((i + 1) + ". " + z.get(i) + " ");
        }
        */
    }
}