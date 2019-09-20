package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenParser implements Parser {
    private static final String WORD_SYMBOL_REGEX = "(?<word>\\w+)(?<symbol>\\p{Punct}{1,3})?";
    private static final String EXPRESSION_REGEX = "[\\p{Punct}\\d]+";
    private static final Pattern WORD_SYMBOL_PATTERN = Pattern.compile(WORD_SYMBOL_REGEX);
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(EXPRESSION_REGEX);

    @Override
    public Component parse(String component) {
        Matcher matcher;
        Token token = new Token();

        if (component.matches(WORD_SYMBOL_REGEX)) {
            matcher = WORD_SYMBOL_PATTERN.matcher(component);
            if (matcher.find()) {
                String word = matcher.group("word");
                String symbol = matcher.group("symbol");

                token.addComponent(new Word(word));
                if (symbol != null) {
                    token.addComponent(new Symbol(symbol));
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
