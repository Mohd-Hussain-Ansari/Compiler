import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class NFAValidateString {
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

        HashMap<String, HashMap<String, Set<String>>> transitions = new HashMap<>();

        for (String state : states) {
            HashMap<String, Set<String>> transition = new HashMap<>();
            for (String validInput : validInputs) {
                System.out.print(
                        String.format("Enter transition states for state '%s' having input '%s' : ", state,
                                validInput));

                String nextStates = sc.nextLine();
                if (!nextStates.isEmpty()) {
                    Set<String> traansitionStates = new HashSet<String>(Arrays.asList(nextStates.split(",")));
                    transition.put(validInput, traansitionStates);
                }

            }
            transitions.put(state, transition);
        }
        //  System.out.println("transition: " + transitions);

        NFA nfa = new NFA(validInputs, startingState, finalState, transitions);

        while (true) {
            message = "Enter string to be valiadated: ";
            String validateString = Utility.getInput(message, sc);
            nfa.validateString(validateString);

            System.out.print("Do you want to validate another string? (y/n): ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("n")) {
                break;
            }
        }

        sc.close();
    }

}
