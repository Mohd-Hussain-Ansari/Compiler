import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * DFA
 */
public class DFA {

    public void validateString(Set<String> validInputs, Set<String> states, String startingState, String finalState,
            HashMap<String, HashMap<String, String>> transitions, String validateString) {

        String currentState = startingState;

        // iterating over the validateString string
        for (int i = 0; i < validateString.length(); i++) {
            char c = validateString.charAt(i);
            HashMap<String, String> currentStateTransition = transitions.get(currentState);

            String nextState = currentStateTransition.get(String.valueOf(c));
            if (nextState == null) {
                System.out.println("Output: Your string is not valid");
                System.out.println(
                        String.format("Reason: Your string contain value '%c' which is not present in input set", c));
                return;
            }
            currentState = nextState;
        }

        if (currentState.equals(finalState)) {
            System.out.println("Output: Your string is valid");
        } else {
            System.out.println("Output: Your string is not valid");
            System.out.println(
                    String.format("Reason: Your string  ends at state '%s' which is not a final state", currentState));
            // System.out.println("Reason: Your string does not end with final state");
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter valid imputs: ");
        String inputString = sc.nextLine();
        Set<String> validInputs = new HashSet<String>(Arrays.asList(inputString.split(",")));
        // System.out.println("Input: " + validInputs);

        System.out.print("Enter states: ");
        inputString = sc.nextLine();
        Set<String> states = new HashSet<String>(Arrays.asList(inputString.split(",")));
        // System.out.println("States: " + states);

        System.out.print("Enter starting state: ");
        String startingState = sc.nextLine();
        // System.out.println("Starting state: " + startingState);

        System.out.print("Enter final state: ");
        String finalState = sc.nextLine();
        // System.out.println("Final state: " + finalState);

        HashMap<String, HashMap<String, String>> transitions = new HashMap<String, HashMap<String, String>>();

        for (String state : states) {
            HashMap<String, String> transition = new HashMap<String, String>();
            for (String validInput : validInputs) {
                System.out.print("Enter transition for state " + state + " having input " + validInput + ": ");
                String nextState = sc.nextLine();
                transition.put(validInput, nextState);
            }
            transitions.put(state, transition);
        }
        // System.out.println("transition: " + transitions);

        DFA dfa = new DFA();

        while (true) {
            System.out.print("Enter string to be valiadated: ");
            String validateString = sc.nextLine();
            dfa.validateString(validInputs, states, startingState, finalState, transitions, validateString);

            System.out.print("Do you want to validate another string? (y/n): ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("n")) {
                break;
            }
        }

        sc.close();
    }

}