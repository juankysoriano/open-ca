package com.openca;

import com.openca.bi.continous.AutomataTotalisticContinous2DMoore;
import com.openca.bi.discrete.AutomataCyclic2D;
import com.openca.bi.discrete.AutomataElementary2D;
import com.openca.bi.discrete.AutomataOuterTotalistic2D;
import com.openca.bi.discrete.AutomataTotalistic2D;
import com.openca.uni.continous.AutomataTotalContinous1D;
import com.openca.uni.discrete.AutomataCyclicDiscrete1D;
import com.openca.uni.discrete.AutomataDiscreteOuterTotalisticDiscrete1D;
import com.openca.uni.discrete.AutomataElementaryDiscrete1D;
import com.openca.uni.discrete.AutomataTotalisticDiscrete1D;

import static com.openca.Automata.*;

class CellularAutomataFactory {
    public static CellularAutomata createFor(int size, int radius, int states, String rule, Dimension dimension, Domain domain, Type type) {

        return dimension == Dimension.UNIDIMENSIONAL
                ? createUnidimensionalFor(size, radius, states, rule, domain, type)
                : createBidimensionalFor(size, radius, states, rule, domain, type);
    }

    private static CellularAutomata createUnidimensionalFor(int size, int radius, int states, String rule, Domain domain, Type type) {
        return domain == Domain.DISCRETE
                ? createUnidimensionalDiscreteFor(size, radius, states, rule, type)
                : createUnidimensionalContinousFor(size, radius, states, rule);
    }

    private static CellularAutomata createBidimensionalFor(int size, int radius, int states, String rule, Domain domain, Type type) {
        return domain == Domain.DISCRETE
                ? createBidimensionalDiscreteFor(size, radius, states, rule, type)
                : createBidimensionalContinousFor(size, radius, states, rule);
    }

    private static CellularAutomata createUnidimensionalDiscreteFor(int size, int radius, int states, String rule, Type type) {
        Automata automata;

        switch (type) {
            case CYCLIC:
                automata = new AutomataCyclicDiscrete1D();
                break;
            case TOTALISTIC:
                automata = new AutomataTotalisticDiscrete1D();
                break;
            case OUTER_TOTALISTIC:
                automata = new AutomataDiscreteOuterTotalisticDiscrete1D();
                break;
            case ELEMENTARY:
                automata = new AutomataElementaryDiscrete1D();
                break;
            default:
                automata = new AutomataElementaryDiscrete1D();
        }

        automata.setSize(size);
        automata.setStates(states);
        automata.setRadius(radius);
        automata.setRule(rule);
        return automata;
    }

    private static CellularAutomata createBidimensionalDiscreteFor(int size, int radius, int states, String rule, Type type) {
        Automata automata;
        switch (type) {
            case CYCLIC:
                automata = new AutomataCyclic2D();
                break;
            case TOTALISTIC:
                automata = new AutomataTotalistic2D();
                break;
            case OUTER_TOTALISTIC:
                automata = new AutomataOuterTotalistic2D();
                break;
            case ELEMENTARY:
                automata = new AutomataElementary2D();
                break;
            default:
                automata = new AutomataElementary2D();
        }

        automata.setSize(size);
        automata.setStates(states);
        automata.setRadius(radius);
        automata.setRule(rule);
        return automata;
    }

    private static CellularAutomata createUnidimensionalContinousFor(int size, int radius, int states, String rule) {
        Automata automata = new AutomataTotalContinous1D();
        automata.setSize(size);
        automata.setStates(states);
        automata.setRadius(radius);
        automata.setRule(rule);
        return automata;
    }

    private static CellularAutomata createBidimensionalContinousFor(int size, int radius, int states, String rule) {
        Automata automata = new AutomataTotalisticContinous2DMoore();
        automata.setSize(size);
        automata.setStates(states);
        automata.setRadius(radius);
        automata.setRule(rule);
        return automata;
    }

}
