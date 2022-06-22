package expression;

import expression.exceptions.LogException;

import java.util.function.IntBinaryOperator;

public class CheckedLog extends Log {
    private static final IntBinaryOperator OPERATOR = (int x, int y) -> {
        if (x <= 0 || y <= 0 || y == 1) {
            throw new LogException("illegal arguments", x, y);
        }

        int result = 0;
        while (x >= y) {
            ++result;
            x /= y;
        }
        return result;
    };

    public CheckedLog(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected IntBinaryOperator getIntOperator() {
        return OPERATOR;
    }
}
