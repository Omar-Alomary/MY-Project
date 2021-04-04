public class ArrayStack<E> implements Stack<E> {

    E data[];
    static int capacity = 100;
    int t = -1;

    public ArrayStack(int cop){
        data = (E[]) new Object[cop];
    }

    public ArrayStack(){
        this(capacity);
    }
    @Override
    public boolean isEmpty() {
        return t == -1;
    }

    @Override
    public int size() {
        return  t + 1;
    }

    @Override
    public E top() {
        if (isEmpty())return  null;
        return data[t];
    }

    @Override
    public void push(E elem) throws IllegalStateException{
        if (size() == data.length) throw new IllegalStateException("Stack is full>");
        data[++t] = elem;
    }

    @Override
    public E pop() {
        if (isEmpty()) return null;
        E d = data[t];
        data[t] = null;
        t--;
        return d;
    }
}
