package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.*;

public class TextCompositeParser implements CompositeParser {
    private static final String PARAGRAPH_SPLIT = "(\\t)|([ ]{4})";
    private CompositeParser nextCompositeParser = new ParagraphCompositeParser();

    @Override
    public Component parse(String str) {
        Composite text = new Composite(TextPart.TEXT);
        str = str.trim();
        String[] paragraphs = str.split(PARAGRAPH_SPLIT);

        for (String paragraph : paragraphs) {
            text.addComponent(nextCompositeParser.parse(paragraph));
        }
        return text;
    }
}
