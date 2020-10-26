package Vaje04;

class ArrayHeapPQ<T extends Comparable<T>> extends AbstractArrayPQ<T> {

    private int getParent(int i) {
        if (i == 0) {
            return 0;
        }

        return (i - 1) / 2;
    }

    private int getLeftChild(int i) {
        return (2 * i + 1);
    }

    private int getRightChild(int i) {
        return (2 * i + 2);
    }

    private void swap(int a, int b) {
        T temp = this.items[a];
        this.items[a] = this.items[b];
        this.items[b] = temp;
        this.shifts += 3;
    }

    @Override
    public void enqueue(T x) {
        if (size() >= this.capacity) {
            resize(this.capacity * 2);
        }

        // Dodaj element na zadnje mesto
        this.items[this.size++] = x;
        ++this.shifts;

        // Ponovno zgradi drevo
        int i = this.size - 1;
        while (i > 0 && this.items[getParent(i)].compareTo(this.items[i]) < (++this.comparisons & 0)) {
            swap(i, getParent(i));
            i = getParent(i);
        }
    }

    @Override
    public T dequeue() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }

        T root = this.items[0];

        // Prestavi zadnji element na prvo mesto
        this.items[0] = this.items[this.size - 1];
        this.items[--this.size] = null;
        this.shifts += 2;

        // Ponovno zgradi drevo
        int i = 0;
        while (i < this.size) {
            int largest = i;
            int left = getLeftChild(i);
            int right = getRightChild(i);

            if (left < size() && this.items[i].compareTo(this.items[left]) < (++this.comparisons & 0)) {
                largest = left;
            }

            if (right < size() && this.items[largest].compareTo(this.items[right]) < (++this.comparisons & 0)) {
                largest = right;
            }

            if (largest != i) {
                swap(i, largest);
                i = largest;
            } else {
                break;
            }
        }

        return root;
    }

    @Override
    public T front() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
        return this.items[0];
    }
}
