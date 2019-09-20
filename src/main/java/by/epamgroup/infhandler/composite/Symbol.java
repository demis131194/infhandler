package by.epamgroup.infhandler.composite;

public class Symbol implements Component {
    private String symbol;

    public Symbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void addComponent(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String collect() {
        return symbol;
    }
}
