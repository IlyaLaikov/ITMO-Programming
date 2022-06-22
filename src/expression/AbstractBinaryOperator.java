package expression;

import java.math.BigInteger;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;

public abstract class AbstractBinaryOperator implements CommonExpression {
    protected final CommonExpression first;
    protected final CommonExpression second;

    private final int hashCode;
    private String toString;
    private String toMiniString;

    public AbstractBinaryOperator(final CommonExpression first, final CommonExpression second) {
        this.first = first;
        this.second = second;
        this.hashCode = Objects.hash(first, second, getClass());
    }

    protected abstract IntBinaryOperator getIntOperator();

    protected abstract BiFunction<BigInteger, BigInteger, BigInteger> getBigIntegerOperator();

    protected abstract String getOperatorString();

    protected abstract int getPriority();

    protected abstract boolean isSymmetrical();

    @Override
    public int evaluate(final int var) {
        return getIntOperator().applyAsInt(
                first.evaluate(var),
                second.evaluate(var));
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return getIntOperator().applyAsInt(
                first.evaluate(x, y, z),
                second.evaluate(x, y, z));
    }

    @Override
    public BigInteger evaluate(final BigInteger var) {
        return getBigIntegerOperator().apply(
                first.evaluate(var),
                second.evaluate(var));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractBinaryOperator) {
            AbstractBinaryOperator op = (AbstractBinaryOperator) obj;
            return first.equals(op.first) && second.equals(op.second) && op.getClass().equals(getClass());
        }
        return false;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = "(" + first + " " + getOperatorString() + " " + second + ")";
        }
        return toString;
    }

    @Override
    public String toMiniString() {
        if (toMiniString == null) {
            toMiniString = operandWrapper(first, checkBracketsByPriority(first))
                    + " " + getOperatorString() + " "
                    + operandWrapper(second, checkSecondByAsymmetrical(second));
        }
        return toMiniString;
    }

    protected boolean checkBracketsByPriority(Expression expr) {
        if (expr instanceof AbstractBinaryOperator) {
            AbstractBinaryOperator exprOp = (AbstractBinaryOperator) expr;
            return exprOp.getPriority() > getPriority();
        }
        return false;
    }

    protected boolean checkBracketsByPriorityOther(Expression expr) {
        return checkBracketsByPriority(expr);
    }

    protected boolean checkSecondByAsymmetrical(Expression expr) {
        if (expr instanceof AbstractBinaryOperator) {
            AbstractBinaryOperator exprOp = (AbstractBinaryOperator) expr;
            if (!exprOp.isSymmetrical() && exprOp.getPriority() >= getPriority()) {
                return true;
            } else if (!isSymmetrical() && exprOp.getPriority() >= getPriority()) {
                return true;
            }
        }
        return checkBracketsByPriorityOther(expr);
    }

    private String operandWrapper(Expression expr, boolean inBrackets) {
        return (inBrackets ? "(" : "") + expr.toMiniString() + (inBrackets ? ")" : "");
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
