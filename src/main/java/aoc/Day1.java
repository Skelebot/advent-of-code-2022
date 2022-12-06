package aoc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import utils.Utils;

public class Day1 extends Puzzle {
	
	List<Integer> sums;

	public Day1(String input) {
		super(input);
		Stream<String> elves = Arrays.stream(input.split("\n\n"));
		this.sums = elves.map(s -> Arrays.stream(s.split("\n")).mapToInt(Integer::parseInt).sum())
				.collect(Collectors.toList());
		this.sums.sort(Comparator.reverseOrder());
	}

	public String part1() {
		return sums.get(0).toString();
	}

	public String part2() {
		return sums.stream().limit(3).reduce(0, Integer::sum).toString();
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day1(Utils.readInput("/input1.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}
}
