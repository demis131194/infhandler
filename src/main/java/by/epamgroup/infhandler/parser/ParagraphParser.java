package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.Component;
import by.epamgroup.infhandler.composite.ComponentImpl;
import by.epamgroup.infhandler.composite.TextPart;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser implements Parser {
    private static final String SENTENCE_REGEX = "\\p{Alnum}.+?(!|\\?|\\.{3}|\\.)";
    private static Pattern pattern = Pattern.compile(SENTENCE_REGEX);
    private Parser nextParser = new SentenceParser();

    @Override
    public Component parse(String str) {
        Matcher matcher = pattern.matcher(str);
        ComponentImpl paragraph = new ComponentImpl(TextPart.PARAGRAPH);

        while (matcher.find()) {
            String comp = matcher.group();
            paragraph.addComponent(nextParser.parse(comp));
        }
        return paragraph;
    }
}
