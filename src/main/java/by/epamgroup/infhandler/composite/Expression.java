package by.epamgroup.infhandler.composite;

public class Expression implements Component {
    private String expression;

    @Override
    public String collect() {
        return expression;
    }
}
