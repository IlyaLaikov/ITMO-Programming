package expression;

import expression.exceptions.SubtractException;

import java.util.function.IntBinaryOperator;

public class CheckedSubtract extends Subtract {
    private static final IntBinaryOperator OPERATOR = (int x, int y) -> {
        if (y > 0 && Integer.MIN_VALUE + y > x || y < 0 && Integer.MAX_VALUE + y < x) {
            throw new SubtractException("overflow", x, y);
        }
        return x - y;
    };

    public CheckedSubtract(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected IntBinaryOperator getIntOperator() {
        return OPERATOR;
    }
}
