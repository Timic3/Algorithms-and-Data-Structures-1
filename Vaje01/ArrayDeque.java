package Vaje01;

class ArrayDeque<T> implements Deque<T>, Stack<T>, Sequence<T> {
    private static final int DEFAULT_CAPACITY = 64;
    private T[] array;
    private int size, front, back;

    // ArrayDeque

    public ArrayDeque() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
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
        StringBuilder objects = new StringBuilder("[");
        if (size > 0) {
            objects.append(array[front].toString());
        }
        for (int i = 0; i < size - 1; i++) {
            objects.append(", ").append(array[next(front + i)]);
        }
        return objects.append("]").toString();
    }

    private int next(int i) {
        return (i + 1) % DEFAULT_CAPACITY;
    }

    private int prev(int i) {
        return (DEFAULT_CAPACITY + i - 1) % DEFAULT_CAPACITY;
    }
}
