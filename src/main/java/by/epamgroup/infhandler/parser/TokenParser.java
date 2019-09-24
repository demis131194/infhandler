package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenParser implements Parser {
    private static final String WORD_SYMBOL_MATCHER = "\\(?([\\w-']+)?\\)?(\\p{Punct}|\\.{3})?";
    private static final String WORD_SYMBOL_REGEX = "([\\w-]+)|\\p{Punct}|\\.{3}";
    private static final String EXPRESSION_REGEX = "([\\p{Punct}\\d]{3,})";
    private static final String WORD_REGEX = "\\w([\\w-]+)?";
    private static final String SYMBOL_REGEX = "\\p{Punct}{1,3}";
    private static final Pattern WORD_SYMBOL_PATTERN = Pattern.compile(WORD_SYMBOL_REGEX);
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(EXPRESSION_REGEX);


    @Override
    public Component parse(String str) {
        Matcher matcher;
        ComponentImpl token = new ComponentImpl(TextPart.TOKEN);

        if (str.matches(WORD_SYMBOL_MATCHER)) {
            matcher = WORD_SYMBOL_PATTERN.matcher(str);

            while (matcher.find()) {
                String wordOrSymbol = matcher.group();
                if (wordOrSymbol.matches(WORD_REGEX)) {
                    token.addComponent(new Word(wordOrSymbol));
                } else if (wordOrSymbol.matches(SYMBOL_REGEX)) {
                    token.addComponent(new Symbol(wordOrSymbol));
                }
            }

        } else if (str.matches(EXPRESSION_REGEX)) {
            matcher = EXPRESSION_PATTERN.matcher(str);
            if (matcher.find()) {
                String expression = matcher.group();
                token.addComponent(new Expression(expression));
            }
        }

        return token;
    }
}
