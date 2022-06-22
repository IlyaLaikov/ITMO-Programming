package expression;

import expression.exceptions.NegateException;

import java.util.function.IntUnaryOperator;

public class CheckedNegate extends Negate {
    private static final IntUnaryOperator OPERATOR = (int x) -> {
        if (x == Integer.MIN_VALUE) {
            throw new NegateException("overflow", x);
        }
        return -x;
    };

    public CheckedNegate(CommonExpression first) {
        super(first);
    }

    @Override
    protected IntUnaryOperator getIntOperator() {
        return OPERATOR;
    }
}
