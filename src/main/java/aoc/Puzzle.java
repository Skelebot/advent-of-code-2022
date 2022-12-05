package aoc;

public abstract class Puzzle {
	final String input;

	public Puzzle(String input) {
		this.input = input;
	}

	public abstract String part1();

	public abstract String part2();
}
