package by.org.singleton.aldorithm.structure;


class IosifRing {
	public static void main(String... sfg) {
		IosifRing r = new IosifRing(41);
		System.out.println(r.fight());
	}

	static class Node {
		private Node next;
		private Node prev;
		private Object data;
		int index;

		public Node(Object data, int index) {
			this.data = data;
			this.index = index;
		}
	}

	private Node head;
	int size;

	IosifRing(int n) {
		size = n;
		for (int i = 0; i < n; i++) {
			add(i + 1);
		}
		Node tail = tail();
		tail.next = head;
		head.prev = tail;
	}

	private void add(Object data) {
		if (head == null) {
			head = new Node(data, 0);
			return;
		}
		Node tail = tail();

		tail.next = new Node(data, tail.index + 1);
		tail.next.prev = tail;
	}

	int fight() {
		Node temp = head;
		while (size != 1) {
			Node nextFromNext = temp.next.next;
			remove(temp.next);
			temp = nextFromNext;
		}
		return (int) temp.data;
	}

	private Node tail() {
		Node tail = head;
		while (tail.next != null) {
			tail = tail.next;
		}
		return tail;
	}

	public void remove(Node elem) {
		size--;
		Node prev = elem.prev;
		Node next = elem.next;
		prev.next = next;
		next.prev = prev;
	}

}
