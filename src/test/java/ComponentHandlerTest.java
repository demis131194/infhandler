import by.epamgroup.infhandler.composite.Component;
import by.epamgroup.infhandler.composite.Composite;
import by.epamgroup.infhandler.composite.TextPart;
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
        Component component = ComponentHandler.sortSentenceInParagraphsByWordCount(componentText);
        String actualText = component.collect();
        String expectedText = "\tBo. Qqqw, eeewqwer! Some text, text-text.\n\tTexttttttt. Second paragraph?\n\tThis is 3 paragraph...";
        Assert.assertEquals(actualText, expectedText);
    }

    @Test
    public void sortWordsAtSentencesByLengthText() throws ComponentHandlerException {
        Component component = ComponentHandler.sortWordsInSentencesByLength(componentText);
        String actualText = component.collect();
        String expectedText = "\tSome text, text-text. Qqqw, eeewqwer! Bo.\n\tSecond paragraph? Texttttttt.\n\t3 is This paragraph...";
        Assert.assertEquals(actualText, expectedText);
    }

    @Test(expectedExceptions = ComponentHandlerException.class)
    public void exceptionTest1() throws ComponentHandlerException {
        ComponentHandler.sortParagraphsBySentencesCount(new Composite(TextPart.PARAGRAPH));
    }

    @Test(expectedExceptions = ComponentHandlerException.class)
    public void exceptionTest2() throws ComponentHandlerException {
        ComponentHandler.sortSentenceInParagraphsByWordCount(new Composite(TextPart.SENTENCE));
    }

    @Test(expectedExceptions = ComponentHandlerException.class)
    public void exceptionTest3() throws ComponentHandlerException {
        ComponentHandler.sortWordsInSentencesByLength(new Composite(TextPart.TOKEN));
    }
}
