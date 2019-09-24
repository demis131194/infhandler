package by.epamgroup.infhandler.composite;

import by.epamgroup.infhandler.exception.IllegalExpressionException;
import by.epamgroup.infhandler.interpreter.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Expression implements Component {
    private static Logger logger = LogManager.getLogger();
    private String expression;

    public Expression(String expression) {
        this.expression = expression;
    }

    @Override
    public TextPart getTextPart() {
        throw new UnsupportedOperationException();
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
        String result;
        try {
            int number = Client.evaluate(expression).interpret();
            result = String.valueOf(number);
        } catch (IllegalExpressionException e) {
            logger.warn(e);
            result = expression;
        }
        return result;
    }
}
