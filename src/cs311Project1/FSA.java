package cs311Project1;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class FSA {
	private int numOfStates;
	private boolean[] finalStates;
	private char[] alphabet;
	private ArrayList<String[]> transitionFunctions;
	public ArrayList<String> inputStrings;
	private final int INITIALSTATE=0;
	private String currentString;
	private boolean[] passOrFail;
	
	
	public FSA()
	{
		transitionFunctions= new ArrayList<String[]>();
		inputStrings= new ArrayList<String>();
	}
	
	public boolean runFSA(int inputIndex)
	{
		char currentSymbol;
		int state= INITIALSTATE;
		int currentSymbolIndex=0;
		boolean exit=false;
		
		currentString=inputStrings.get(inputIndex);//assigning the current string to be worked on
		currentSymbol=currentString.charAt(currentSymbolIndex);//get the first char
		for (int i=0;i<alphabet.length;i++)//check to see if the first char is in the alphabet
		{
			if(alphabet[i]==currentSymbol)
			{
				state=0;
				exit= false;
				break;
			}
			else
			{
			exit=true;
			}
		}
		
		while(!exit)
		{
			currentSymbol=currentString.charAt(currentSymbolIndex);//iterate to the next symbol in the function
			state= getNextState(state,currentSymbol);
			currentSymbolIndex++;
						if (state>numOfStates||currentSymbolIndex==currentString.length())//exit= true if trap, or no more symbols left
			{
				exit=true;
			}
			
		}
		if(exit)
		{
			if(currentSymbolIndex==currentString.length()&&finalStates[state])
			{
				passOrFail[inputIndex]=true;
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	
	private int getNextState(int currentState, char nextSymbol)
	{

		for(int i=0;i<transitionFunctions.size();i++)
		{

			if(currentState==((Integer.parseInt(transitionFunctions.get(i)[0]))))//get the function that starts at the current state
			{

				if(transitionFunctions.get(i)[1].charAt(0)==nextSymbol)// get the correct input
				{
					
					return Integer.parseInt(transitionFunctions.get(i)[2]);//return the next state
				}
			}
		}
		return numOfStates-1;//state wasnt found, return 1 more than num of states, to indicate trap state
	}
	
	
	public void setNumOfStates(int tempNumOfStates)
	{
		
		numOfStates= tempNumOfStates+1;

	}
	
	public void setFinalStates(int[] tempFinalStates)
	{
		finalStates = new boolean[numOfStates];
		
		for(int i= 0; i<tempFinalStates.length; i++)//set any final state to true, leave the rest to false
		{
			finalStates[tempFinalStates[i]]=true;
		}
	}
	
	public void setalphabet(char[] tempAlphabet)
	{
		alphabet=tempAlphabet;
		
		
	}
	
	public void setTransistionFunctions(ArrayList<String[]> tempTransitionFunctions)
	{
		transitionFunctions=tempTransitionFunctions;
	}
	
	public void addToTransitionFunctions(String[] tempTransitionFunction)
	{
		transitionFunctions.add(tempTransitionFunction);
	}
	
	public void setInputStrings(ArrayList<String> tempInputString)
	{
		inputStrings = tempInputString;
	}
	
	public void addToInputStrings(String tempInputString)
	{
		inputStrings.add(tempInputString);
		
	}
	public void setPassOrFailSize() 
	{
		passOrFail= new boolean[inputStrings.size()];
	}
	
	public String toString()
	{
		return "\nNumber of states: " + numOfStates+ "\nFinal states: "+ Arrays.toString(finalStates)
				+"\nAlphabet: "+ Arrays.toString(alphabet)+ "\nTransition: \n"+ transitionsToString()+ 
				"\nStrings:" + passFailString()+"\n";
	}
	
	public String passFailString()
	{
		String output="";
		for (int i=0; i< amountOfInputs();i++)
		{
			output+= "\nInput"+ i+")"+ inputStrings.get(i)+ "        Result: "+ passOrFail[i];
		}
		return output;
	}
	
	
	
	public String transitionsToString()
	{
		String transitionString="";
		for(int i=0;i<transitionFunctions.size();i++)
		{
			transitionString+=transitionFunctions.get(i)[0]+" ";
			transitionString+=transitionFunctions.get(i)[1]+" ";
			transitionString+=transitionFunctions.get(i)[2];
			transitionString +="\n";
		}
		return transitionString;

	}
	public int amountOfInputs()
	{
		return inputStrings.size();
	}
	
	

}
