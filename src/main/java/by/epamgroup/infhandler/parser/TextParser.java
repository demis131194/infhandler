package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.Component;
import by.epamgroup.infhandler.composite.Paragraph;
import by.epamgroup.infhandler.composite.Text;

import java.util.stream.Stream;

public class TextParser implements Parser {
    private static final String PARAGRAPH_SPLIT = "(\\t)|([ ]{4})";
    private Parser nextParser = new ParagraphParser();

    @Override
    public Component parse(String component) {
        Text text = new Text();
        component = component.trim();
        String[] paragraphs = component.split(PARAGRAPH_SPLIT);

        for (String str : paragraphs) {
            text.addComponent(nextParser.parse(str));
        }
        return text;
    }
}
