package util;

import by.epamgroup.infhandler.exception.TextReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TextReaderTest {
    private static Logger logger = LogManager.getLogger();
    private static final String PATH_TO_TEXT = "src/test/resources/text/text.txt";

    private TextReaderTest() {
    }

    public static String readTextFromFile() throws TextReaderException {
        logger.trace("Enter in readTextFromFile.");
        List<String> strings;
        try  {
            strings = Files.readAllLines(Paths.get(PATH_TO_TEXT), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TextReaderException(e);
        }
        logger.trace("Exit from readTextFromFile.");
        String text = strings.stream().reduce("", (s, s2) -> s + "\n" + s2);
        return text.replaceAll("\\n", " ");
    }
}
