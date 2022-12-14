package aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.stream.Collectors;
import utils.Utils;

public class Day14 extends Puzzle {

	final List<Rock> rocks;
	final int bottom;
	final int floor;

	public Day14(String input) {
		super(input);
		this.rocks = input.lines().flatMap(l -> getRocks(l).stream()).collect(Collectors.toUnmodifiableList());
		this.bottom = this.rocks.stream().mapToInt(r -> Integer.max(r.start.y, r.end.y)).max().getAsInt();
		this.floor = bottom + 2;
	}

	private List<Rock> getRocks(String line) {
		ArrayList<Rock> rocks = new ArrayList<>();
		String[] positions = line.split(" -> ");
		for (int i = 0; i < positions.length - 1; i++) {
			rocks.add(new Rock(new Pos(positions[i]), new Pos(positions[i + 1])));
		}
		return rocks;
	}

	private boolean posValid(Pos s, HashSet<Pos> sand) {
		if (s.y >= floor)
			return false;
		if (sand.contains(s))
			return false;
		for (Rock r : rocks) {
			if (r.contains(s))
				return false;
		}
		return true;
	}

	private boolean simulate(Pos s, HashSet<Pos> sand) {
		if (posValid(new Pos(s.x, s.y + 1), sand)) {
			s.y += 1;
			return false;
		} else if (posValid(new Pos(s.x - 1, s.y + 1), sand)) {
			s.y += 1;
			s.x -= 1;
			return false;
		} else if (posValid(new Pos(s.x + 1, s.y + 1), sand)) {
			s.y += 1;
			s.x += 1;
			return false;
		} else
			return true;
	}

	@Override
	public String part1() {
		HashSet<Pos> sand = new HashSet<>();
		int simulated = 0;
		main: while (true) {
			Pos s = new Pos(500, 0);
			while (!simulate(s, sand)) {
				if (s.y > bottom)
					break main;
			}
			sand.add(s);
			simulated++;
		}
		return String.valueOf(simulated);
	}

	@Override
	public String part2() {
		HashSet<Pos> sand = new HashSet<>();
		int simulated = 0;
		main: while (true) {
			Pos s = new Pos(500, 0);
			while (!simulate(s, sand))
				;
			if (s.y == 0) {
				simulated += 1;
				break main;
			}
			sand.add(s);
			simulated++;
		}
		return String.valueOf(simulated);
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day14(Utils.read("/input14.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}

	private class Rock {
		final boolean horizontal;
		final Pos start;
		final Pos end;

		public Rock(Pos start, Pos end) {
			if (start.x < end.x) {
				this.start = start;
				this.end = end;
			} else {
				this.start = end;
				this.end = start;
			}
			if (start.y == end.y)
				this.horizontal = true;
			else
				this.horizontal = false;
		}

		public boolean contains(Pos p) {
			if (horizontal) {
				return (p.y == start.y) && (p.x >= start.x) && (p.x <= end.x);
			} else {
				if (start.y < end.y)
					return (p.x == start.x) && (p.y >= start.y) && (p.y <= end.y);
				else
					return (p.x == start.x) && (p.y >= end.y) && (p.y <= start.y);
			}
		}
	}

	private class Pos {
		int x;
		int y;

		public Pos(String pos) {
			String[] coords = pos.strip().split(",");
			this.x = Integer.parseInt(coords[0]);
			this.y = Integer.parseInt(coords[1]);
		}

		public Pos(int x, int y) {
			this.x = x;
			this.y = y;
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
