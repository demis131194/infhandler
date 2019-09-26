package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.*;
import by.epamgroup.infhandler.exception.IllegalExpressionException;
import by.epamgroup.infhandler.interpreter.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenParser implements ComponentParser {
    private static Logger logger = LogManager.getLogger();

    private static final String WORD_REGEX = "\\w([\\w-']+)?";
    private static final String SYMBOL_REGEX = "\\p{Punct}|\\.{3}";
    private static final String WORD_OR_SYMBOL_REGEX = String.format("(%s)|%s", WORD_REGEX, SYMBOL_REGEX);
    private static final String WORD_AND_SYMBOL_REGEX = "\\(?([\\w-']+)?\\)?(\\p{Punct}|\\.{3})?";
    private static final String EXPRESSION_REGEX = "([\\p{Punct}\\d]{3,})";
    private static final Pattern WORD_SYMBOL_PATTERN = Pattern.compile(WORD_OR_SYMBOL_REGEX);
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(EXPRESSION_REGEX);

    @Override
    public Component parse(String str) {
        Component token = new Composite(TextPart.TOKEN);

        if (str.matches(WORD_AND_SYMBOL_REGEX)) {
            Matcher matcher = WORD_SYMBOL_PATTERN.matcher(str);

            while (matcher.find()) {
                String wordOrSymbol = matcher.group();

                if (wordOrSymbol.matches(WORD_REGEX)) {
                    token.addComponent(new Word(wordOrSymbol));
                } else if (wordOrSymbol.matches(SYMBOL_REGEX)) {
                    token.addComponent(new Symbol(wordOrSymbol));
                }
            }

        } else if (str.matches(EXPRESSION_REGEX)) {
            Matcher matcher = EXPRESSION_PATTERN.matcher(str);

            if (matcher.find()) {
                String expression = matcher.group();
                expression = calculateExpression(expression);
                token.addComponent(new Word(expression));
            }
        }
        return token;
    }

    private String calculateExpression(String expression) {
        try {
            int number = Client.evaluate(expression).interpret();
            expression = String.valueOf(number);
        } catch (IllegalExpressionException e) {
            logger.warn(e);
        }
        return expression;
    }
}
