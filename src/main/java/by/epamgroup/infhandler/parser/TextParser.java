package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.*;

public class TextParser implements Parser {
    private static final String PARAGRAPH_SPLIT = "(\\t)|([ ]{4})";
    private Parser nextParser = new ParagraphParser();

    @Override
    public Component parse(String str) {
        ComponentImpl text = new ComponentImpl(TextPart.TEXT);
        str = str.trim();
        String[] paragraphs = str.split(PARAGRAPH_SPLIT);

        for (String paragraph : paragraphs) {
            text.addComponent(nextParser.parse(paragraph));
        }
        return text;
    }
}
