package aoc;

import java.io.IOException;

import org.junit.Test;
import org.junit.Assert;

import utils.Utils;

public class Day1Test 
{
    @Test
    public void test() throws IOException
    {
        String input = Utils.readInput("/test1.txt");
        
        Assert.assertEquals(24000, Day1.part1(input));
        Assert.assertEquals(45000, Day1.part2(input));
    }
}
