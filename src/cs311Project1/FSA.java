package cs311Project1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author Kevin Nguyen CPP CS311 Jan 25 2018
 *
 */

public class FSA {
	private int numOfStates;
	private boolean[] finalStates;
	private char[] alphabet;
	private ArrayList<String[]> transitionFunctions;
	public ArrayList<String> inputStrings;
	private final int INITIALSTATE = 0;
	private String currentString;
	private boolean[] passOrFail;
	private String[] finalStateString;

	// constructor

	public FSA() {
		transitionFunctions = new ArrayList<String[]>();
		inputStrings = new ArrayList<String>();
	}

	// runFSA through all input strings
	public void runFSA() {
		for (int i = 0; i < inputStrings.size(); i++) {
			runFSA(i);
		}

	}

	// method used to run the fsa on a specific inputString index
	public boolean runFSA(int inputIndex) {
		char currentSymbol;
		int state = INITIALSTATE;
		int currentSymbolIndex = 0;
		boolean exit = false;

		currentString = inputStrings.get(inputIndex);// assigning the current string to be worked on
		currentSymbol = currentString.charAt(currentSymbolIndex);// get the first char
		for (int i = 0; i < alphabet.length; i++)// check to see if the first char is in the alphabet
		{
			if (alphabet[i] == currentSymbol) {
				state = 0;
				exit = false;
				break;
			} else {
				exit = true;// exit the loop, the character is not in the alphabet
			}
		}

		while (!exit) {
			currentSymbol = currentString.charAt(currentSymbolIndex);// iterate to the next symbol in the function
			state = getNextState(state, currentSymbol);
			currentSymbolIndex++;
			if (state > numOfStates || currentSymbolIndex == currentString.length())// exit= true if trap, or no more
			                                                                        // symbols left
			{
				exit = true;
			}

		}
		if (exit)// determining if we are exiting because we are at the end or if we are trapped,
		         // or if any other reason
		{
			if (currentSymbolIndex == currentString.length() && finalStates[state]) {
				passOrFail[inputIndex] = true;// we aer in a final state set the string to pass
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	// method used to determine what the next state should be given a current state
	// and the next symbol
	private int getNextState(int currentState, char nextSymbol) {

		for (int i = 0; i < transitionFunctions.size(); i++) {

			if (currentState == ((Integer.parseInt(transitionFunctions.get(i)[0]))))// get the function that starts at
			                                                                        // the current state
			{

				if (transitionFunctions.get(i)[1].charAt(0) == nextSymbol)// get the correct input
				{

					return Integer.parseInt(transitionFunctions.get(i)[2]);// return the next state
				}
			}
		}
		return numOfStates - 1;// state wasnt found, return 1 more than num of states, to indicate trap state
	}

	// set the num of states
	public void setNumOfStates(int tempNumOfStates) {

		numOfStates = tempNumOfStates + 1;// +1 for trap state
	}

	// method to set which states are going to be held true for final states
	public void setFinalStates(int[] tempFinalStates) {
		finalStates = new boolean[numOfStates];

		for (int i = 0; i < tempFinalStates.length; i++)// set any final state to true, leave the rest to false
		{
			finalStates[tempFinalStates[i]] = true;
		}
	}

	// set the alpha bet according to a char array
	public void setalphabet(char[] tempAlphabet) {
		alphabet = tempAlphabet;

	}

	// set the transition functions to be followed
	public void setTransistionFunctions(ArrayList<String[]> tempTransitionFunctions) {
		transitionFunctions = tempTransitionFunctions;
	}

	// add to the transition function arraylist format is (p a q)
	public void addToTransitionFunctions(String[] tempTransitionFunction) {
		transitionFunctions.add(tempTransitionFunction);
	}

	// setting the inout array list to a new array list of all inputs being used
	public void setInputStrings(ArrayList<String> tempInputString) {
		inputStrings = tempInputString;
	}

	// adding to the input arraylist with another input
	public void addToInputStrings(String tempInputString) {
		inputStrings.add(tempInputString);

	}

	// formatting a string that will be readable
	public String toString() {
		return "\nNumber of states: " + numOfStates + "\nFinal states: " + Arrays.toString(finalStateString)
		        + "\nAlphabet: " + Arrays.toString(alphabet) + "\nTransition: \n" + transitionsToString() + "\nStrings:"
		        + passFailString() + "\n";
	}

	// set which states need to be identified by using a boolean
	public void setFinals(String[] tempFinalStates) {
		finalStateString = tempFinalStates;
	}

	// to string method to show if something passed or not
	public String passFailString() {
		String output = "";
		for (int i = 0; i < amountOfInputs(); i++)// iterate through the list of inputs
		{
			output += "\nInput " + i + ") " + inputStrings.get(i);

			for (int j = inputStrings.get(i).length(); j < 50; j++) {
				output += " ";
			}
			output += "Result: " + passOrFail[i];
		}
		return output;
	}

	// helper for to string
	public void setPassOrFailSize() {
		passOrFail = new boolean[inputStrings.size()];
	}

	// helper for to string
	public String transitionsToString() {
		String transitionString = "";
		for (int i = 0; i < transitionFunctions.size(); i++) {
			transitionString += transitionFunctions.get(i)[0] + " ";
			transitionString += transitionFunctions.get(i)[1] + " ";
			transitionString += transitionFunctions.get(i)[2];
			transitionString += "\n";
		}
		return transitionString;

	}

	// helper method to set a count on how many inputs there are
	public int amountOfInputs() {
		return inputStrings.size();
	}

}