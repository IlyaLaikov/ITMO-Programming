package expression;

import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;

public class Log extends AbstractBinaryOperator {
    private static final IntBinaryOperator OPERATOR = (int x, int y) -> {
        int result = 0;
        while (x >= y) {
            ++result;
            x /= y;
        }
        return result;
    };
    private static final BiFunction<BigInteger, BigInteger, BigInteger> BIGINT_OPERATOR = (BigInteger x,
            BigInteger y) -> x.pow(y.intValue());
    private static final String OPERATOR_STRING = "//";
    private static final int PRIORITY = 0;
    private static final boolean IS_SYMMETRICAL = false;

    public Log(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected IntBinaryOperator getIntOperator() {
        return OPERATOR;
    }

    @Override
    protected BiFunction<BigInteger, BigInteger, BigInteger> getBigIntegerOperator() {
        return BIGINT_OPERATOR;
    }

    @Override
    protected String getOperatorString() {
        return OPERATOR_STRING;
    }

    @Override
    protected int getPriority() {
        return PRIORITY;
    }

    @Override
    protected boolean isSymmetrical() {
        return IS_SYMMETRICAL;
    }
}
