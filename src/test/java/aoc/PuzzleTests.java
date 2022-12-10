package aoc;

import org.junit.Assert;
import org.junit.Test;

import utils.Utils;

public class PuzzleTests {
	@Test
	public void day1() {
		Puzzle puzzle = new Day1(Utils.read("/test1.txt"));

		Assert.assertEquals("24000", puzzle.part1());
		Assert.assertEquals("45000", puzzle.part2());
	}

	@Test
	public void day2() {
		Puzzle puzzle = new Day2(Utils.read("/test2.txt"));

		Assert.assertEquals("15", puzzle.part1());
		Assert.assertEquals("12", puzzle.part2());
	}

	@Test
	public void day3() {
		Puzzle puzzle = new Day3(Utils.read("/test3.txt"));

		Assert.assertEquals("157", puzzle.part1());
		Assert.assertEquals("70", puzzle.part2());
	}

	@Test
	public void day4() {
		Puzzle puzzle = new Day4(Utils.read("/test4.txt"));

		Assert.assertEquals("2", puzzle.part1());
		Assert.assertEquals("4", puzzle.part2());
	}

	@Test
	public void day5() {
		Puzzle puzzle = new Day5(Utils.read("/test5.txt"));

		Assert.assertEquals("CMZ", puzzle.part1());
		Assert.assertEquals("MCD", puzzle.part2());
	}

	@Test
	public void day6() {
		Puzzle puzzle = new Day6("bvwbjplbgvbhsrlpgdmjqwftvncz");
		Assert.assertEquals("5", puzzle.part1());
		Assert.assertEquals("23", puzzle.part2());

		Puzzle puzzle1 = new Day6("nppdvjthqldpwncqszvftbrmjlhg");
		Assert.assertEquals("6", puzzle1.part1());
		Assert.assertEquals("23", puzzle1.part2());

		Puzzle puzzle2 = new Day6("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg");
		Assert.assertEquals("10", puzzle2.part1());
		Assert.assertEquals("29", puzzle2.part2());

		Puzzle puzzle3 = new Day6("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw");
		Assert.assertEquals("11", puzzle3.part1());
		Assert.assertEquals("26", puzzle3.part2());
	}

	@Test
	public void day7() {
		Puzzle puzzle = new Day7(Utils.read("/test7.txt"));

		Assert.assertEquals("95437", puzzle.part1());
		Assert.assertEquals("24933642", puzzle.part2());
	}

	@Test
	public void day8() {
		Puzzle puzzle = new Day8(Utils.read("/test8.txt"));

		Assert.assertEquals("21", puzzle.part1());
		Assert.assertEquals("8", puzzle.part2());
	}

	@Test
	public void day9() {
		Puzzle puzzle1 = new Day9(Utils.read("/test9_1.txt"));

		Assert.assertEquals("13", puzzle1.part1());
		Assert.assertEquals("1", puzzle1.part2());

		Puzzle puzzle2 = new Day9(Utils.read("/test9_2.txt"));
		Assert.assertEquals("36", puzzle2.part2());
	}

	@Test
	public void day10() {
		Puzzle puzzle = new Day10(Utils.read("/test10.txt"));

		Assert.assertEquals("13140", puzzle.part1());
		Assert.assertEquals(Utils.read("/output10_2.txt"), puzzle.part2());
	}
}
