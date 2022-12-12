package aoc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.Utils;

public class Day12 extends Puzzle {

	final List<List<Integer>> elevation;
	final int lenx;
	final int leny;
	Pos start;
	Pos end;

	public Day12(String input) {
		super(input);
		this.elevation = input.lines().map(s -> s.chars().mapToObj(n -> {
			if (n == (int) 'S')
				return 254;
			else if (n == (int) 'E')
				return ((int) 'z') - ((int) 'a');
			else
				return n - (int) 'a';
		}).collect(Collectors.toUnmodifiableList())).collect(Collectors.toUnmodifiableList());
		this.lenx = elevation.get(0).size();
		this.leny = elevation.size();

		List<String> lines = input.lines().collect(Collectors.toUnmodifiableList());
		start = IntStream.range(0, leny).filter(l -> lines.get(l).contains("S"))
				.mapToObj(l -> new Pos(lines.get(l).indexOf('S'), l)).findFirst().get();
		end = IntStream.range(0, leny).filter(l -> lines.get(l).contains("E"))
				.mapToObj(l -> new Pos(lines.get(l).indexOf('E'), l)).findFirst().get();
	}

	private boolean validMove(Pos now, Pos after) {
		int nowElev = now.get(elevation);
		int afterElev = after.get(elevation);
		return afterElev <= (nowElev + 1);
	}

	private int dijkstra(Pos s) {
		HashSet<Pos> unvisited = new HashSet<>();
		for (int y = 0; y < leny; y++)
			for (int x = 0; x < lenx; x++)
				unvisited.add(new Pos(x, y));

		List<List<Integer>> distances = IntStream.range(0, leny)
				.mapToObj(n -> IntStream.range(0, lenx).mapToObj(m -> Integer.MAX_VALUE).collect(Collectors.toList()))
				.collect(Collectors.toList());

		List<List<Pos>> prev = IntStream.range(0, leny)
				.mapToObj(n -> IntStream.range(0, lenx).mapToObj(m -> (Pos) null).collect(Collectors.toList()))
				.collect(Collectors.toList());

		s.set(distances, 0);
		Pos current = s;

		main: while (!unvisited.isEmpty()) {
			List<Pos> unvis = unvisited.stream().collect(Collectors.toList());
			unvis.sort(Comparator.comparingInt(o -> o.get(distances)));
			current = unvis.get(0);

			if (current.equals(end)) {
				break main;
			}

			unvisited.remove(current.clone());
			for (Pos next : current.neighbors()) {
				if (validMove(current, next) && unvisited.contains(next)) {
					int alt = current.get(distances) + 1;
					if (alt < next.get(distances)) {
						next.set(distances, alt);
						next.set(prev, current.clone());
					}
				}
			}
		}

		int moves = 0;
		if (current.get(prev) != null || current.equals(s)) {
			while (current.get(prev) != null) {
				moves += 1;
				current = current.get(prev);
			}
		}
		return moves;
	}

	@Override
	public String part1() {
		return String.valueOf(dijkstra(start));
	}

	@Override
	public String part2() {
		List<Pos> starts = IntStream.range(0, leny).filter(e -> elevation.get(e).contains(0))
				.mapToObj(e -> new Pos(elevation.get(e).indexOf(0), e)).collect(Collectors.toUnmodifiableList());
		return String.valueOf(starts.stream().mapToInt(p -> dijkstra(p)).min().getAsInt());
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day12(Utils.read("/input12.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}

	private class Pos {
		int x;
		int y;

		public Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Pos clone() {
			return new Pos(this.x, this.y);
		}

		public <T> T get(List<List<T>> from) {
			return from.get(this.y).get(this.x);
		}

		public <T> T set(List<List<T>> to, T set) {
			return to.get(this.y).set(this.x, set);
		}

		public List<Pos> neighbors() {
			ArrayList<Pos> neighbors = new ArrayList<>(4);
			if (this.x < lenx - 1)
				neighbors.add(new Pos(this.x + 1, this.y));
			if (this.x > 0)
				neighbors.add(new Pos(this.x - 1, this.y));
			if (this.y < leny - 1)
				neighbors.add(new Pos(this.x, this.y + 1));
			if (this.y > 0)
				neighbors.add(new Pos(this.x, this.y - 1));
			return neighbors;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Pos))
				return false;
			Pos other = (Pos) o;
			return (this.x == other.x) && (this.y == other.y);
		}

		@Override
		public int hashCode() {
			return this.x * 31 + this.y * 31;
		}
	}
}