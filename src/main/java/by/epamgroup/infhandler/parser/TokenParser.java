package by.epamgroup.infhandler.parser;

import by.epamgroup.infhandler.composite.Component;

public class TokenParser implements Parser {
    private Parser wordParser = new WordParser();
    private Parser SymbolParser = new SymbolParser();
    private Parser ExpressionParser = new ExpressionParser();

    @Override
    public Component parse(String component) {
        return null;
    }
}
