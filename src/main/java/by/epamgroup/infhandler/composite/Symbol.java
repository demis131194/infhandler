package by.epamgroup.infhandler.composite;

import java.util.List;

public class Symbol implements Component {
    private TextPart textPart = TextPart.SYMBOL;
    private String symbol;

    public Symbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public TextPart getTextPart() {
        return textPart;
    }

    @Override
    public void addComponent(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Component> getComponents() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String collect() {
        return symbol;
    }
}
