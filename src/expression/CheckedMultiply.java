package expression;

import expression.exceptions.MultiplyException;

import java.util.function.IntBinaryOperator;

public class CheckedMultiply extends Multiply {
    private static final IntBinaryOperator OPERATOR = (int x, int y) -> {
        if (x != 0 && y != 0 && ((x * y) / x != y || (x * y) / y != x)) {
            throw new MultiplyException("overflow", x, y);
        }
        return x * y;
    };

    public CheckedMultiply(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected IntBinaryOperator getIntOperator() {
        return OPERATOR;
    }
}
