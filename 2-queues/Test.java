import java.util.NoSuchElementException;

public class Test {
    
    public static void main(String[] args) {
        /*
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(3);
        deque.removeFirst();
        deque.removeLast();
        for (Integer i : deque) 
            System.out.println(i);
            */
        /*
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; ++i)
            rq.enqueue(i);
        rq.dequeue();
        for (Integer i : rq)
            System.out.print(i);
        System.out.println("");
        for (Integer i : rq)
            System.out.print(i);
            */
        int k = Integer.parseInt(args[0]);
        System.out.println(k);
        String s;
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            System.out.println(s);          
        }
    }

}