package Vaje04;

abstract class AbstractArrayPQ<T extends Comparable<T>> implements PriorityQueue<T> {
    protected int capacity;
    protected T[] items;
    protected int size;
    protected int comparisons;
    protected int shifts;

    public AbstractArrayPQ() {
        this.capacity = 64;
        this.items = (T[]) new Comparable[this.capacity];
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder objects = new StringBuilder("[");
        if (this.size > 0) {
            objects.append(this.items[0].toString());
        }
        for (int i = 1; i < this.size; ++i) {
            objects.append(", ").append(this.items[i]);
        }
        return objects.append("]").toString();
    }

    protected void resize(int newCapacity) {
        this.capacity = newCapacity;
        T[] newArray = (T[]) new Comparable[this.capacity];
        if (size() >= 0) {
            System.arraycopy(this.items, 0, newArray, 0, size());
        }
        this.items = newArray;
    }

    public int getShifts() {
        return shifts;
    }

    public int getComparisons() {
        return comparisons;
    }
}
