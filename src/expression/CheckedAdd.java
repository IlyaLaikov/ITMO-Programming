package expression;

import expression.exceptions.AddException;

import java.util.function.IntBinaryOperator;


public class CheckedAdd extends Add {
    private static final IntBinaryOperator OPERATOR = (int x, int y) -> {
        if (y > 0 && Integer.MAX_VALUE - y < x || y < 0 && Integer.MIN_VALUE - y > x) {
            throw new AddException("overflow", x, y);
        }
        return x + y;
    };

    public CheckedAdd(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected IntBinaryOperator getIntOperator() {
        return OPERATOR;
    }
}
