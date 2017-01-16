package model.rule;

import java.util.ArrayList;
import java.util.Arrays;

public class Rule {

    private RuleType type;
    ArrayList<Integer> values = new ArrayList<>();

    public Rule(RuleType type, Integer... values) {
        this.type = type;
        int i = getRuleValuesNumber(type);

        for (Integer value: values) {
            if (i == 0) break;
            this.values.add(value);
            i--;
        }
    }

    public Rule(RuleType type, ArrayList<Integer> values) {
        this.type = type;
        int i = getRuleValuesNumber(type);

        for (Integer value: values) {
            if (i == 0) break;
            this.values.add(value);
            i--;
        }
    }

    public RuleType getType() {
        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder values = new StringBuilder();

        for (Integer value : this.values) {
            values.append(value).append(",");
        }

        values.setLength(values.length() - 1);

        return type + "(" + values.toString() + ")";
    }

    private static int getRuleValuesNumber(RuleType type) {
        switch (type) {
            case J: return 3;
            case T: return 2;
            case S: return 1;
            case Z: return 1;
            default: return 0;
        }
    }

}
