import by.epamgroup.infhandler.composite.Component;
import by.epamgroup.infhandler.exception.ComponentHandlerException;
import by.epamgroup.infhandler.exception.TextReaderException;
import by.epamgroup.infhandler.handler.ComponentHandler;
import by.epamgroup.infhandler.parser.TextParser;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.TextReaderTest;

@Test
public class ComponentHandlerTest {
    private Component componentText;

    @BeforeMethod
    public void initText() throws TextReaderException {
        componentText = new TextParser().parse(TextReaderTest.readTextFromFile());
    }

    @Test
    public void sortParagraphsBySentencesCountTest() throws ComponentHandlerException {
        Component component = ComponentHandler.sortParagraphsBySentencesCount(componentText);
        String actualText = component.collect();
        String expectedText = "\tThis is 3 paragraph...\n\tSecond paragraph? Texttttttt.\n\tSome text, text-text. Qqqw, eeewqwer! Bo.";
        Assert.assertEquals(actualText, expectedText);
    }

    @Test
    public void sortSentenceAtParagraphsByWordCountText() throws ComponentHandlerException {
        Component component = ComponentHandler.sortSentenceAtParagraphsByWordCount(componentText);
        String actualText = component.collect();
        String expectedText = "\tBo. Qqqw, eeewqwer! Some text, text-text.\n\tTexttttttt. Second paragraph?\n\tThis is 3 paragraph...";
        Assert.assertEquals(actualText, expectedText);
    }

    @Test
    public void sortWordsAtSentencesByLengthText() throws ComponentHandlerException {
        Component component = ComponentHandler.sortWordsAtSentencesByLength(componentText);
        String actualText = component.collect();
        String expectedText = "\tSome text, text-text. Qqqw, eeewqwer! Bo.\n\tSecond paragraph? Texttttttt.\n\tis This 3 paragraph...";
        Assert.assertEquals(actualText, expectedText);
    }
}
