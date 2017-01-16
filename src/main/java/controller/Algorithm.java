package controller;

import model.Registers;
import model.rule.Rule;
import model.rule.RuleType;
import model.rule.Rules;

import java.util.ArrayList;
import java.util.Map;

public class Algorithm {

    private Registers registers = new Registers();
    private Rules rules = new Rules();

    public Algorithm(Map<Integer, Integer> initMap) {
        initMap.forEach((register, value) -> registers.setValue(register, value));
    }

    public void addRule(RuleType type, Integer... values) {
        rules.addRule(new Rule(type, values));
    }

    public void addRule(Rule rule) {
        rules.addRule(rule);
    }

    public int getResult() {

        int pos = 1;

        Rule next;
        while ((next = rules.getRule(pos)) != null) {
            ArrayList<Integer> values = next.getValues();

            switch (next.getType()) {
                case J: pos = J(values, pos); break;
                case T: T(values); pos++; break;
                case S: S(values); pos++; break;
                case Z: Z(values); pos++; break;
            }

        }

        return registers.getValue(0);
    }

    private int J(ArrayList<Integer> values, int pos) {
        int firstRegister = values.get(0);
        int secondRegister = values.get(1);
        int nextRule = values.get(2);

        int firstValue = registers.getValue(firstRegister);
        int secondValue = registers.getValue(secondRegister);

        return firstValue == secondValue ? nextRule : pos + 1;
    }

    private void T(ArrayList<Integer> values) {
        int firstRegister = values.get(0);
        int secondRegister = values.get(1);

        int firstValue = registers.getValue(firstRegister);
        registers.setValue(secondRegister, firstValue);
    }

    private void S(ArrayList<Integer> values) {
        int register = values.get(0);
        int previousValue = registers.getValue(register);

        registers.setValue(register, ++previousValue);
    }

    private void Z(ArrayList<Integer> values) {
        int register = values.get(0);
        registers.setValue(register, 0);
    }

}
