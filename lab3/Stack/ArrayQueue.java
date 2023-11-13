package Stack;

public class ArrayQueue<T> implements Queue<T> {
    private T[] elements;
    private int front, rear, size, capacity;

    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        elements = (T[]) new Object[capacity];
        front = size = 0;
        rear = capacity - 1;
    }

    public void enqueue(T element) {
        if (isFull()) {
            // Handle queue full scenario, resize array or throw an exception
        } else {
            rear = (rear + 1) % capacity;
            elements[rear] = element;
            size++;
        }
    }

    public T dequeue() {
        if (isEmpty()) {
            // Handle empty queue scenario, throw an exception or return null
            return null;
        } else {
            T item = elements[front];
            front = (front + 1) % capacity;
            size--;
            return item;
        }
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return elements[front];
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private boolean isFull() {
        return size == capacity;
    }
}
