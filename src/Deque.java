import java.util.*;

public class Deque<Item> implements Iterable<Item> {

	private Node head, tail;
	private int size;

	// construct an empty deque
	public Deque() {
		head = null;
		tail = null;
		size = 0;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return head == null && tail == null;
	}

	// return the number of items on the deque
	public int size() {
		return size;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item == null) {
			throw new java.lang.NullPointerException("Item may not be null.");
		}

		Node newNode = new Node();
		newNode.item = item;
		newNode.prev = null;
		newNode.next = head;

		if (head != null) {
			head.prev = newNode;
		}
		head = newNode;

		if (tail == null) {
			tail = head;
		}

		size++;
	}

	// add the item to the end
	public void addLast(Item item) {
		if (item == null) {
			throw new java.lang.NullPointerException("Item may not be null.");
		}

		Node newNode = new Node();
		newNode.item = item;
		newNode.prev = tail;
		newNode.next = null;

		if (tail != null) {
			tail.next = newNode;
		}
		tail = newNode;

		if (head == null) {
			head = tail;
		}

		size++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}

		Item item = head.item;

		head = head.next;

		if (head == null) {
			tail = null;
		} else {
			head.prev = null;
		}

		size--;

		return item;
	}

	// remove and return the item from the end
	public Item removeLast() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}

		Item item = tail.item;

		tail = tail.prev;

		if (tail == null) {
			head = null;
		} else {
			tail.next = null;
		}

		size--;

		return item;
	}

	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	// public void printMe() {
	// if (isEmpty()) {
	// System.out.println("Empty");
	// return;
	// }
	//
	// Node work = head;
	// while (work != null) {
	// System.out.print(work.item.toString()+ ", ");
	// work = work.next;
	// }
	// System.out.println();
	// }

	// unit testing
	public static void main(String[] args) {
		testEmpty();
		testAddRemoveFirst();
		testAddRemoveLast();
		testAddRemoveMixed();
		testFullEmptyFull();
		testIterator();
		testIteratorMulti();
	}

	private static void testEmpty() {
		Deque<String> d = new Deque<String>();
		assert d.isEmpty();
		assert d.size() == 0;
	}

	private static void testAddRemoveFirst() {
		Deque<String> d = new Deque<String>();
		assert d.size() == 0;
		d.addFirst("hello");
		assert d.size() == 1;
		d.addFirst("world");

		assert d.size() == 2;

		String s;

		s = d.removeFirst();
		assert s.equals("world");
		assert d.size() == 1;

		s = d.removeFirst();
		assert s.equals("hello");
		assert d.size() == 0;
	}

	private static void testAddRemoveLast() {
		Deque<String> d = new Deque<String>();
		assert d.size() == 0;
		d.addLast("hello");
		assert d.size() == 1;
		d.addLast("world");

		assert d.size() == 2;

		String s;

		s = d.removeLast();
		assert s.equals("world");
		assert d.size() == 1;

		s = d.removeLast();
		assert s.equals("hello");
		assert d.size() == 0;
	}

	private static void testAddRemoveMixed() {
		Deque<String> d = new Deque<String>();
		assert d.size() == 0;

		d.addFirst("hello");
		// d.printMe();
		assert d.size() == 1;

		d.addLast("world");
		// d.printMe();
		assert d.size() == 2;

		d.addLast("from");
		// d.printMe();
		assert d.size() == 3;

		d.addFirst("mars");
		// d.printMe();
		assert d.size() == 4;

		String s;

		s = d.removeLast();
		// d.printMe();
		assert s.equals("from");
		assert d.size() == 3;

		s = d.removeFirst();
		// d.printMe();
		assert s.equals("mars");
		assert d.size() == 2;

		s = d.removeFirst();
		// d.printMe();
		assert s.equals("hello");
		assert d.size() == 1;

		s = d.removeFirst();
		// d.printMe();
		assert s.equals("world");
		assert d.size() == 0;
	}

	private static void testFullEmptyFull() {
		Deque<String> d = new Deque<String>();

		d.addFirst("aaa");
		d.addLast("bbb");
		d.addFirst("ccc");
		d.addLast("ddd");
		d.addFirst("eee");
		d.addLast("fff");

		String s;
		s = d.removeFirst();
		assert s.equals("eee");
		s = d.removeLast();
		assert s.equals("fff");
		s = d.removeFirst();
		assert s.equals("ccc");
		s = d.removeLast();
		assert s.equals("ddd");
		s = d.removeFirst();
		assert s.equals("aaa");
		s = d.removeLast();
		assert s.equals("ddd");

		d.addFirst("aaa");
		d.addLast("bbb");
		d.addFirst("ccc");
		d.addLast("ddd");

		s = d.removeFirst();
		assert s.equals("ccc");
		s = d.removeLast();
		assert s.equals("ddd");
		s = d.removeFirst();
		assert s.equals("aaa");
		s = d.removeLast();
		assert s.equals("bbb");

	}

	private static void testIterator() {
		Deque<String> d = new Deque<String>();

		String[] items = new String[4];
		items[0] = "mars";
		items[1] = "hello";
		items[2] = "world";
		items[3] = "from";

		d.addFirst(items[1]);
		d.addLast(items[2]);
		d.addLast(items[3]);
		d.addFirst(items[0]);

		Iterator<String> i = d.iterator();

		int index = 0;
		while (i.hasNext()) {
			String s = i.next();
			// System.out.println(s + " " + items[index]);
			assert s.equals(items[index]);
			index++;
		}
	}

	private static void testIteratorMulti() {
		Deque<String> d = new Deque<String>();

		String[] items = new String[4];
		items[0] = "mars";
		items[1] = "hello";
		items[2] = "world";
		items[3] = "from";

		d.addFirst(items[1]);
		d.addLast(items[2]);
		d.addLast(items[3]);
		d.addFirst(items[0]);

		int o = 0;
		for (String outer : d) {
			int i = 0;
			for (String inner : d) {
				assert inner.equals(items[i]);
				assert outer.equals(items[o]);
				// System.out.println(outer+" "+inner);
				i++;
			}
			assert outer.equals(items[o]);
			o++;
		}
	}

	private class Node {
		private Item item;
		private Node next, prev;

		@Override
		public String toString() {
			return item == null ? "null item" : item.toString();
		}
	}

	private class DequeIterator implements Iterator<Item> {

		private Node current = head;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if (current == null) {
				throw new java.util.NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;

			return item;
		}
	}
}
