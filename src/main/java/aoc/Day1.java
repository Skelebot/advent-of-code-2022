package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import utils.Utils;

public class Day1 {

  public static int part1(String input) {
    Stream<String> elves = Arrays.stream(input.split("\n\n"));
    Stream<Integer> sums = elves.map(
        s -> Arrays.stream(s.split("\n"))
        .mapToInt(Integer::parseInt)
        .sum()
    );

    return sums.sorted(Comparator.reverseOrder()).findFirst().orElse(0);
  }

  public static int part2(String input) {
    Stream<String> elves = Arrays.stream(input.split("\n\n"));
    Stream<Integer> sums = elves.map(
        s -> Arrays.stream(s.split("\n"))
        .mapToInt(Integer::parseInt)
        .sum()
    );

    return sums.sorted(Comparator.reverseOrder()).limit(3).reduce(0, Integer::sum);
  }

  public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Utils.readInput("/input1.txt");
        
        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }
}
