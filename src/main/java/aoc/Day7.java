package aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.Utils;

public class Day7 extends Puzzle {

	final List<String> lines;
	Node tree;

	public Day7(String input) {
		super(input);
		this.lines = input.lines().collect(Collectors.toUnmodifiableList());
		Node current = tree = new Node("dir /", null);

		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).startsWith("$ ls")) {
				List<String> files = lines.subList(i + 1, lines.size()).stream().takeWhile(s -> !s.startsWith("$"))
						.collect(Collectors.toUnmodifiableList());

				for (String f : files) {
					Node child = new Node(f, current);
					current.children.add(child);
				}
			} else if (lines.get(i).startsWith("$ cd ..")) {
				current = current.parent;
			} else if (lines.get(i).startsWith("$ cd")) {
				String name = lines.get(i).substring(5, lines.get(i).length());
				if (!name.equals("/")) {
					current = current.children.stream().filter(s -> s.name.equals(name)).findFirst().get();
				}
			}
		}
		tree.recalculateSizes();
	}

	private long sumSmall(Node node) {
		return node.children.stream().filter(c -> c.dir).mapToLong(c -> {
			if (c.size < 100000) {
				return c.size + sumSmall(c);
			}
			return sumSmall(c);
		}).sum();
	}

	@Override
	public String part1() {
		long sum = sumSmall(tree);
		return String.valueOf(sum);
	}

	private void findDeletable(ArrayList<Long> deletable, Node n, long min) {
		n.children.stream().filter(c -> c.dir).forEach(c -> {
			if (c.size >= min) {
				deletable.add(c.size);
			}
			findDeletable(deletable, c, min);
		});
	}

	@Override
	public String part2() {
		long unused = 70000000 - tree.size;
		long needed = 30000000 - unused;

		ArrayList<Long> deletable = new ArrayList<Long>();
		findDeletable(deletable, tree, needed);
		return String.valueOf(deletable.stream().sorted().findFirst().get());
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day7(Utils.readInput("/input7.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}
}

class Node {
	final String name;
	final boolean dir;
	final Node parent;

	long size;
	List<Node> children;

	public Node(String line, Node parent) {
		String[] split = line.split(" ");
		this.name = split[1];
		if (split[0].equals("dir")) {
			this.dir = true;
			this.size = 0;
		} else {
			this.dir = false;
			this.size = Integer.parseInt(split[0]);
		}

		this.parent = parent;
		this.children = new ArrayList<Node>();
	}

	public long recalculateSizes() {
		if (!dir) {
			return size;
		}
		size = children.stream().reduce(0l, (acc, n) -> acc + n.recalculateSizes(), Long::sum);
		return size;
	}
}