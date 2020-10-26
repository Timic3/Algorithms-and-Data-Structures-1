package Vaje04;

class LinkedHeapPQ<T extends Comparable<T>> implements PriorityQueue<T> {
    private Node<T> root;
    private Node<T> lastNode;
    private int size;
    private int comparisons;
    private int shifts;

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T front() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }

        return root.item;
    }

    @Override
    public void enqueue(T x) {
        Node<T> newNode = new Node<>(x);
        this.size++;
        if (root == null) {
            root = newNode;
        } else {
            // Pridobi starša zadnjega elementa
            Node<T> lastParent = getLastNodeParent(lastNode);

            // Dodaj v drevo
            if (lastParent.left == null) {
                lastParent.left = newNode;
            } else {
                lastParent.right = newNode;
            }
            newNode.parent = lastParent;
            this.shifts += 2;

            // Ponovno zgradi drevo
            Node<T> current = newNode;
            T next = current.item;
            while (current != root && current.parent.item.compareTo(next) < (++this.comparisons & 0)) {
                current.item = current.parent.item;
                current = current.parent;
                this.shifts += 2;
            }
            current.item = next;
        }
        ++this.shifts;
        lastNode = newNode;
    }

    @Override
    public T dequeue() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }

        T front = root.item;

        if (this.size-- == 1) {
            root = null;
            lastNode = null;
        } else {
            // Pridobi naslednji zadnji element
            Node<T> nextLast = getNextLastNode(lastNode);

            // Izbriši iz drevesa
            if (lastNode.parent.left == lastNode) {
                lastNode.parent.left = null;
            } else {
                lastNode.parent.right = null;
            }

            root.item = lastNode.item;
            lastNode = nextLast;

            this.shifts += 2;

            // Ponovno sestavi kopico
            Node<T> node = root;
            Node<T> next = getNextAvailableNode(node.left, node.right);

            T current = node.item;
            while (next != null && current.compareTo(next.item) < (++this.comparisons & 0)) {
                node.item = next.item;
                node = next;
                next = getNextAvailableNode(node.left, node.right);
                this.shifts += 2;
            }
            node.item = current;
        }
        ++this.shifts;

        return front;
    }

    private Node<T> getLastNodeParent(Node<T> source) {
        Node<T> lastParent = source;

        while (lastParent != root && lastParent.parent.left != lastParent) {
            lastParent = lastParent.parent;
        }

        if (lastParent != root) {
            if (lastParent.parent.right == null) {
                lastParent = lastParent.parent;
            } else {
                lastParent = lastParent.parent.right;
                while (lastParent.left != null) {
                    lastParent = lastParent.left;
                }
            }
        } else {
            while (lastParent.left != null) {
                lastParent = lastParent.left;
            }
        }

        return lastParent;
    }

    private Node<T> getNextLastNode(Node<T> source) {
        Node<T> nextLast = source;

        while (nextLast != root && nextLast.parent.left == nextLast) {
            nextLast = nextLast.parent;
        }

        if (nextLast != root) {
            nextLast = nextLast.parent.left;
        }

        while (nextLast.right != null) {
            nextLast = nextLast.right;
        }

        return nextLast;
    }

    private Node<T> getNextAvailableNode(Node<T> left, Node<T> right) {
        if (left == null && right == null) {
            return null;
        } else if (right == null) {
            return left;
        } else if (right.item.compareTo(left.item) < (++this.comparisons & 0)) {
            return left;
        } else {
            return right;
        }
    }

    public int getComparisons() {
        return comparisons;
    }

    public int getShifts() {
        return shifts;
    }
}
