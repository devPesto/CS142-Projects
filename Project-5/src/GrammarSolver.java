/**
 * Name: Amir Seman
 * Instructor: Allison Obourn
 * Course: CS 142, Fall 2023
 * Project: Grammar Solver
 * <p>
 * This class assists the main class to help generate sentences that follow a grammatical rule set
 * provided by the user in a file. This grammatical ruleset follows the Brackus-Naur Form (BNF) that dictates *how sentences are grammatically.
 */

import java.util.*;

public class GrammarSolver {
    private Map<String, String[]> map = new HashMap<>();
    private Random random = new Random();

    /**
     * Parameterized constructor that populates a hash map with the given
     * rules.
     *
     * @param rules a list of rules read from a file
     * @throws IllegalArgumentException if the rules given is empty or null
     */
    public GrammarSolver(List<String> rules) {
        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("Rule set is null or empty");
        }
        populateMap(rules);
    }

    /**
     * @param symbol the given symbol
     * @return true if the given symbol is non-terminal (i.e. a key of the map), otherwise false
     * @throws IllegalArgumentException if the symbol is null or has a length of 0
     */
    public boolean contains(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException("Null or empty string passed");
        }
        return map.containsKey(symbol);
    }

    /**
     * @return a key set of the map containing all non-terminal symbols
     */
    public Set<String> getSymbols() {
        return map.keySet();
    }

    /**
     * Method that generates terminal words/phrases from a given symbol. If the symbol is a non-terminal or the rule contains non-terminal symbols, the method will recursively call
     * <p>
     * itself until only terminal symbols remain. If multiple rules are present for a symbol then * a rule is picked at random.
     *
     * @param symbol the symbol passed by the user
     * @return Á word or phrase that consists of non-terminal words
     */
    public String generate(String symbol) {
        if (contains(symbol)) {
            String[] rules = map.get(symbol);
            String rule = rules[random.nextInt(rules.length)];
            StringBuilder result = new StringBuilder();
            for (String r : rule.split(" ")) {
                result.append(generate(r)).append(" ");
            }
            return result.toString().trim();
        } else {
            return symbol;
        }
    }

    /**
     * Populates the HashMap with the BNF grammar rules
     *
     * @param rules list containing the rule set of grammar rules in the Backus - Naur Form
     * @throws IllegalArgumentException if a rule with an existing key is inserted
     */
    private void populateMap(List<String> rules) {
        for (String line : rules) {
            String[] entry = line.split(" : :=");
            String key = entry[0];
            if (!map.containsKey(key)) {
                String[] value = entry[1].split("\\|");
                map.put(key, value);
            } else {
                throw new IllegalArgumentException("Map already contains key: " + key);
            }
        }
    }
}