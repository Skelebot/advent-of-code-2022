package aoc;

import java.util.List;
import java.util.stream.Collectors;

import utils.Utils;

public class Day4 extends Puzzle {

	final List<Range[]> ranges;

	public Day4(String input) {
		super(input);
		this.ranges = input.lines().map(Range::fromLine).collect(Collectors.toUnmodifiableList());
	}

	public String part1() {
		long count = ranges.stream().filter(Range::rangesOverlapCompletely).count();
		return String.valueOf(count);
	}

	public String part2() {
		long count = ranges.stream().filter(Range::rangesOverlap).count();
		return String.valueOf(count);
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day4(Utils.read("/input4.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}
}

class Range {
	int start;
	int end;

	public Range(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public static Range[] fromLine(String line) {
		int hyphen = line.indexOf('-', 0);
		int comma = line.indexOf(',', 0);
		int hyphen2 = line.indexOf('-', comma);

		int start1 = Integer.parseInt(line.substring(0, hyphen));
		int end1 = Integer.parseInt(line.substring(hyphen + 1, comma));
		int start2 = Integer.parseInt(line.substring(comma + 1, hyphen2));
		int end2 = Integer.parseInt(line.substring(hyphen2 + 1, line.length()));

		Range[] ranges = { new Range(start1, end1), new Range(start2, end2) };
		return ranges;
	}

	public static boolean rangesOverlapCompletely(Range[] ranges) {
		return ranges[0].contains(ranges[1]) || ranges[1].contains(ranges[0]);
	}

	public static boolean rangesOverlap(Range[] ranges) {
		return ranges[0].overlaps(ranges[1]) || ranges[1].overlaps(ranges[0]);
	}

	public boolean contains(Range other) {
		return (other.start >= this.start) && (other.end <= this.end);
	}

	public boolean overlaps(Range other) {
		return ((other.start >= this.start) && (other.start <= this.end))
				|| ((other.end <= this.end) && (other.end >= this.start));
	}
}