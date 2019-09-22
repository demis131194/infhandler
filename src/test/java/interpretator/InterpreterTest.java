package interpretator;

import by.epamgroup.infhandler.interpreter.Client;
import by.epamgroup.infhandler.interpreter.Expression;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class InterpreterTest {
    private Client client = new Client();

    @Test
    public void test1() {
        Expression expression = client.calculate("13<<2");
        int actualResult = expression.interpret();
        int expectedResult = 52;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test2() {
        Expression expression = client.calculate("8>>1");
        int actualResult = expression.interpret();
        int expectedResult = 4;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test3() {
        Expression expression = client.calculate("8<<1<<1");
        int actualResult = expression.interpret();
        int expectedResult = 32;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test4() {
        Expression expression = client.calculate("8>>1>>1");
        int actualResult = expression.interpret();
        int expectedResult = 2;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test5() {
        Expression expression = client.calculate("(8>>1)<<1");
        int actualResult = expression.interpret();
        int expectedResult = 8;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test6() {
        Expression expression = client.calculate("16>>(1<<2)");
        int actualResult = expression.interpret();
        int expectedResult = 1;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test7() {
        Expression expression = client.calculate("(4>>1)<<(1<<2)");
        int actualResult = expression.interpret();
        int expectedResult = 32;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test8() {
        Expression expression = client.calculate("(((1<<1)<<2)<<2)>>1");
        int actualResult = expression.interpret();
        int expectedResult = 16;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test9() {
        Expression expression = client.calculate("~1");
        int actualResult = expression.interpret();
        int expectedResult = ~1;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test10() {
        Expression expression = client.calculate("~(~1)<<4");
        int actualResult = expression.interpret();
        int expectedResult = 16;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test11() {
        Expression expression = client.calculate("1&1");
        int actualResult = expression.interpret();
        int expectedResult = 1;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test12() {
        Expression expression = client.calculate("7&3");
        int actualResult = expression.interpret();
        int expectedResult = 3;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test13() {
        Expression expression = client.calculate("~6&9|(3&4)");
        int actualResult = expression.interpret();
        int expectedResult = 9;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test14() {
        Expression expression = client.calculate("~6&9|(3&4)");
        int actualResult = expression.interpret();
        int expectedResult = 9;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test15() {
        Expression expression = client.calculate("5|(1&2&(3|(4&(1^5|6&47)|3)|(~89&4|(42&7)))|1)");
        int actualResult = expression.interpret();
        int expectedResult = 5;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test16() {
        Expression expression = client.calculate("(~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78");
        int actualResult = expression.interpret();
        int expectedResult = 78;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test17() {
        Expression expression = client.calculate("(7^5|1&2<<(2|5>>2&71))|1200");
        int actualResult = expression.interpret();
        int expectedResult = 1202;
        Assert.assertEquals(actualResult, expectedResult);
    }
}
