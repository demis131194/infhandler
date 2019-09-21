package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenParser implements Parser {
    private static final String WORD_SYMBOL_MATCHER = "\\(?([\\w-']+)?\\)?(\\p{Punct}|\\.{3})?";
    private static final String WORD_SYMBOL_REGEX = "(\\w+)|\\p{Punct}|\\.{3}";
    private static final String EXPRESSION_REGEX = "([\\p{Punct}\\d]{3,})";
    private static final Pattern WORD_SYMBOL_PATTERN = Pattern.compile(WORD_SYMBOL_REGEX);
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(EXPRESSION_REGEX);

    @Override
    public Component parse(String component) {
        Matcher matcher;
        Token token = new Token();

        if (component.matches(WORD_SYMBOL_MATCHER)) {
            matcher = WORD_SYMBOL_PATTERN.matcher(component);
            while (matcher.find()) {
                String str = matcher.group();

                if (str.matches("\\w+")) {
                    token.addComponent(new Word(str));
                } else if (str.matches("\\p{Punct}{1,3}")) {
                    token.addComponent(new Symbol(str));
                }
            }
        } else if (component.matches(EXPRESSION_REGEX)) {
            matcher = EXPRESSION_PATTERN.matcher(component);
            if (matcher.find()) {
                String expression = matcher.group();
                token.addComponent(new Expression(expression));
            }
        } else {
            throw new IllegalArgumentException();
        }

        return token;
    }
}
