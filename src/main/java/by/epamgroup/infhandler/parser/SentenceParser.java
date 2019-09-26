package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.Component;
import by.epamgroup.infhandler.composite.Composite;
import by.epamgroup.infhandler.composite.TextPart;

public class SentenceParser implements ComponentParser {
    private static final String TOKEN_SPLIT = "\\s+";
    private static ComponentParser nextComponentParser = new TokenParser();

    @Override
    public Component parse(String str) {
        Component sentence = new Composite(TextPart.SENTENCE);
        str = str.strip();

        String[] tokens = str.split(TOKEN_SPLIT);

        for (String token : tokens) {
            sentence.addComponent(nextComponentParser.parse(token));
        }
        return sentence;
    }
}
