import by.epamgroup.infhandler.composite.Component;
import by.epamgroup.infhandler.exception.TextReaderException;
import by.epamgroup.infhandler.parser.TextCompositeParser;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.TextReaderTest;

@Test
public class ComponentParserTextTest {

    public void test() throws TextReaderException {
        String textStr = TextReaderTest.readTextFromFile();
        Component componentText = new TextCompositeParser().parse(textStr);
        String actualText = componentText.collect();
        String expectedText = "\tSome text, text-text. Qqqw, eeewqwer! Bo.\n\tSecond paragraph? Texttttttt.\n\tThis is 3 paragraph...";
        Assert.assertEquals(actualText, expectedText);
    }
}
