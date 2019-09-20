package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.Component;

import java.util.regex.Pattern;

public class TextParser implements Parser {
    private static final String PARAGRAPH_PATTERN = "(\\t)|([ ]{4})";
    private Parser nextParser = new ParagraphParser();

    @Override
    public Component parse(String component) {
        return null;
    }
}
