package aoc;

import java.io.IOException;

import org.junit.Test;
import org.junit.Assert;

import utils.Utils;

public class Day2Test 
{
    @Test
    public void test() throws IOException
    {
        String input = Utils.readInput("/test2.txt");
        
        Assert.assertEquals(15, Day2.part1(input));
        Assert.assertEquals(12, Day2.part2(input));
    }
}
