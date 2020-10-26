package Vaje04;

class Node<T> {
    T item;
    Node<T> left, right, parent;

    public Node(T item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Node " + this.item.toString() + ", left: " + this.left.item + ", right: " + this.right.item;
    }
}
