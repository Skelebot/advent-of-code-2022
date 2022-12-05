package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import utils.Utils;

public class Day2 extends Puzzle {

	final List<String> lines;

	public Day2(String input) {
		super(input);
		this.lines = input.lines().collect(Collectors.toUnmodifiableList());
	}

	public String part1() {
		int sum = lines.stream().map(s -> new Round(s, true)).mapToInt(r -> r.score()).sum();
		return String.valueOf(sum);
	}

	public String part2() {
		int sum = lines.stream().map(s -> new Round(s, false)).mapToInt(r -> r.score()).sum();
		return String.valueOf(sum);
	}

	public static void main(String[] args) throws URISyntaxException, IOException {
		Puzzle puzzle = new Day2(Utils.readInput("/input2.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}

	static enum Choice {
		ROCK(1), PAPER(2), SCISSORS(3), INVALID(-1); // Avoid using exceptions

		public final int score;

		Choice(int score) {
			this.score = score;
		}

		static Choice fromChar(char c) {
			switch (c) {
			case 'A':
			case 'X':
				return ROCK;
			case 'B':
			case 'Y':
				return PAPER;
			case 'C':
			case 'Z':
				return SCISSORS;
			default:
				return INVALID;
			}
		}

		boolean winsOver(Choice other) {
			return (this == PAPER && other == ROCK) || (this == ROCK && other == SCISSORS)
					|| (this == SCISSORS && other == PAPER);
		}

		Choice winning() {
			switch (this) {
			case PAPER:
				return SCISSORS;
			case SCISSORS:
				return ROCK;
			case ROCK:
				return PAPER;
			default:
				return INVALID;
			}
		}

		Choice losing() {
			return this.winning().winning();
		}
	}

	static class Round {
		final Choice choice1;
		final Choice choice2;

		public Round(String round, boolean part1) {
			this.choice1 = Choice.fromChar(round.charAt(0));
			if (part1) {
				this.choice2 = Choice.fromChar(round.charAt(2));
			} else {
				switch (round.charAt(2)) {
				case 'X':
					this.choice2 = choice1.losing();
					break;
				case 'Y':
					this.choice2 = choice1;
					break;
				case 'Z':
					this.choice2 = choice1.winning();
					break;
				default:
					this.choice2 = Choice.INVALID;
				}
			}
		}

		public int winScore() {
			if (this.choice2.winsOver(this.choice1)) {
				return 6;
			} else if (this.choice1 == this.choice2) {
				return 3;
			} else {
				return 0;
			}
		}

		public int score() {
			return this.choice2.score + winScore();
		}
	}
}
