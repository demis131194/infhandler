package interpretator;

import by.epamgroup.infhandler.exception.IllegalExpressionException;
import by.epamgroup.infhandler.interpreter.Client;
import by.epamgroup.infhandler.interpreter.Expression;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class InterpreterTest {

    @Test
    public void test1() throws IllegalExpressionException {
        Expression expression = Client.evaluate("13<<2");
        int actualResult = expression.interpret();
        int expectedResult = 52;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test2() throws IllegalExpressionException {
        Expression expression = Client.evaluate("(8>>1)<<1");
        int actualResult = expression.interpret();
        int expectedResult = 8;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test3() throws IllegalExpressionException {
        Expression expression = Client.evaluate("16>>(1<<2)");
        int actualResult = expression.interpret();
        int expectedResult = 1;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test4() throws IllegalExpressionException {
        Expression expression = Client.evaluate("(((1<<1)<<2)<<2)>>1");
        int actualResult = expression.interpret();
        int expectedResult = 16;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test5() throws IllegalExpressionException {
        Expression expression = Client.evaluate("~1");
        int actualResult = expression.interpret();
        int expectedResult = ~1;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test6() throws IllegalExpressionException {
        Expression expression = Client.evaluate("~(~1)<<4");
        int actualResult = expression.interpret();
        int expectedResult = 16;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test7() throws IllegalExpressionException {
        Expression expression = Client.evaluate("7&3");
        int actualResult = expression.interpret();
        int expectedResult = 3;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test8() throws IllegalExpressionException {
        Expression expression = Client.evaluate("~6&9|(3&4)");
        int actualResult = expression.interpret();
        int expectedResult = 9;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test9() throws IllegalExpressionException {
        Expression expression = Client.evaluate("5|(1&2&(3|(4&(1^5|6&47)|3)|(~89&4|(42&7)))|1)");
        int actualResult = expression.interpret();
        int expectedResult = 5;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test10() throws IllegalExpressionException {
        Expression expression = Client.evaluate("(~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78");
        int actualResult = expression.interpret();
        int expectedResult = 78;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test11() throws IllegalExpressionException {
        Expression expression = Client.evaluate("(7^5|1&2<<(2|5>>2&71))|1200");
        int actualResult = expression.interpret();
        int expectedResult = 1202;
        Assert.assertEquals(actualResult, expectedResult);
    }
}
