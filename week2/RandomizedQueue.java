import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] rqueue;
	private int size;

	public RandomizedQueue() {
		rqueue = (Item[]) new Object[2];
		size = 0;
	}

	public boolean isEmpty() { return size==0; }
	public int size() { return size; }

	private void expand(int size) {
		Item[] temp = (Item[]) new Object[size*2];
		for (int i=0; i<size; i++){
			temp[i] = rqueue[i];
		}
		rqueue = temp;
	}

	private void contract(int size) {
		if (size==1) { return;}
		Item[] temp = (Item[]) 	new Object[size/2];
		for (int i=0; i<size/2; i++){
			temp[i] = rqueue[i];
		}
		rqueue = temp;
	}

	public void enqueue(Item item) {
		if (item==null){ throw new IllegalArgumentException("Null items are not allowed.");}
		if (size==rqueue.length){ expand(size);}
		rqueue[size++] = item;
	}

	public Item sample() {
		if (size==0){ throw new NoSuchElementException("Cannot sample from empty queue.");}
		return rqueue[StdRandom.uniform(size)];
	}

	public Item dequeue() {
		if (size==0){ throw new NoSuchElementException("Cannot dequeue from empty queue.");}
		int index = StdRandom.uniform(size);
		Item value = rqueue[index];
		rqueue[index] = rqueue[size--];
		rqueue[size+1] = null;
		if (size==rqueue.length/2){ contract(size); }
		return value;
	}

	public Iterator<Item> iterator() {
		StdRandom.shuffle(rqueue, 0, size);
		return new Iterator<Item>(){
			private int count = 0;
			private int length = size;

			public boolean hasNext() { return count!=length;}
			public Item next() {
				if (!hasNext()) {throw new NoSuchElementException("The queue is empty.");}
				return rqueue[count++];
			}
			public void remove() { throw new UnsupportedOperationException("this operation is not supported."); }
		};	
	}
}