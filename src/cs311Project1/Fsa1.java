package cs311Project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fsa1 {

	private final static String fsaInputFile = "src/fsaInput.txt";
	private final static String endCurrentFSA= "## END FSA";
	private static  ArrayList<String> input=new ArrayList<String>();
	private static ArrayList<FSA> fSAList= new ArrayList<FSA>(); 

	public static void main(String[] args) {
		readFile(fsaInputFile);
		inputParser();
		System.out.println(fSAList);
		for (int i=0;i<fSAList.size();i++)
		{
			//System.out.println(i+" fsa");
			for(int j=0; j<fSAList.get(i).amountOfInputs();j++)
			{
				System.out.println(j +" : "+fSAList.get(i).inputStrings.get(j) +" Result "+fSAList.get(i).runFSA(j));
			}
			
		}

		
	}
	private static List<String> readFile(String filename) {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				input.add(line);
			}
			reader.close();
			return input;
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", filename);
			e.printStackTrace();
			return null;
		}
	}
	
	private static void inputParser()
	{
		int currentLine=0;
		
		int maxLine=input.size();
		//System.out.println("input size = "+input.size());
		while (currentLine<maxLine)
		{
			//System.out.println("start");
			FSA temp= new FSA();
			System.out.println("nextFSA");
			temp.setNumOfStates(Integer.parseInt(input.get((currentLine))));
			currentLine++;
			//adding of the final states
			int[] finalStates;
			String[] finalStatesString=input.get(currentLine).split(" ");// gotta do string to int
			
			finalStates= new int[finalStatesString.length];// instantiate finalstates int array
			for (int i=0;i<finalStatesString.length;i++)//fill in the final states
			{
				finalStates[i]= Integer.parseInt(finalStatesString[i]);
				
			}
			
			temp.setFinalStates(finalStates);
			currentLine++;
			
			//adding alphabet
			String[] alphaString =input.get(currentLine).split(" ");
			char[] alphaChars=new char[alphaString.length];
			for(int i=0;i<alphaString.length;i++)
			{
				alphaChars[i]=alphaString[i].charAt(0);
			}
			
			temp.setalphabet(alphaChars);
			
			currentLine++;
			
			//add
			
			while(input.get(currentLine).charAt(0)=='(')//add the transition functions, till no more transition functions are in the input
			{
				String[] transitionFunction;
				String noParenthesis =input.get(currentLine).substring(1,input.get(currentLine).length()-1);
				transitionFunction= noParenthesis.split(" ");//making transition function a set of three values
				//test to string to ensure the arrays are set currectly
				//System.out.println(Arrays.toString(transitionFunction));
				temp.addToTransitionFunctions(transitionFunction);
				currentLine++;
			}
			
			while (!input.get(currentLine).equals(endCurrentFSA))// add all input strings or exit if done
			{
				temp.addToInputStrings(input.get(currentLine));
				//System.out.println(input.get(currentLine));
				currentLine++;
			}
			currentLine++;
			
			//System.out.println(currentLine);
			//System.out.println("done");
			fSAList.add(temp);
			
			
			
			
			
			
		}
	}
}