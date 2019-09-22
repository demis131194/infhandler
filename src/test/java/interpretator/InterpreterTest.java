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
        Expression expression = client.evaluate("13<<2");
        int actualResult = expression.interpret();
        int expectedResult = 52;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test2() {
        Expression expression = client.evaluate("8>>1");
        int actualResult = expression.interpret();
        int expectedResult = 4;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test3() {
        Expression expression = client.evaluate("8<<1<<1");
        int actualResult = expression.interpret();
        int expectedResult = 32;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test4() {
        Expression expression = client.evaluate("8>>1>>1");
        int actualResult = expression.interpret();
        int expectedResult = 2;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test5() {
        Expression expression = client.evaluate("(8>>1)<<1");
        int actualResult = expression.interpret();
        int expectedResult = 8;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test6() {
        Expression expression = client.evaluate("16>>(1<<2)");
        int actualResult = expression.interpret();
        int expectedResult = 1;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test7() {
        Expression expression = client.evaluate("(4>>1)<<(1<<2)");
        int actualResult = expression.interpret();
        int expectedResult = 32;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test8() {
        Expression expression = client.evaluate("(((1<<1)<<2)<<2)>>1");
        int actualResult = expression.interpret();
        int expectedResult = 16;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test9() {
        Expression expression = client.evaluate("~1");
        int actualResult = expression.interpret();
        int expectedResult = ~1;
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test10() {
        Expression expression = client.evaluate("~(~1)<<4");
        int actualResult = expression.interpret();
        int expectedResult = 16;
        Assert.assertEquals(actualResult, expectedResult);
    }
}
