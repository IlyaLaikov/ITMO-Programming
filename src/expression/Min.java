package expression;

import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;

public class Min extends AbstractBinaryOperator {
    private static final String OPERATOR_STRING = "min";
    private static final int PRIORITY = 3;
    private static final boolean IS_SYMMETRICAL = true;

    public Min(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected IntBinaryOperator getIntOperator() {
        return Integer::min;
    }

    @Override
    protected BiFunction<BigInteger, BigInteger, BigInteger> getBigIntegerOperator() {
        return BigInteger::min;
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

    @Override
    protected boolean checkBracketsByPriorityOther(Expression expr) {
        if (expr instanceof AbstractBinaryOperator) {
            AbstractBinaryOperator exprOp = (AbstractBinaryOperator) expr;
            return exprOp instanceof Max || exprOp.getPriority() > getPriority();
        }
        return false;
    }
}
