package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.Component;
import by.epamgroup.infhandler.composite.Sentence;

public class SentenceParser implements Parser {
    private static final String TOKEN_SPLIT = "\\s+";
    private Parser nextParser = new TokenParser();

    @Override
    public Component parse(String component) {
        Sentence sentence = new Sentence();
        component = component.trim();

        String[] tokens = component.split(TOKEN_SPLIT);

        for (String str : tokens) {
            sentence.addComponent(nextParser.parse(str));
        }
        return sentence;
    }
}
