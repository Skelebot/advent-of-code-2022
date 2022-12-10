package aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import utils.Utils;

public class Day8 extends Puzzle {

	final List<List<Integer>> rows;
	final List<List<Integer>> cols;
	final ArrayList<ArrayList<Boolean>> vis;
	final int len;

	public Day8(String input) {
		super(input);
		this.rows = input.lines()
				.map(l -> l.chars().mapToObj(i -> Character.getNumericValue((char) i)).collect(Collectors.toList()))
				.collect(Collectors.toUnmodifiableList());
		this.len = rows.size();
		this.cols = IntStream.range(0, len)
				.mapToObj(x -> rows.stream().map(row -> row.get(x)).collect(Collectors.toList()))
				.collect(Collectors.toList());
		this.vis = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			ArrayList<Boolean> v = new ArrayList<>(Collections.nCopies(len, false));
			vis.add(v);
		}
	}

	private boolean checkVis(List<Integer> l, int idx) {
		int tree = l.get(idx);
		return l.subList(0, idx).stream().allMatch(t -> t < tree)
				|| l.subList(idx + 1, len).stream().allMatch(t -> t < tree);
	}

	@Override
	public String part1() {
		for (int y = 0; y < len; y++) {
			for (int x = 0; x < len; x++) {
				if (checkVis(rows.get(y), x) || checkVis(cols.get(x), y))
					vis.get(y).set(x, true);
			}
		}
		long count = vis.stream().mapToLong(r -> r.stream().filter(t -> t).count()).sum();
		return String.valueOf(count);
	}

	private <T> List<T> reversed(List<T> l) {
		List<T> lr = new ArrayList<>(l);
		Collections.reverse(lr);
		return lr;
	}

	private long rateView(List<Integer> subview, int tree) {
		long count = subview.stream().takeWhile(n -> n < tree).count();
		if (count < subview.size())
			count += 1;
		return count;
	}

	private long treeScore(int x, int y) {
		int tree = rows.get(y).get(x);
		long left = rateView(reversed(rows.get(y).subList(0, x)), tree);
		long right = rateView(rows.get(y).subList(x + 1, len), tree);
		long up = rateView(reversed(cols.get(x).subList(0, y)), tree);
		long down = rateView(cols.get(x).subList(y + 1, len), tree);
		return up * down * left * right;
	}

	@Override
	public String part2() {
		long max = LongStream.range(1, len - 1)
				.flatMap(y -> IntStream.range(1, len - 1).mapToLong(x -> treeScore(x, (int) y))).max().getAsLong();
		return String.valueOf(max);
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day8(Utils.read("/input8.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}
}
