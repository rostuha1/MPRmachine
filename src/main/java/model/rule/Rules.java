package model.rule;

import java.util.ArrayList;

public class Rules {

    private ArrayList<Rule> rules = new ArrayList<>();

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public Rule getRule(int index) {
        if (index < 1 || index > rules.size()) return null;
        return rules.get(index - 1);
    }

}
