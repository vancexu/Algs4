public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rqueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rqueue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; ++i) {
            System.out.println(rqueue.dequeue());
        }
        
    }
}