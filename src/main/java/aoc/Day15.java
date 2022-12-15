package aoc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.*;
import java.util.regex.*;

import utils.Utils;

public class Day15 extends Puzzle {
	final List<Sensor> sensors;
    int xmin;
    int xmax;
    final boolean test;

	public Day15(String input, boolean test) {
		super(input);
		this.sensors = input.lines().map(Sensor::new).collect(Collectors.toUnmodifiableList());
        this.xmin = sensors.stream().mapToInt(s -> s.beacon.x).min().getAsInt();
        this.xmax = sensors.stream().mapToInt(s -> s.beacon.x).max().getAsInt();
        for(Sensor s : sensors) {
            int reachx = s.sensor.x - s.dist;
            if(reachx < xmin) xmin = reachx;
            reachx = s.sensor.x + s.dist;
            if(reachx > xmax) xmax = reachx;
        }
        this.test = test;
	}

	public String part1() {
        int sum = 0;
        Pos p = new Pos(xmin, 2000000);
        if(test) p = new Pos(xmin, 10);

        main: while(p.x <= xmax) {
            for(Sensor s : this.sensors) {
                if(s.excludes(p, true)) {
                    sum++;
                    p.x++;
                    continue main;
                }
            }
            p.x++;
        }
		return String.valueOf(sum);
	}

	public String part2() {
        Pos p = new Pos(0, 0);
        int max = 4000000;
        if(test) max = 20;

        outer: for(int y = 0; y <= max; y++) {
            inner: for(int x = 0; x <= max; x++) {
               p.x = x;
               p.y = y;
               for(Sensor s : this.sensors) {
                   if(s.excludes(p, false)) {
                       int w = s.tpx(p);
                       x += w;
                       continue inner;
                   }
               }
               break outer;
            }
        }

		return String.valueOf(((long)p.x * 4000000l) + (long)p.y);
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day15(Utils.read("/input15.txt"), false);

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}

    class Sensor {
        final Pos sensor;
        final Pos beacon;
        final int dist;

        public Sensor(String line) {
            Pattern pat = Pattern.compile("-?\\d+");
            Matcher num = pat.matcher(line);
            String[]matches = num.results().map(MatchResult::group).toArray(String[]::new);
            this.sensor = new Pos(Integer.parseInt(matches[0]), Integer.parseInt(matches[1]));
            this.beacon = new Pos(Integer.parseInt(matches[2]), Integer.parseInt(matches[3]));
            this.dist = this.sensor.distance(this.beacon);
        }

        public boolean excludes(Pos p, boolean part1) {
            if(part1) if(p.equals(this.beacon)) return false;
            return this.sensor.distance(p) <= dist;
        }

        public int tpx(Pos p) {
            return dist - this.sensor.distance(p);
        }
    }

	private class Pos {
		int x;
		int y;

		public Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}

        public int distance(Pos other) {
            return Math.abs(other.y - this.y) + Math.abs(other.x - this.x);
        }

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Pos))
				return false;
			Pos other = (Pos) o;
			return (this.x == other.x) && (this.y == other.y);
		}

		@Override
		public int hashCode() {
			return this.x * 31 + this.y * 31;
		}
	}
}
