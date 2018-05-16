import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;

public class Permutation{
	public static void main(String[] args) {
		RandomizedQueue<String> rqueue = new RandomizedQueue<String>();

		int items = Integer.parseInt(args[0]);

		while (!StdIn.isEmpty()){
			rqueue.enqueue(StdIn.readString());
		}

		Iterator<String> itr = rqueue.iterator();
		while (itr.hasNext() && items != 0){
			StdOut.println(itr.next());
			items--;
		}
	}
}