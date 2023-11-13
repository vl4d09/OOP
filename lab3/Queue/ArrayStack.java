package Queue;
public class ArrayStack<T> implements Stack<T> {
    private T[] elements;
    private int top;

    public ArrayStack(int capacity) {
        elements = (T[]) new Object[capacity];
        top = -1;
    }

    public void push(T element) {
        if (top == elements.length - 1) {
        } else {
            elements[++top] = element;
        }
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        } else {
            return elements[top--];
        }
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return elements[top];
        }
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }
}
