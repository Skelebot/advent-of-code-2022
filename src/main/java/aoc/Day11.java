package aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Comparator;
import java.util.stream.Collectors;
import utils.Utils;

public class Day11 extends Puzzle {

	final List<Monkey> monkeysOriginal;

	public Day11(String input) {
		super(input);
		this.monkeysOriginal = Arrays.stream(input.split("\n\n")).map(Monkey::new)
				.collect(Collectors.toUnmodifiableList());
	}

	private void round(List<Monkey> monkeys, long divide) {
		for (Monkey m : monkeys) {
			while (!m.items.isEmpty()) {
				int monke = m.inspectNext(divide);
				monkeys.get(monke).items.add(m.items.remove(0));
			}
		}
	}

	private long monkeyBusiness(List<Monkey> monkeys) {
		List<Long> inspected = monkeys.stream().map(m -> m.inspected).collect(Collectors.toList());
		inspected.sort(Comparator.reverseOrder());
		return inspected.stream().limit(2).reduce(1l, (a, b) -> a * b);
	}

	@Override
	public String part1() {
		List<Monkey> monkeys = monkeysOriginal.stream().map(Monkey::cloneMonkey).collect(Collectors.toList());
		for (int i = 1; i <= 20; i++) {
			round(monkeys, 0);
		}
		return String.valueOf(monkeyBusiness(monkeys));
	}

	@Override
	public String part2() {
		List<Monkey> monkeys = monkeysOriginal.stream().map(Monkey::cloneMonkey).collect(Collectors.toList());
		long div = monkeysOriginal.stream().mapToLong(m -> m.div).reduce(1, (a, b) -> a * b);
		for (int i = 1; i <= 10000; i++) {
			round(monkeys, div);
		}
		return String.valueOf(monkeyBusiness(monkeys));
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day11(Utils.read("/input11.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}
}

class Monkey {
	long inspected;
	ArrayList<Long> items;
	Function<Long, Long> operation;
	Function<Long, Integer> test;
	long div;

	public Monkey(String in) {
		Iterator<String> lines = in.lines().iterator();
		this.items = new ArrayList<>();
		lines.next();
		Pattern num = Pattern.compile("\\d+");
		Matcher itemMatcher = num.matcher(lines.next());
		while (itemMatcher.find()) {
			this.items.add(Long.parseLong(itemMatcher.group()));
		}

		String opLine = lines.next();
		Pattern op = Pattern.compile("(\\*|\\+)");
		Matcher opMatcher = op.matcher(opLine);
		opMatcher.find();
		char operand = opMatcher.group().charAt(0);

		Matcher modifMatcher = num.matcher(opLine);
		if (modifMatcher.find()) {
			final int modif = Integer.parseInt(modifMatcher.group());
			if (operand == '+')
				this.operation = (x) -> (x + modif);
			else if (operand == '*')
				this.operation = (x) -> (x * modif);
		} else {
			if (operand == '+')
				this.operation = (x) -> (x + x);
			else
				this.operation = (x) -> (x * x);
		}

		final long div = findLong(lines.next(), num);
		this.div = div;
		final int trueIdx = (int) findLong(lines.next(), num);
		final int falseIdx = (int) findLong(lines.next(), num);
		this.test = (x) -> {
			if ((x % div) == 0)
				return trueIdx;
			else
				return falseIdx;
		};
		this.inspected = 0;
	}

	private Monkey(ArrayList<Long> items, Function<Long, Long> operation, Function<Long, Integer> test, long div) {
		this.inspected = 0;
		this.items = new ArrayList<>();
		this.items.addAll(items);
		this.operation = operation;
		this.div = div;
		this.test = test;
	}

	public int inspectNext(long divide) {
		this.inspected++;
		long item = this.items.get(0);
		item = this.operation.apply(item);
		if (divide == 0) {
			item /= 3;
		} else
			item %= divide;
		this.items.set(0, item);
		int monke = this.test.apply(item);
		return monke;
	}

	private long findLong(String line, Pattern p) {
		Matcher matcher = p.matcher(line);
		matcher.find();
		return Long.parseLong(matcher.group());
	}

	public static Monkey cloneMonkey(Monkey m) {
		return new Monkey(m.items, m.operation, m.test, m.div);
	}
}