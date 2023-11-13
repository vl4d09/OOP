package Queue;

import java.util.LinkedList;
import java.util.Queue;

public class StackUsingQueues<T> implements Stack<T> {
    private Queue<T> queue1 = new LinkedList<>();
    private Queue<T> queue2 = new LinkedList<>();
    private int size;

    public void push(T element) {
        queue2.add(element);
        while (!queue1.isEmpty()) {
            queue2.add(queue1.remove());
        }
        Queue<T> temp = queue1;
        queue1 = queue2;
        queue2 = temp;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }
        size--;
        return queue1.remove();
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return queue1.peek();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
