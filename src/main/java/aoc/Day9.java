package aoc;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import utils.Utils;

public class Day9 extends Puzzle {

	final List<Motion> motions;
	HashSet<Pos> vis;

	public Day9(String input) {
		super(input);
		this.motions = input.lines().map(Motion::new).collect(Collectors.toUnmodifiableList());
		this.vis = new HashSet<Pos>();
	}

	private void keepup(Pos head, Pos tail) {
		if (head.x > tail.x) {
			tail.x += 1;
		} else if (head.x < tail.x) {
			tail.x -= 1;
		}
		if (head.y > tail.y) {
			tail.y += 1;
		} else if (head.y < tail.y) {
			tail.y -= 1;
		}
	}

	@Override
	public String part1() {
		vis.clear();
		Pos head = new Pos(0, 0);
		Pos tail = new Pos(0, 0);

		for (Motion mot : motions) {
			for (int i = 0; i < mot.amount; i++) {
				head.move(mot.dir, 1);
				if (!head.adjacent(tail)) {
					keepup(head, tail);
				}
				vis.add(tail.clone());
			}
		}

		return String.valueOf(vis.size());
	}

	@Override
	public String part2() {
		vis.clear();
		Pos[] knots = new Pos[10];
		for (int i = 0; i < 10; i++)
			knots[i] = new Pos(0, 0);

		for (Motion mot : motions) {
			for (int i = 0; i < mot.amount; i++) {
				knots[0].move(mot.dir, 1);
				for (int k = 1; k < 10; k++) {
					if (!knots[k - 1].adjacent(knots[k])) {
						keepup(knots[k - 1], knots[k]);
					}
				}
				vis.add(knots[9].clone());
			}
		}

		return String.valueOf(vis.size());
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day9(Utils.read("/input9.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}
}

class Pos {
	int x;
	int y;

	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move(Direction dir, int amount) {
		switch (dir) {
		case UP:
			this.y += amount;
			break;
		case DOWN:
			this.y -= amount;
			break;
		case LEFT:
			this.x -= amount;
			break;
		case RIGHT:
			this.x += amount;
			break;
		}
	}

	public boolean adjacent(Pos other) {
		if (Math.abs(this.x - other.x) > 1)
			return false;
		if (Math.abs(this.y - other.y) > 1)
			return false;
		return true;
	}

	public Pos clone() {
		return new Pos(this.x, this.y);
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

enum Direction {
	UP, DOWN, LEFT, RIGHT
}

class Motion {
	final Direction dir;
	final int amount;

	public Motion(String line) {
		switch (line.charAt(0)) {
		case 'U':
			this.dir = Direction.UP;
			break;
		case 'D':
			this.dir = Direction.DOWN;
			break;
		case 'L':
			this.dir = Direction.LEFT;
			break;
		case 'R':
			this.dir = Direction.RIGHT;
			break;
		default:
			this.dir = Direction.UP;
		}
		this.amount = Integer.parseInt(line.substring(2));
	}
}