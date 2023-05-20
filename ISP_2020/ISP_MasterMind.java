package ISP_2020;

import java.util.Scanner;
import java.io.*;
import java.util.Random;

/*
 *Author: Donghui Yu
 *use programming knowledge and skills leaned from school to implement this chessboard game
 *2020
 */

public class ISP_MasterMind {
	
	public static String[] codeEntered(String codeEntered_string) {//method 1
		String[] codeEntered_arrayRaw = new String[codeEntered_string.length()];//create unprocessed array
		int numBlank = 0;//create counter
		for(int i = 0; i < codeEntered_string.length();i++) {
			if(codeEntered_string.charAt(i) != ' ') {//when no blanks
				codeEntered_arrayRaw[i] = "" + codeEntered_string.charAt(i);//assign char variable to array
			}else {
				numBlank++;//count the number of blanks
				codeEntered_arrayRaw[i] = "";
			}
		}
		String[] codeEntered_arrayFine = new String[codeEntered_string.length()-numBlank];//create new array that is going to be returned
		int counterCodeEntered = 0;//create the counter for codeEntered_arrayFine
		for (int i = 0; i < codeEntered_string.length(); i++) {
			if (!(codeEntered_arrayRaw[i].equals(""))) {
				codeEntered_arrayFine[counterCodeEntered] = codeEntered_arrayRaw[i];
				counterCodeEntered ++;
			}
		}
		return(codeEntered_arrayFine);//return
	}
	
	
	
	public static String[] shuffle(String[] array) {//method 2--use switch to shuffle the array
		Random randomGeneratorInMethod = new Random();
		for(int i = 0; i < array.length; i++) {//randomly switch the array for 24 times in order to make the array as random as possible
			int temptIndex = randomGeneratorInMethod.nextInt(array.length);
			String tempt = array[i];
			array[i] = array[temptIndex];
			array[temptIndex] = tempt; //switching the variables in array 
		}
		return array;//return
		
	}
	
	
	
	public static String[] compareArray(String[] arrayPlayer, String[] arrayCode) {//method 3
		String[] arrayResult = new String[4];//create a new array which stores the comparing result of 'ø' or 'o'
		for(int i = 0; i < arrayPlayer.length; i++) {
			if (arrayPlayer[i].equals(arrayCode[i])) {
				arrayResult[i] = "ø";//both color(type) and order is correct
			} else {
				boolean colorRightOrderWrong = false; //set boolean value: the color is correct but the order is wrong
				for(int k = 0; k < arrayResult.length; k++) {
					if (arrayPlayer[i].equals(arrayCode[k])) {
						colorRightOrderWrong = true;
						arrayResult[i] = "o";//correct color but wrong order
					}
				}
				if (! colorRightOrderWrong) {//when both color and order are wrong
					arrayResult[i] = "";//assign "nothing"(nothing will display on the screen)
				}
			}
		}
		return arrayResult;//return
	}
	
	
	
