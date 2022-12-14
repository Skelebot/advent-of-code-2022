package aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import utils.Utils;

public class Day13 extends Puzzle {

	final List<Pair> pairs;
	ArrayList<Node> all;

	public Day13(String input) {
		super(input);
		this.pairs = Arrays.stream(input.split("\n\n")).map(Pair::new).collect(Collectors.toUnmodifiableList());
		this.all = input.lines().filter(l -> !l.isBlank()).map(l -> new Node(l, null))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public String part1() {
		int sum = 0;
		for (int i = 0; i < pairs.size(); i++) {
			if (pairs.get(i).compare())
				sum += i + 1;
		}
		return String.valueOf(sum);
	}

	@Override
	public String part2() {
		Node divider1 = new Node("[[2]]", null);
		long less1 = this.all.stream().filter(n -> new Pair(n, divider1).compare()).count() + 1;
		Node divider2 = new Node("[[6]]", null);
		long less2 = this.all.stream().filter(n -> new Pair(n, divider2).compare()).count() + 2;
		return String.valueOf(less1 * less2);
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day13(Utils.read("/input13.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}

	class Pair {
		final Node left;
		final Node right;

		public Pair(String lines) {
			String left = lines.lines().findFirst().get();
			String right = lines.lines().skip(1).findFirst().get();
			this.left = readPacket(left);
			this.right = readPacket(right);
		}

		public Pair(Node left, Node right) {
			this.left = left;
			this.right = right;
		}

		private Node readPacket(String packet) {
			return new Node(packet, null);
		}

		public boolean compare() {
			return compare(this.left.children, this.right.children) == 1;
		}

		// 1 - true
		// 0 - false
		// -1 - inconsequential
		public int compare(List<Node> leftList, List<Node> rightList) {
			Iterator<Node> l = leftList.listIterator();
			Iterator<Node> r = rightList.listIterator();
			while (l.hasNext() && r.hasNext()) {
				Node ln = l.next();
				Node rn = r.next();
				if (ln.single && rn.single) {
					if (ln.value < rn.value) {
						return 1;
					} else if (ln.value > rn.value) {
						return 0;
					} else
						continue;
				} else if (ln.single) {
					ln = new Node(List.of(ln.value), ln.parent);
				} else if (rn.single) {
					rn = new Node(List.of(rn.value), rn.parent);
				}
				int ret = compare(ln.children, rn.children);
				if (ret == -1)
					continue;
				else
					return ret;
			}
			if (!l.hasNext() && r.hasNext()) {
				return 1;
			} else if (l.hasNext() && !r.hasNext()) {
				return 0;
			}
			return -1;
		}
	}

	class Node {
		boolean single;
		int value;
		Node parent;
		ArrayList<Node> children;

		public Node(int i, Node parent) {
			this.single = true;
			this.parent = parent;
			this.value = i;
		}

		public Node(List<Integer> children, Node parent) {
			this.single = false;
			this.parent = parent;
			this.children = new ArrayList<>();
			for (Integer child : children) {
				this.children.add(new Node(child, this));
			}
		}

		public Node(String line, Node parent) {
			this.children = new ArrayList<>();
			this.single = false;
			this.parent = parent;
			int start = 1;
			while (start < line.length()) {
				if (line.charAt(start) == '[') {
					int closing = findMatching(line, start);
					String sub = line.substring(start, closing + 1);
					this.children.add(new Node(sub, this));
					start = closing + 2;
				} else {
					int end = line.indexOf(',', start);
					if (end == -1)
						end = line.length() - 1;
					if (line.charAt(start) == ']') {
						start = end + 1;
						continue;
					}
					String sub = line.substring(start, end);
					this.children.add(new Node(Integer.parseInt(sub), this));
					start = end + 1;
				}
			}
		}

		public int findMatching(String line, int start) {
			int lvl = 1;
			for (int i = start + 1; i < line.length(); i++) {
				if (line.charAt(i) == '[')
					lvl++;
				else if (line.charAt(i) == ']')
					lvl--;
				if (lvl == 0)
					return i;
			}
			return -1;
		}
	}
}