package aoc;

import java.io.IOException;

import org.junit.Test;
import org.junit.Assert;

import utils.Utils;

public class Day3Test 
{
    @Test
    public void test() throws IOException
    {
        String input = Utils.readInput("/test3.txt");
        
        Assert.assertEquals(157, Day3.part1(input));
        Assert.assertEquals(70, Day3.part2(input));
    }
}
