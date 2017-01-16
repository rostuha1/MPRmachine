package model.rule;

public enum RuleType {
    J, T, S, Z;

    public boolean checkValues(int valuesNumber) {
        return this == J && valuesNumber == 3 || this == T && valuesNumber == 2 || this == S && valuesNumber == 1 || this == Z && valuesNumber == 1;
    }

    public static RuleType getType(String type) {
        switch (type.charAt(0)) {
            case 'J': return J;
            case 'T': return T;
            case 'S': return S;
            case 'Z': return Z;
            default: return null;
        }
    }

}
