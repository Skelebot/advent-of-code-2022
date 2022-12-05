package aoc;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import utils.Utils;

public class PuzzleTests {
	@Test
	public void day1() throws IOException {
		Puzzle puzzle = new Day1(Utils.readInput("/test1.txt"));

		Assert.assertEquals("24000", puzzle.part1());
		Assert.assertEquals("45000", puzzle.part2());
	}

	@Test
	public void day2() throws IOException {
		Puzzle puzzle = new Day2(Utils.readInput("/test2.txt"));

		Assert.assertEquals("15", puzzle.part1());
		Assert.assertEquals("12", puzzle.part2());
	}

	@Test
	public void day3() throws IOException {
		Puzzle puzzle = new Day3(Utils.readInput("/test3.txt"));

		Assert.assertEquals("157", puzzle.part1());
		Assert.assertEquals("70", puzzle.part2());
	}

	@Test
	public void day4() throws IOException {
		Puzzle puzzle = new Day4(Utils.readInput("/test4.txt"));

		Assert.assertEquals("2", puzzle.part1());
		Assert.assertEquals("4", puzzle.part2());
	}

	@Test
	public void day5() throws IOException {
		Puzzle puzzle = new Day5(Utils.readInput("/test5.txt"));

		Assert.assertEquals("CMZ", puzzle.part1());
		Assert.assertEquals("MCD", puzzle.part2());
	}
}
