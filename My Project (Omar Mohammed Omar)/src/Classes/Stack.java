public interface Stack<E> {
    boolean isEmpty();
    int size();
    E top();
    void push(E elem);
    E pop();
}
