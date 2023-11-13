package Stack;
import java.util.Stack;

public class QueueUsingStacks<T> implements Queue<T> {
    private Stack<T> stack1 = new Stack<>();
    private Stack<T> stack2 = new Stack<>();

    public void enqueue(T element) {
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        stack1.push(element);
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        return stack1.pop();
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return stack1.peek();
    }

    public boolean isEmpty() {
        return stack1.isEmpty();
    }

    public int size() {
        return stack1.size();
    }
}
