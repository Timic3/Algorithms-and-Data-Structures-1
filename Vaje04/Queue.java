package Vaje04;

interface Queue<T> extends Collection {
    T front() throws CollectionException;
    void enqueue(T x);
    T dequeue() throws CollectionException;
}
