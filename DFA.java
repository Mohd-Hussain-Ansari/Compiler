import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * DFA
 */
public class DFA {

    String startingState, finalState;
    HashMap<String, HashMap<String, String>> transitions;

    public DFA(String startingState, String finalState, HashMap<String, HashMap<String, String>> transitions) {
        this.startingState = startingState;
        this.finalState = finalState;
        this.transitions = transitions;
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

        // if current state is final state then string is valid
        if (currentState.equals(finalState)) {
            System.out.println("Output: Your string is valid");
        }
        // string ends at state which is not a final state
        else {
            Utility.showError(
                    String.format("Your string  ends at state '%s' which is not a final state", currentState));
        }

    }

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

        message = "Enter final state: ";
        String finalState = Utility.getInput(message, sc);
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

        while (true) {
            message = "Enter string to be valiadated: ";
            String validateString = Utility.getInput(message, sc);
            dfa.validateString(validateString);

            System.out.print("Do you want to validate another string? (y/n): ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("n")) {
                break;
            }
        }

        sc.close();
    }

}