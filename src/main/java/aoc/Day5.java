package aoc;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import utils.Utils;

public class Day5 extends Puzzle {

	final List<String> start;
	final List<String> commands;

	public Day5(String input) {
		super(input);
		this.start = input.lines().takeWhile(s -> !s.startsWith(" 1")).collect(Collectors.toUnmodifiableList());
		this.commands = input.lines().dropWhile(s -> !s.startsWith("m")).collect(Collectors.toUnmodifiableList());
	}

	@Override
	public String part1() {
		Crane crates = new Crane(start);
		return crates.move9000(commands);
	}

	@Override
	public String part2() {
		Crane crates = new Crane(start);
		return crates.move9001(commands);
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day5(Utils.read("/input5.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}
}

class Crane {
	@SuppressWarnings("unchecked")
	final Stack<Character>[] stacks = new Stack[9];
	Pattern number = Pattern.compile("\\d+");

	public Crane(List<String> init) {
		for (int i = 0; i < 9; i++) {
			stacks[i] = new Stack<Character>();
		}

		for (String line : init) {
			int stack = 0;
			for (int i = 1; i < line.length(); i += 4) {
				Character c = line.charAt(i);
				if (c != ' ')
					this.stacks[stack].add(0, line.charAt(i));
				stack += 1;
			}
		}
	}

	public String topCrates() {
		return Arrays.stream(this.stacks).takeWhile(s -> !s.isEmpty()).map(s -> s.lastElement().toString())
				.collect(Collectors.joining());
	}

	class Command {
		public final int amount;
		public final int from;
		public final int to;

		public Command(String line) {
			Matcher m = number.matcher(line);
			Iterator<Integer> nums = m.results().map(r -> Integer.parseInt(r.group())).iterator();
			this.amount = nums.next();
			this.from = nums.next() - 1;
			this.to = nums.next() - 1;
		}
	}

	public String move9000(List<String> commands) {
		commands.stream().map(Command::new).forEach(c -> {
			for (int i = 0; i < c.amount; i++) {
				this.stacks[c.to].push(this.stacks[c.from].pop());
			}
		});

		return this.topCrates();
	}

	public String move9001(List<String> commands) {
		commands.stream().map(Command::new).forEach(c -> {
			int size = stacks[c.from].size();
			List<Character> sub = stacks[c.from].subList(size - c.amount, size);
			this.stacks[c.to].addAll(sub);
			for (int i = 0; i < c.amount; i++) {
				this.stacks[c.from].pop();
			}
		});

		return this.topCrates();
	}
}