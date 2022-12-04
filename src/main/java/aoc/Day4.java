package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import utils.Utils;

public class Day4 {

  public static long part1(String input) {
    return input.lines()
        .map(Range::fromLine)
        .filter(Range::rangesOverlapCompletely)
        .count();
  }

  public static long part2(String input) {
    return input.lines()
        .map(Range::fromLine)
        .filter(Range::rangesOverlap)
        .count();
  }

  public static void main(String[] args)
      throws URISyntaxException, IOException {
    String input = Utils.readInput("/input4.txt");

    System.out.println("Part 1: " + part1(input));
    System.out.println("Part 2: " + part2(input));
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

    Range[] ranges = {new Range(start1, end1), new Range(start2, end2)};
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
    return ((other.start >= this.start) && (other.start <= this.end)) ||
        ((other.end <= this.end) && (other.end >= this.start));
  }
}