	public static void main(String[] args) throws Exception {//main method
		
		
		Scanner scanNormal = new Scanner(System.in);//create a normal scanner to receive input from user
		PrintWriter writerCodePeg = new PrintWriter("codePegs.txt");//create new print writer
		File fileCodePeg = new File("codePegs.txt");//create file
		PrintWriter writerRecording = new PrintWriter("record.txt");//create; used to record win and lose of a single player
		
		for(int i = 0; i < 6; i++) {//store 24 code pegs into codePeg.txt
			writerCodePeg.println("r");
			writerCodePeg.println("y");
			writerCodePeg.println("b");
			writerCodePeg.println("g");
			writerCodePeg.println("v");
			writerCodePeg.println("i");
		}
		writerCodePeg.close();//save the file
		
		//print rules
		System.out.println("RULES: in this game, there are a hidden code and you are supposed to decode by inputing 4 code pegs");
		System.out.println("you have 8-12 turns to decode depending on the difficulty you choose");
		System.out.println("the hidden code consists of 4 different code pegs; ");
		System.out.println("there are totally 6 different code pegs, each type has 4 code pegs");
		System.out.println("r for red, y for yellow, b for blue, g for green, v for violet, i for indigo");
		System.out.println("the console will print 'ø' for both order and color is correct ");
		System.out.println("and output 'o' for correct color but wrong order");
		System.out.println("to be mentioned: the result is printed randomly");
		System.out.println("believe your self and try your best :) ");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		
		System.out.println("WELCOME to MASTERMIND!!!  what is your name, player?");
		String name = scanNormal.nextLine();
		System.out.println("Feel HAPPY! to play this game, "+name);
		
		
		
		boolean boolMoreRounds = true;//set a boolean variable to make the big loop work; 
		while (boolMoreRounds) {//the big loop will iterate if the player wants to play more
			Scanner scanFile = new Scanner(fileCodePeg);//create scanner of file
			String[] codePegArray = new String[24];//there should only 24 elements in the code peg array
			for(int i = 0; i < 24; i++) {//assign values to array
				codePegArray[i] = scanFile.next();
			}//to get the array of all code pegs
			scanFile.close();//close the scanFile
			
			
			System.out.println("which difficulty level you want to play, "+name+"? enter 1 for easy(12 turns to decode) and enter 2 for hard(8 turns to decode)");
			int difficulty = scanNormal.nextInt();//ask for difficulty
			scanNormal.nextLine();//to convenient the following input
			int turns = 12;//set and initialize how many turns the player is  going to play
			
			if ((difficulty != 1)&&(difficulty != 2)) {
				System.out.println("Sorry, the console cannot identify what you entered, due to the choice you choose, you have 12 turns to decode");
				turns = 12;
			}else if(difficulty == 1){
				System.out.println("Thanks for your wise decision, you now have 12 turns to decode");
				turns = 12;
			}else if(difficulty == 2) {
				System.out.println("Thanks for your wise decision, you now have 8 turns to decode");
				turns = 8;
			}
			System.out.println("");
			System.out.println("");
			
			
			String[] codeArray = new String[4];//create the blank array which will store the code
			String[] codeShuffleArray = shuffle(codePegArray);//shuffle to create the code
			for(int i = 0; i < 4; i++) {//assign values to code array
				codeArray[i] = codeShuffleArray[i];
			}
		
			
			
			boolean winLose = false;//create and initialize; winLose will be true when the player win this round, otherwise, false
			for(int i = 0; i < turns; i++) {//start the real game
				System.out.println("turn " + (i+1));
				System.out.println("enter your code with 4 code pegs");
				String codeEnteredString = scanNormal.nextLine();//prompt the user to enter their codes
				String[] codeEnteredArray = codeEntered(codeEnteredString);//convert input to array
				boolean formatCorrect = false;//set and initialize the boolean
				if(codeEnteredArray.length == 4) {
					formatCorrect = true;//when the user enter 4 code pegs, formatCorrect is true; if not, false
				}
				while(formatCorrect == false) {//to make sure the user enters in the correct format
					System.out.println("please enter your code with 4 code pegs");
					codeEnteredString = scanNormal.nextLine();//prompt the user to enter their codes
					codeEnteredArray = codeEntered(codeEnteredString);//convert input(string) to array
					if(codeEnteredArray.length == 4) {
						formatCorrect = true;//to end the loop
					}else {
						formatCorrect = false;
					}
				}
				
				
				System.out.print("                     result: ");
				String[] resultArray = compareArray(codeEnteredArray , codeArray);//compare the input from player and the code
				String[] resultShuffleArray = shuffle(resultArray);
				for(int a = 0; a < 4; a++) {
					System.out.print(resultShuffleArray[a]);//print the result
				}
				System.out.println("");
				if ((resultArray[0].equals("ø"))&&(resultArray[1].equals("ø"))&&(resultArray[2].equals("ø"))&&(resultArray[3].equals("ø"))) {//when decoding successfully
					winLose = true;
					i = turns;//to end the loop
				} else {
					winLose = false;
				}
			}
			
			
			
			
			if(winLose) {//when winning
				System.out.println("Congratulation, WIN!!!");
				writerRecording.println("win");//recording the winning of player
			}else if (! winLose) {//when losing
				System.out.println("Unluckily, you lose");
				System.out.println("the code is " + codeArray[0]+codeArray[1]+codeArray[2]+codeArray[3]);
				writerRecording.println("lose");//recording the losing of player
			}
			System.out.println("do you want another game round? enter 1 for yes and 2 for no");
			int willOfPlayer = scanNormal.nextInt();
			if(willOfPlayer == 1) {
				boolMoreRounds = true;//the player wants to play more
			}else if(willOfPlayer == 2) {
				boolMoreRounds = false;//the player wants to stop
			}else {
				System.out.println("the console cannot identify what you entered, the game round is now over");
				boolMoreRounds = false;//cannot identify what the player wants, the program automatically end the game
			}
			
			
		
		}
		writerRecording.close();//save win and lose
		scanNormal.close();//close scanner
	}

}
