package Queue;
public class LinkedStack<T> implements Stack<T> {
    private Node<T> top;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public void push(T element) {
        Node<T> newNode = new Node<>(element);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        } else {
            T data = top.data;
            top = top.next;
            size--;
            return data;
        }
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return top.data;
        }
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
