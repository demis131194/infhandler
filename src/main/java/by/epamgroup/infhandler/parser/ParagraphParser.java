package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.Component;
import by.epamgroup.infhandler.composite.Paragraph;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser implements Parser {
    private static final String SENTENCE_REGEX = "\\p{Alnum}.+?(!|\\?|\\.{3}|\\.)";
    private static Pattern pattern = Pattern.compile(SENTENCE_REGEX);
    private Parser nextParser = new SentenceParser();

    @Override
    public Component parse(String component) {
        Matcher matcher = pattern.matcher(component);
        Paragraph paragraph = new Paragraph();

        while (matcher.find()) {
            String comp = matcher.group();
            paragraph.addComponent(nextParser.parse(comp));
        }
        return paragraph;
    }
}
