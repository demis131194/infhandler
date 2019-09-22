package by.epamgroup.infhandler.composite;

import by.epamgroup.infhandler.interpreter.Client;

import java.util.List;

public class Expression implements Component {
    private static Client client = new Client();
    private String expression;

    public Expression(String expression) {
        this.expression = expression;
    }

    @Override
    public List<Component> getComponents() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addComponent(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String collect() {
        int result = client.calculate(expression).interpret();
        return String.valueOf(result);
    }
}
