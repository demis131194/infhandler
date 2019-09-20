package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.Component;

public class ParagraphParser implements Parser {
    private Parser nextParser = new SentenceParser();

    @Override
    public Component parse(String component) {
        return null;
    }
}
