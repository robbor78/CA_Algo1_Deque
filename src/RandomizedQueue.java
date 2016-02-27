import java.util.*;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] s;
	private int n = 0;
	private boolean isNeedToShuffle = true;

	// construct an empty randomized queue
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		s = (Item[]) new Object[1];
	}

	// is the queue empty?
	public boolean isEmpty() {
		return n == 0;
	}

	// return the number of items on the queue
	public int size() {
		return n;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new java.lang.NullPointerException("Item may not be null.");
		}

		if (n == s.length) {
			resize(2 * s.length);
		}

		s[n++] = item;

		isNeedToShuffle = true;
	}

	// remove and return a random item
	public Item dequeue() {
		Item item = get(n - 1);
		s[n - 1] = null;
		if (n > 0 && n == s.length / 4) {
			resize(s.length / 2);
		}
		n--;
		return item;
	}

	// return (but do not remove) a random item
	public Item sample() {
		return get(n - 1);
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	// unit testing
	public static void main(String[] args) {
		testEmpty();
		testQueueDequeue();
		testRandom();
		testFullEmptyFull();
		testIterator();
		testIteratorMulti();
	}

	private static void testEmpty() {
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		assert q.isEmpty();
		assert q.size() == 0;
	}

	private static void testQueueDequeue() {
		RandomizedQueue<String> q = new RandomizedQueue<String>();

		q.enqueue("hello");
		assert !q.isEmpty();
		assert q.size() == 1;

		String s = q.sample();
		assert s.equals("hello");
		assert !q.isEmpty();
		assert q.size() == 1;

		s = q.dequeue();
		assert s.equals("hello");
		assert !q.isEmpty();
		assert q.size() == 0;
	}

	private static void testRandom() {
		RandomizedQueue<String> q = new RandomizedQueue<String>();

		q.enqueue("aaa");
		q.enqueue("bbb");
		q.enqueue("ccc");
		q.enqueue("ddd");
		q.enqueue("eee");

		String s;
		s = q.dequeue();
		// System.out.println(s);
		s = q.dequeue();
		// System.out.println(s);
		s = q.dequeue();
		// System.out.println(s);
		s = q.dequeue();
		// System.out.println(s);
		s = q.dequeue();
		// System.out.println(s);
	}

	private static void testFullEmptyFull() {
		RandomizedQueue<String> q = new RandomizedQueue<String>();

		q.enqueue("aaa");
		q.enqueue("bbb");
		q.enqueue("ccc");
		q.enqueue("ddd");
		q.enqueue("eee");

		String s;
		s = q.dequeue();
		System.out.println(s);
		s = q.dequeue();
		System.out.println(s);
		s = q.dequeue();
		System.out.println(s);
		s = q.dequeue();
		System.out.println(s);
		s = q.dequeue();
		System.out.println(s);

		q.enqueue("aaa");
		q.enqueue("bbb");
		q.enqueue("ccc");
		q.enqueue("ddd");
		q.enqueue("eee");

		s = q.dequeue();
		System.out.println(s);
		s = q.dequeue();
		System.out.println(s);
		s = q.dequeue();
		System.out.println(s);
		s = q.dequeue();
		System.out.println(s);
		s = q.dequeue();
		System.out.println(s);
	}

	private static void testIterator() {
		RandomizedQueue<String> q = new RandomizedQueue<String>();

		q.enqueue("aaa");
		q.enqueue("bbb");
		q.enqueue("ccc");
		q.enqueue("ddd");
		q.enqueue("eee");

		Iterator<String> i = q.iterator();

		while (i.hasNext()) {
			String s = i.next();
			// System.out.println(s);
		}

	}

	private static void testIteratorMulti() {
		RandomizedQueue<String> q = new RandomizedQueue<String>();

		q.enqueue("aaa");
		q.enqueue("bbb");
		q.enqueue("ccc");
		q.enqueue("ddd");
		q.enqueue("eee");

		for (String outer : q) {
			for (String inner : q) {
				System.out.println(outer + " " + inner);
			}
		}
	}

	private class RandomizedQueueIterator implements Iterator<Item> {

		private Item[] p;
		private int current, length;

		@SuppressWarnings("unchecked")
		public RandomizedQueueIterator() {
			length = n;
			p = (Item[]) new Object[length];

			for (int i = 0; i < length; i++) {
				p[i] = s[i];
			}

			shuffle(p, length);

			current = 0;

		}

		public boolean hasNext() {
			return current < length;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if (p[current]==null) {
				throw new NoSuchElementException();
			}
			return p[current++];
		}
	}

	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i < n; i++) {
			copy[i] = s[i];
		}
		s = copy;

	}

	private void shuffle(Item[] arr, int length) {
		for (int i = 0; i < length; i++) {
			int r = edu.princeton.cs.algs4.StdRandom.uniform(i + 1);
			exch(arr, i, r);
		}
	}

	private void exch(Item[] a, int i, int r) {
		Item tmp = a[i];
		a[i] = a[r];
		a[r] = tmp;
	}

	private Item get(int i) {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException("The queue is empty.");
		}
		if (isNeedToShuffle) {
			shuffle(s, n);
			isNeedToShuffle = false;
		}
		return s[i];
	}
}
