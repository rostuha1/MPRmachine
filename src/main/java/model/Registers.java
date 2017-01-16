package model;

import java.util.HashMap;
import java.util.Map;

public class Registers {

    private Map<Integer, Integer> registers = new HashMap<>();

    public Registers() {}

    public int getValue(int register) {

        Integer res = registers.get(register);

        if (res == null) {
            registers.put(register, 0);
            return 0;
        }

        return res;
    }

    public void setValue(int register, int value) {
        registers.put(register, value);
    }

}
