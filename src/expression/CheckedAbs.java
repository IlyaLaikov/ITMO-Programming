package expression;

import expression.exceptions.AbsException;

import java.util.function.IntUnaryOperator;

public class CheckedAbs extends Abs {
    private static final IntUnaryOperator OPERATOR = (int x) -> {
        if (x == Integer.MIN_VALUE) {
            throw new AbsException("overflow", x);
        }
        return x < 0 ? -x : x;
    };

    public CheckedAbs(CommonExpression child) {
        super(child);
    }

    @Override
    protected IntUnaryOperator getIntOperator() {
        return OPERATOR;
    }
}
