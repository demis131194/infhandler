package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.Component;

public class SentenceParser implements Parser {
    private Parser nextParser = new TokenParser();

    @Override
    public Component parse(String component) {
        return null;
    }
}
