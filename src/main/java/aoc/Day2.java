package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import utils.Utils;

public class Day2 {
  static enum Choice {
    ROCK(1),
    PAPER(2),
    SCISSORS(3),
    INVALID(-1); // Avoid using exceptions

    public final int score;

    Choice(int score) { this.score = score; }
    static Choice fromChar(char c) {
      switch (c) {
      case 'A':
        return ROCK;
      case 'B':
        return PAPER;
      case 'C':
        return SCISSORS;
      case 'X':
        return ROCK;
      case 'Y':
        return PAPER;
      case 'Z':
        return SCISSORS;
      default:
        return INVALID;
      }
    }
    boolean winsOver(Choice other) {
      return (this == PAPER && other == ROCK) ||
          (this == ROCK && other == SCISSORS) ||
          (this == SCISSORS && other == PAPER);
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
    Choice losing() { return this.winning().winning(); }
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
    public int score() { return this.choice2.score + winScore(); }
  }

  public static int part1(String input) {
    return input.lines()
        .map(s -> new Round(s, true))
        .mapToInt(r -> r.score())
        .sum();
  }

  public static int part2(String input) {
    return input.lines()
        .map(s -> new Round(s, false))
        .mapToInt(r -> r.score())
        .sum();
  }

  public static void main(String[] args)
      throws URISyntaxException, IOException {
    String input = Utils.readInput("/input2.txt");

    System.out.println("Part 1: " + part1(input));
    System.out.println("Part 2: " + part2(input));
  }
}
