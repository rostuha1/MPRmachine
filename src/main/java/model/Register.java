package model;

public class Register {
    private int index;
    private int value;

    public Register(int index, int value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return index + ", " + value;
    }

    public static Register parse(String test) {
        String[] symbols = test.replaceAll(" ", "").split(",");
        if (symbols.length < 2) return null;

        int register = Integer.parseInt(symbols[0]);
        int value = Integer.parseInt(symbols[1]);

        return new Register(register, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Register register = (Register) o;

        if (index != register.index) return false;
        return value == register.value;
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + value;
        return result;
    }
}
