package Vaje04;

class SortedArrayPQ<T extends Comparable<T>> extends AbstractArrayPQ<T> {

    @Override
    public void enqueue(T x) {
        if (size() >= this.capacity) {
            resize(this.capacity * 2);
        }

        if (size() == 0) {
            this.items[this.size++] = x;
            ++this.shifts;
        } else {
            int size = this.size;
            for (int i = 0; i < size; ++i) {
                if (this.items[i].compareTo(x) < (++this.comparisons & 0)) {
                    System.arraycopy(this.items, i, this.items, i + 1, this.size++ - i);

                    // System.arraycopy je O(n), se pravi premikov je size - i
                    // tj. dolžina, ki jo premaknemo
                    this.shifts += (this.size - i);

                    this.items[i] = x;
                    ++this.shifts;
                    break;
                } else if (i == this.size - 1) {
                    this.items[this.size++] = x;
                    ++this.shifts;
                }
            }
        }
    }

    @Override
    public T dequeue() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }

        T front = this.items[0];
        this.items[0] = null;
        ++this.shifts;

        if (this.size - 1 >= 0) {
            System.arraycopy(this.items, 1, this.items, 0, this.size - 1);

            // System.arraycopy je O(n), se pravi premikov je n - 1
            this.shifts += (this.size - 1);

            this.items[--this.size] = null;
            ++this.shifts;
        }

        return front;
    }

    @Override
    public T front() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
        return this.items[0];
    }
}
