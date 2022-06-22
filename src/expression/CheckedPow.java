package expression;

import expression.exceptions.PowException;

import java.util.function.IntBinaryOperator;

public class CheckedPow extends Pow {
    private static boolean checkMultiply(int x, int y) {
        return x != 0 && y != 0 && ((x * y) / x != y || (x * y) / y != x);
    }

    private static final IntBinaryOperator OPERATOR = (int x, int y) -> {
        if (x == 0 && y == 0 || y < 0) {
            throw new PowException("illegal arguments", x, y);
        }
        int saveX = x, saveY = y;
        int result = 1;
        while (y > 0) {
            if (y % 2 == 1) {
                if (checkMultiply(result, x)) {
                    throw new PowException("overflow", saveX, saveY);
                }
                result *= x;
            }
            if (y != 1) {
                if (checkMultiply(x, x)) {
                    throw new PowException("overflow", saveX, saveY);
                }
            }
            x *= x;
            y = (y >> 1);
        }
        return result;
    };

    public CheckedPow(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected IntBinaryOperator getIntOperator() {
        return OPERATOR;
    }
}
