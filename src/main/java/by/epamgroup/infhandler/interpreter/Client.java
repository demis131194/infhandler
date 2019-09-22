package by.epamgroup.infhandler.interpreter;

import by.epamgroup.infhandler.util.Cursor;

public class Client {

    public Expression calculate(String expression) {
        expression = expression.replaceAll(">>", ">");
        expression = expression.replaceAll("<<", "<");
        return evaluate(expression);
    }

    private Expression evaluate(String expression) {

        while (!expression.matches("-?[\\d]+")) {

            while (expression.contains(Operation.NOT.getOperation())) {
                expression = executeOperation(expression, Operation.NOT);
            }
            while (expression.contains(Operation.RIGHT_SHIFT.getOperation())) {
                expression = executeOperation(expression, Operation.RIGHT_SHIFT);
            }
            while (expression.contains(Operation.LEFT_SHIFT.getOperation())) {
                expression = executeOperation(expression, Operation.LEFT_SHIFT);
            }
            while (expression.contains(Operation.AND.getOperation())) {
                expression = executeOperation(expression, Operation.AND);
            }
            while (expression.contains(Operation.XOR.getOperation())) {
                expression = executeOperation(expression, Operation.XOR);
            }
            while (expression.contains(Operation.OR.getOperation())) {
                expression = executeOperation(expression, Operation.OR);
            }

        }

        return new NumberExpression(Integer.parseInt(expression));
    }

    private String executeOperation(String expression, Operation operation) {
        int operatorCursor = expression.indexOf(operation.getOperation());
        Cursor cursor;
        Expression leftExpression;
        Expression rightExpression;

        if (operation == Operation.NOT) {
            cursor = new Cursor(operatorCursor, operatorCursor + 1, operatorCursor);
            leftExpression = null;
        } else {
            cursor = new Cursor(operatorCursor - 1, operatorCursor + 1, operatorCursor);
            leftExpression = getLeftExpression(expression, cursor);
        }

        rightExpression = getRightExpression(expression, cursor);

        Expression resultExpression = null;

        switch (operation) {
            case NOT:
                 resultExpression = new NotExpression(rightExpression);
                break;
            case LEFT_SHIFT:
                resultExpression = new LeftBitShiftExpression(leftExpression, rightExpression);
                break;
            case RIGHT_SHIFT:
                resultExpression = new RightBitShiftExpression(leftExpression, rightExpression);
                break;
            case AND:
                resultExpression = new AndExpression(leftExpression, rightExpression);
                break;
            case XOR:
                resultExpression = new XorExpression(leftExpression, rightExpression);
                break;
            case OR:
                resultExpression = new OrExpression(leftExpression, rightExpression);
                break;
        }

        int result = resultExpression.interpret();
        String replacedPartExpression = expression.substring(cursor.getLeftCursor(), cursor.getRightCursor());
        return expression.replace(replacedPartExpression, String.valueOf(result));
    }

    private Expression getRightExpression(String expression, Cursor cursor) {
        int rightCursor = cursor.getRightCursor();
        int operatorCursor = cursor.getOperatorCursor();

        char rightChar = expression.charAt(rightCursor);

        if (Character.isDigit(rightChar)) {

            try {
                while (Character.isDigit(rightChar) || rightChar == '-') {
                    rightChar = expression.charAt(++rightCursor);
                }

                cursor.setRightCursor(rightCursor);
                rightCursor--;
            } catch (Exception e) {
                rightCursor--;
                cursor.setRightCursor(rightCursor + 1);
            }

            int number = Integer.parseInt(expression.substring(operatorCursor + 1, rightCursor + 1));
            return new NumberExpression(number);

        } else if (rightChar == '(') {
            int breaksCount = 1;

            while (breaksCount > 0) {
                rightChar = expression.charAt(++rightCursor);

                if (rightChar == ')') {
                    breaksCount--;
                } else if (rightChar == '(') {
                    breaksCount++;
                }

            }
            cursor.setRightCursor(rightCursor + 1);
            String newExpression = expression.substring(operatorCursor + 2, rightCursor);
            return evaluate(newExpression);
        }

        throw new IllegalArgumentException("Wrong expression!");
    }

    private Expression getLeftExpression(String expression, Cursor cursor) {
        int leftCursor = cursor.getLeftCursor();
        int operatorCursor = cursor.getOperatorCursor();

        char leftChar = expression.charAt(leftCursor);

        if (Character.isDigit(leftChar)) {

            try {
                while (Character.isDigit(leftChar) || leftChar == '-') {
                    leftChar = expression.charAt(--leftCursor);
                }

                cursor.setLeftCursor(leftCursor + 1);
                leftCursor++;

            } catch (Exception e) {
                leftCursor++;
                cursor.setLeftCursor(leftCursor);
            }

            int number = Integer.parseInt(expression.substring(leftCursor, operatorCursor));
            return new NumberExpression(number);

        } else if (leftChar == ')') {
            int breaksCount = 1;

            while (breaksCount > 0) {
                leftChar = expression.charAt(--leftCursor);

                if (leftChar == ')') {
                    breaksCount++;
                } else if (leftChar == '(') {
                    breaksCount--;
                }

            }
            cursor.setLeftCursor(leftCursor);
            leftCursor++;
            String newExpression = expression.substring(leftCursor, operatorCursor - 1);
            return evaluate(newExpression);
        }

        throw new IllegalArgumentException("Wrong expression!");
    }
}
