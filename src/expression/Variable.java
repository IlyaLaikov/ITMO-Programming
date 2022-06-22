package expression;

import java.math.BigInteger;

public class Variable implements CommonExpression {
    private final String name;
    private final int hashCode;

    public Variable(String name) {
        this.name = name;
        this.hashCode = name.hashCode();
    }

    @Override
    public int evaluate(int var) {
        return var;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new IllegalArgumentException("Too low arguments");
        }
    }

    @Override
    public BigInteger evaluate(BigInteger var) {
        return var;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            Variable variable = (Variable) obj;
            return name.equals(variable.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toMiniString() {
        return name;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
