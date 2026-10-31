import java.util.AbstractList;

public class MyArrayList<E> extends AbstractList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public boolean add(E element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
        modCount++;
        return true;
    }


    @Override
    public E get(int index) {
        checkIndex(index);
        return elementAt(index);
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E removed = elementAt(index);
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
        elements[size] = null;
        modCount++;
        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    private void ensureCapacity(int required) {
        if (required > elements.length) {
            int newCapacity = elements.length * 2;
            Object[] newElements = new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс " + index + ", размер " + size);
        }
    }

    @SuppressWarnings("unchecked")
    private E elementAt(int index) {
        return (E) elements[index];
    }
}
