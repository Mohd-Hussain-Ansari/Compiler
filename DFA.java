import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * DFA
 */
public class DFA {

    String startingState;
    Set<String> finalStates;
    HashMap<String, HashMap<String, String>> transitions;


    public DFA(String startingState, Set<String> finalStates, HashMap<String, HashMap<String, String>> transitions) {
        this.startingState = startingState;
        this.finalStates = finalStates;
        this.transitions = transitions;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public HashMap<String, HashMap<String, String>> getTransitions() {
        return transitions;
    }

    public void validateString(String validateString) {

        String currentState = startingState;

        // iterating over the validateString string
        for (int i = 0; i < validateString.length(); i++) {
            char c = validateString.charAt(i);
            HashMap<String, String> currentStateTransition = transitions.get(currentState);

            String nextState = currentStateTransition.get(String.valueOf(c));

            // is transition for current state with input 'c' not present
            if (nextState == null) {
                // input is invalid
                Utility.showError(
                        String.format("Your string contain value '%c' which is not present in input set", c));
                return;
            }
            // now we have the next state as a current state for next iteration
            currentState = nextState;
        }

        // if current state is in final state then string is valid
        if (finalStates.contains(currentState)) {
            System.out.println("Output: Your string is valid");
        }
        // string ends at state which is not a final state
        else {
            Utility.showError(
                    String.format("Your string  ends at state '%s' which is not a final state", currentState));
        }

    }

    public Set<String> getReachableStates(String state, Set<String> reachableStates) {

        reachableStates.add(state);

        HashMap<String, String> currentStateTransition = transitions.get(state);
        // iterating over all the transitions of current state
        for (String transitionState : currentStateTransition.values()) {
            // if next state is not already in reachableStates set
            if (!reachableStates.contains(transitionState)) {
                // add next state to reachableStates set
                reachableStates.addAll(getReachableStates(transitionState, reachableStates));
            }
        }

        return reachableStates;
    }

    public void replaceState(String state, String replaceWith) {
        // replace state in transitions
        for (HashMap<String, String> currentStateTransition : transitions.values()) {
            for (String input : currentStateTransition.keySet()) {
                String nextState = currentStateTransition.get(input);
                if (nextState.equals(state)) {
                    currentStateTransition.put(input, replaceWith);
                }
            }
        }
    }

    public void minimizeDFA() {
        System.out.println("Minimizing DFA...");
        // get reachableStates states from start state
        Set<String> reachableStates = getReachableStates(startingState, new HashSet<String>());
        // System.out.println("Reachable states: " + reachableStates);

        System.out.println("Removing unreachable states from transitions...");

        // Step 1 : removed unreachable states from transitions
        transitions.entrySet().removeIf(entry -> !reachableStates.contains(entry.getKey()));
        // System.out.println("Transitions after removing unreachable states: " +
        // transitions);

        System.out.println("Splitting transitions into final and non final states...");

        // step 2 : seperate final and non final states into two array
        ArrayList<String> newFinalStates = new ArrayList<String>();
        ArrayList<String> nonFinalStates = new ArrayList<String>();

        for (String state : reachableStates) {
            if (finalStates.contains(state)) {
                newFinalStates.add(state);
            } else {
                nonFinalStates.add(state);
            }
        }

        // System.out.println("New Final states: " + newFinalStates);
        // System.out.println("Non final states: " + nonFinalStates);

        System.out.println("Replacing final states having same transition...");

        // step 3 : replace final states having common transition with one state
        int finalStateSize = newFinalStates.size();
        // iterating over new final states
        for (int i = 0; i < finalStateSize; i++) {
            for (int j = i + 1; j < finalStateSize + 1; j++) {
                String state = newFinalStates.get(i);
                String otherState = newFinalStates.get(j);
                // if both states have same transition
                if (transitions.get(state).equals(transitions.get(otherState))) {
                    // replace other state with current state
                    replaceState(otherState, state);
                    // System.out.println("replaced " + otherState + " with " + state);
                    transitions.remove(otherState);
                    newFinalStates.remove(otherState);
                    finalStateSize--;
                }

            }
        }

        finalStates = new HashSet<String>(newFinalStates);
        // System.out.println("Final states after replacing: " + finalStates);

        System.out.println("Replacing non final states having same transition...");

        // step 4 : replace non final states having common transition with one state
        int nonFinalStateSize = newFinalStates.size();
        // iterating over new final states
        for (int i = 0; i < nonFinalStateSize; i++) {
            for (int j = i + 1; j < nonFinalStateSize + 1; j++) {
                String state = nonFinalStates.get(i);
                String otherState = nonFinalStates.get(j);
                // if both states have same transition
                if (transitions.get(state).equals(transitions.get(otherState))) {
                    // replace other state with current state
                    replaceState(otherState, state);
                    // System.out.println("replaced " + otherState + " with " + state);
                    transitions.remove(otherState);
                    nonFinalStates.remove(otherState);
                    nonFinalStateSize--;
                }

            }
        }

    }

}