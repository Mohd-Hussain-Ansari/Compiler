import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * NFA
 */
public class NFA {

    boolean isStringValid;

    // it will store end states for every recursive call
    Set<String> endStates = new HashSet<String>();

    Set<String> validInputs;
    String startingState, finalState;

    HashMap<String, HashMap<String, Set<String>>> transitions;

    public NFA(Set<String> validInputs, String startingState, String finalState,
            HashMap<String, HashMap<String, Set<String>>> transitions) {
        this.validInputs = validInputs;
        this.startingState = startingState;
        this.finalState = finalState;
        this.transitions = transitions;
    }

    public boolean isInvalidCharPresent(Set<String> validInputs, String validateString) {
        for (int i = 0; i < validateString.length(); i++) {
            char c = validateString.charAt(i);
            if (!validInputs.contains(String.valueOf(c))) {
                Utility.showError(
                        String.format("Your string contain value '%c' which is not present in input set", c));
                return true;
            }
        }
        return false;
    }

    public void validateString(String validateString) {

        // is validateString does not contains invalid character
        if (!isInvalidCharPresent(validInputs, validateString)) {

            isStringValid = false;
            try {

                Thread t1 = new Thread(
                        new ValidateStringThread(startingState, validateString));
                t1.start();
                t1.join();

                if (isStringValid) {
                    System.out.println("Output: Your string is valid");

                } else {
                    Utility.showError("Your string does not end with final state");
                    System.out.println("End states: " + endStates);

                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
    }

    private class ValidateStringThread implements Runnable {

        private String currentState, validateString;

        public ValidateStringThread(String currentState, String validateString) {
            this.currentState = currentState;
            this.validateString = validateString;
        }

        @Override
        public void run() {

            int i;

            // iterating in validateString and also checking if isStringValid becomes true
            // from any other thread
            for (i = 0; i < validateString.length() && !isStringValid; i++) {
                HashMap<String, Set<String>> transition = transitions.get(currentState);
                Set<String> nextStates = transition.get(String.valueOf(validateString.charAt(i)));

                // is there is no transition for current state
                if (nextStates == null) {
                    break;
                } else {
                    Iterator<String> iterator = nextStates.iterator();

                    currentState = iterator.next();

                    // creating new thread for every transition state
                    if (nextStates.size() > 1) {
                        while (iterator.hasNext()) {
                            String nextState = iterator.next();
                            try {
                                Thread t1 = new Thread(
                                        new ValidateStringThread(nextState, validateString.substring(i + 1)));
                                t1.start();
                                t1.join();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }

                    }

                }
            }

            if (i == validateString.length()) {

                if (currentState.equals(finalState)) {
                    isStringValid = true;
                }

                else {
                    endStates.add(currentState);
                }
            }

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