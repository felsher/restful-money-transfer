package my.restful.homework.moneytransfer.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class ExcternalStringUtilsTest {

    @Test
    public void testProcess() throws Exception {
        String s = "ABBCCC";

        Map<Character, Integer> result = ExcternalStringUtils.process(s);

        Integer a = result.get('A');
        Integer b = result.get('B');
        Integer c = result.get('C');

        Assert.assertNotNull(a);
        Assert.assertNotNull(b);
        Assert.assertNotNull(c);

        Assert.assertTrue(1 == a);
        Assert.assertTrue(2 == b);
        Assert.assertTrue(3 == c);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessByNull() throws Exception {
        ExcternalStringUtils.process(null);
    }

}