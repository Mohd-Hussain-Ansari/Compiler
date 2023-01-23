import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MinimizeDFA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String inputString, message;

        message = "Enter valid imputs: ";
        inputString = Utility.getInput(message, sc);

        Set<String> validInputs = new HashSet<String>(Arrays.asList(inputString.split(",")));
        // System.out.println("Input: " + validInputs);

        message = "Enter states: ";
        inputString = Utility.getInput(message, sc);

        Set<String> states = new HashSet<String>(Arrays.asList(inputString.split(",")));
        // System.out.println("States: " + states);

        message = "Enter starting state: ";
        String startingState = Utility.getInput(message, sc);
        // System.out.println("Starting state: " + startingState);

        message = "Enter all final states: ";
        inputString = Utility.getInput(message, sc);

        Set<String> finalState = new HashSet<String>(Arrays.asList(inputString.split(",")));
        // System.out.println("Final state: " + finalState);

        HashMap<String, HashMap<String, String>> transitions = new HashMap<String, HashMap<String, String>>();

        for (String state : states) {
            HashMap<String, String> transition = new HashMap<String, String>();
            for (String validInput : validInputs) {

                message = String.format("Enter transition state for state '%s' having input '%s' : ", state,
                        validInput);
                String nextState = Utility.getInput(message, sc);
                transition.put(validInput, nextState);
            }
            transitions.put(state, transition);
        }
        // System.out.println("transition: " + transitions);

        DFA dfa = new DFA(startingState, finalState, transitions);

        dfa.minimizeDFA();

        System.out.println("New DFA after minimization: ");
        System.out.println("Final states: " + dfa.getFinalStates());
        System.out.println("Transitions: " + dfa.getTransitions());

        sc.close();
    }
}