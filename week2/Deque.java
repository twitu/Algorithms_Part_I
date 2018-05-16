import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private Node start, end;
	private int size;

	private class Node {
		private Item item;
		private Node next, prev;

		public Node(Item item) {
			this.item = item;
		}
	}

	public Deque() {
		size = 0;
	}

	public boolean isEmpty() { return size==0; }

	public int size() { return size; }

	public void addFirst(Item item) {
		if (item==null){ throw new IllegalArgumentException("null items are not supported."); }
		if (this.isEmpty()) {
			start = new Node(item);
			end = start;
			size++;
		} else {
			Node temp = start;
			start = new Node(item);
			start.next = temp;
			temp.prev = start;
			size++;
		}
	}

	public void addLast(Item item){
		if (item==null){ throw new IllegalArgumentException("null items are not supported."); }
		if (this.isEmpty()){
			end = new Node(item);
			start = end;
			size++;
		} else {
			Node temp = end;
			end = new Node(item);
			temp.next = end;
			end.prev = temp;
			size++;
		}
	}

	public Item removeFirst(){
		if (this.isEmpty()){
			throw new NoSuchElementException("There is no element to remove.");
		} else {
			Item temp = start.item;
			if (size==1){
				start = null;
				end = null;
				size--;
			} else {
				start.next.prev = null;
				start = start.next;
				size--;
			}
			return temp;
		}
	}

	public Item removeLast(){
		if (this.isEmpty()){ 
			throw new NoSuchElementException("There is no element to remove.");
		} else {
			Item temp = end.item;
			if (size==1){
				start = null;
				end = null;
				size--;
			} else {
				end = end.prev;
				end.next = null;
				size--;
			}
			return temp;
		}
	}

	public Iterator<Item> iterator(){
		return new Iterator<Item>(){
			private Node current = start;
			private Item value;
			
			public boolean hasNext() { return current != null; }
			public void remove() { throw new UnsupportedOperationException("this operation is not supported."); }
			public Item next() {
				if (!hasNext()){throw new NoSuchElementException("There are no more items left.");}
					value = current.item;
					current = current.next;
					return value;
			}
		};
	}

	public static void main(String[] args){
		Deque<Integer> deque = new Deque<Integer>();
		deque.addFirst(1);
		deque.removeFirst();
		deque.addFirst(2);
		deque.addLast(3);
		deque.addLast(4);
	}
}
