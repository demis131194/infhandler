package by.epamgroup.infhandler.util;

import by.epamgroup.infhandler.exception.TextReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TextReader {
    private static Logger logger = LogManager.getLogger();
    private static final String PATH_TO_TEXT = "src/main/resources/text/text.txt";

    private TextReader() {
    }

    public static String readTextFromFile() throws TextReaderException {
        logger.trace("Enter in " + TextReader.class.getSimpleName());
        List<String> strings;
        try  {
            strings = Files.readAllLines(Paths.get(PATH_TO_TEXT), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TextReaderException("Some Exception", e);
        }
        logger.trace("Exit from " + TextReader.class.getSimpleName());
        return strings.stream().reduce("", (s, s2) -> s + "\n" + s2);
    }
}
