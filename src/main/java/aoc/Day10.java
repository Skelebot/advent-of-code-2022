package aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import utils.Utils;

public class Day10 extends Puzzle {

	final List<String> lines;

	public Day10(String input) {
		super(input);
		this.lines = input.lines().collect(Collectors.toUnmodifiableList());
	}

	@Override
	public String part1() {
		Cpu cpu = new Cpu(lines);
		long strength = 0;
		while (cpu.cycle <= 220) {
			int v = cpu.tick();
			if ((cpu.cycle + 20) % 40 == 0) {
				strength += v * cpu.cycle;
			}
		}
		return String.valueOf(strength);
	}

	private boolean shouldDraw(int cycle, int sprite) {
		return Math.abs(sprite - cycle) <= 1;
	}

	@Override
	public String part2() {
		ArrayList<String> screen = new ArrayList<>();
		for (int i = 0; i < 6; i++)
			screen.add(new String());

		Cpu cpu = new Cpu(lines);
		while (cpu.cycle < 240) {
			int v = cpu.tick();
			int screen_y = (cpu.cycle - 1) / 40;
			int screen_x = (cpu.cycle - 1) - (screen_y * 40);
			if (shouldDraw(v, screen_x)) {
				screen.set(screen_y, screen.get(screen_y).concat("#"));
			} else {
				screen.set(screen_y, screen.get(screen_y).concat("."));
			}
		}
		return screen.stream().collect(Collectors.joining("\n"));
	}

	public static void main(String[] args) {
		Puzzle puzzle = new Day10(Utils.read("/input10.txt"));

		System.out.println("Part 1: " + puzzle.part1());
		System.out.println("Part 2: " + puzzle.part2());
	}
}

class Cpu {
	ArrayList<String> program;
	int pc;
	int x;
	int cycle;
	boolean busy;

	public Cpu(List<String> pr) {
		this.program = new ArrayList<>(pr);
		this.x = 1;
		this.cycle = 0;
		this.pc = 0;
		this.busy = false;
	}

	public int tick() {
		this.cycle += 1;
		if (this.busy) {
			this.busy = false;
			int val = Integer.parseInt(this.program.get(pc).substring(5));
			this.x += val;
			this.pc += 1;
			return this.x - val;
		} else if (this.program.get(pc).startsWith("addx")) {
			this.busy = true;
			return this.x;
		} else {
			this.pc += 1;
			return this.x;
		}
	}
}