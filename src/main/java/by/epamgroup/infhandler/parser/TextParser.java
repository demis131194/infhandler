package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.*;

public class TextParser implements ComponentParser {
    private static final String PARAGRAPH_SPLIT = "\\t|[ ]{4}";
    private static ComponentParser nextComponentParser = new ParagraphParser();

    @Override
    public Component parse(String str) {
        Component text = new Composite(TextPart.TEXT);

        str = str.strip();
        String[] paragraphs = str.split(PARAGRAPH_SPLIT);

        for (String paragraph : paragraphs) {
            text.addComponent(nextComponentParser.parse(paragraph));
        }
        return text;
    }
}
