package by.epamgroup.infhandler.composite;

import java.util.List;

public class Expression implements Component {
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
        return expression;
    }
}
