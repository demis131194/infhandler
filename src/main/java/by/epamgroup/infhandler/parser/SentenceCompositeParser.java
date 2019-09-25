package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.Component;
import by.epamgroup.infhandler.composite.Composite;
import by.epamgroup.infhandler.composite.TextPart;

public class SentenceCompositeParser implements CompositeParser {
    private static final String TOKEN_SPLIT = "\\s+";
    private CompositeParser nextCompositeParser = new TokenCompositeParser();

    @Override
    public Component parse(String str) {
        Composite sentence = new Composite(TextPart.SENTENCE);
        str = str.strip();

        String[] tokens = str.split(TOKEN_SPLIT);

        for (String token : tokens) {
            sentence.addComponent(nextCompositeParser.parse(token));
        }
        return sentence;
    }
}
