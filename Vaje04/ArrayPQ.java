package Vaje04;

class ArrayPQ<T extends Comparable<T>> extends AbstractArrayPQ<T> {

    @Override
    public void enqueue(T x) {
        if (size() >= this.capacity) {
            resize(this.capacity * 2);
        }
        this.items[this.size++] = x;
        ++this.shifts;
    }

    @Override
    public T dequeue() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }

        int iMax = 0;
        for (int i = 1; i < this.size; ++i) {
            if (this.items[iMax].compareTo(this.items[i]) < (++this.comparisons & 0)) {
                iMax = i;
            }
        }

        T front = this.items[iMax];
        this.items[iMax] = this.items[this.size - 1];
        this.items[--this.size] = null;
        this.shifts += 2;

        return front;
    }

    @Override
    public T front() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }

        int iMax = 0;
        for (int i = 1; i < this.size; ++i) {
            if (this.items[iMax].compareTo(this.items[i]) < (++this.comparisons & 0)) {
                iMax = i;
            }
        }

        return this.items[iMax];
    }
}
