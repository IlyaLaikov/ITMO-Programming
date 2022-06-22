package expression;

import expression.exceptions.DivideException;

import java.util.function.IntBinaryOperator;

public class CheckedDivide extends Divide {
    private static final IntBinaryOperator OPERATOR = (int x, int y) -> {
        if (y == 0) {
            throw new DivideException("divide by zero", x, y);
        } else if (x == Integer.MIN_VALUE && y == -1) {
            throw new DivideException("overflow", x, y);
        }
        return x / y;
    };

    public CheckedDivide(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected IntBinaryOperator getIntOperator() {
        return OPERATOR;
    }
}
