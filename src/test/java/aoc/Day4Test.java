package aoc;

import java.io.IOException;

import org.junit.Test;
import org.junit.Assert;

import utils.Utils;

public class Day4Test 
{
    @Test
    public void test() throws IOException
    {
        String input = Utils.readInput("/test4.txt");
        
        Assert.assertEquals(2, Day4.part1(input));
        Assert.assertEquals(4, Day4.part2(input));
    }
}
