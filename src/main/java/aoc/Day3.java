package aoc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import utils.Utils;

public class Day3 extends Puzzle {

	final List<Rucksack> sacks;

	public Day3(String input) {
		super(input);
		this.sacks = input.lines().map(Rucksack::new).collect(Collectors.toList());
	}

	public String part1() {
		int sum = sacks.stream().map(Rucksack::common).mapToInt(Rucksack::priority).sum();
		return String.valueOf(sum);
	}

	public String part2() {
		int sum = 0;
		for (int i = 0; i <= sacks.size() - 3; i += 3) {
			sum += Rucksack.priority(Rucksack.badge(sacks.subList(i, i + 3)));
		}
		return String.valueOf(sum);
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day3(Utils.readInput("/input3.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}
}

class Rucksack {
	private List<Character> left;
	private List<Character> right;

	public Rucksack(String line) {
		int items = line.length() / 2;
		this.left = line.chars().limit(items).mapToObj(c -> Character.valueOf((char) c)).collect(Collectors.toList());
		this.right = line.chars().skip(items).mapToObj(c -> Character.valueOf((char) c)).collect(Collectors.toList());
	}

	List<Character> all() {
		return Stream.concat(left.stream(), right.stream()).collect(Collectors.toList());
	}

	Character common() {
		return left.stream().filter(right::contains).findFirst().orElse(Character.valueOf(' '));
	}

	static int priority(Character c) {
		char cv = c.charValue();
		if (cv >= 'a' && cv <= 'z') {
			return (int) cv - (int) 'a' + 1;
		} else {
			return (int) cv - (int) 'A' + 27;
		}
	}

	static Character badge(List<Rucksack> sacks) {
		List<Character> acc = sacks.get(0).all();
		for (Rucksack sack : sacks) {
			acc.retainAll(sack.all());
		}
		return acc.get(0);
	}
}