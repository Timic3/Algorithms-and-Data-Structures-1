package Vaje01;

interface Sequence<T> extends Collection {
    static final String ERR_MSG_INDEX = "Wrong index in sequence.";

    T get(int i) throws CollectionException;
    void add(T x) throws CollectionException;

    T set(int i, T x) throws CollectionException;
    int indexOf(T x) throws CollectionException;
    //T remove(int i) throws CollectionException;
    //T remove(T x) throws CollectionException;
}
