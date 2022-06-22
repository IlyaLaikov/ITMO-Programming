package expression;

import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;

public class Add extends AbstractBinaryOperator {
    private static final String OPERATOR_STRING = "+";
    private static final int PRIORITY = 2;
    private static final boolean IS_SYMMETRICAL = true;

    public Add(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected IntBinaryOperator getIntOperator() {
        return Integer::sum;
    }

    @Override
    protected BiFunction<BigInteger, BigInteger, BigInteger> getBigIntegerOperator() {
        return BigInteger::add;
    }

    @Override
    protected boolean checkSecondByAsymmetrical(Expression expr) {
        if (expr instanceof AbstractBinaryOperator) {
            AbstractBinaryOperator exprOp = (AbstractBinaryOperator) expr;
            if (!exprOp.isSymmetrical() && exprOp.getPriority() > getPriority()) {
                return true;
            } else if (exprOp instanceof Min || exprOp instanceof Max) {
                return true;
            }
            return (!isSymmetrical()) && (exprOp.getPriority() >= getPriority());
        }
        return false;
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
