package aoc;

import utils.Utils;

public class Day6 extends Puzzle {

	final String input;

	public Day6(String input) {
		super(input);
		this.input = input;
	}

	@Override
	public String part1() {
		return String.valueOf(startOfMessage(input, 4));
	}

	@Override
	public String part2() {
		return String.valueOf(startOfMessage(input, 14));
	}

	private int startOfMessage(String in, int len) {
		for (int i = 0; i < in.length(); i++) {
			if (input.substring(i, i + len).chars().distinct().count() == len) {
				return i + len;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day6(Utils.readInput("/input6.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}
}
