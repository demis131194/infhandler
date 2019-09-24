package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.Component;
import by.epamgroup.infhandler.composite.ComponentImpl;
import by.epamgroup.infhandler.composite.TextPart;

public class SentenceParser implements Parser {
    private static final String TOKEN_SPLIT = "\\s+";
    private Parser nextParser = new TokenParser();

    @Override
    public Component parse(String str) {
        ComponentImpl sentence = new ComponentImpl(TextPart.SENTENCE);
        str = str.strip();

        String[] tokens = str.split(TOKEN_SPLIT);

        for (String token : tokens) {
            sentence.addComponent(nextParser.parse(token));
        }
        return sentence;
    }
}
