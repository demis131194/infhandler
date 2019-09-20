package by.epamgroup.infhandler.composite;

public class Symbol implements Component {
    private String symbol;

    @Override
    public String collect() {
        return symbol;
    }
}
