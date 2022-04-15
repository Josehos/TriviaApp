package com.kenzie.app;

// import necessary libraries

import java.util.*;

//Enum list for the user to pick from
enum ClueCriteria {
    RANDOM,
    WORLD_HISTORY,
    SLOGANEERING,
    ASIAN_HISTORY
}

public class Main {

    //Compares user's input to the correct answer
    public static boolean isCorrect(ClueDTO clue, String userInput){
        return userInput.toLowerCase(Locale.ROOT).equals(clue.getAnswer().toLowerCase(Locale.ROOT));
    }

    //Creates a Clue object from a list of clues. Object is created randomly
    public static ClueDTO getRandomClue(ClueListDTO clueListDTO, int num) {
        return clueListDTO.getClues().get(num);
    }

    //Determines URL for GET request based on user input
    public static String determineURL(int userPick) {
        switch (userPick) {
            case 1:
                return CustomHttpClient.getDesiredURL(ClueCriteria.RANDOM);
            case 2:
                return CustomHttpClient.getDesiredURL(ClueCriteria.WORLD_HISTORY);
            case 3:
                return CustomHttpClient.getDesiredURL(ClueCriteria.SLOGANEERING);
            case 4:
                return CustomHttpClient.getDesiredURL(ClueCriteria.ASIAN_HISTORY);
            default:
                return "Invalid entry";
        }
    }

    //Determines if the user wants to continue playing after 10 questions have been displayed
    public static boolean continuePlay() {
        System.out.print("Would you like to continue playing? Y/N: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toUpperCase(Locale.ROOT).equals("Y");
    }

    public static void main(String[] args) {

        //Opening message with user prompt
        System.out.println("Welcome to my Random Question generator game!\n" +
                "You can pick from random questions or pick from 3 different categories:\n" +
                "Enter 1 for Random questions\n" +
                "Enter 2 for World History questions\n" +
                "Enter 3 for Sloganeering questions\n" +
                "Enter 4 for Asian History questions\n");

        //Instantiate a Scanner object
        Scanner scan = new Scanner(System.in);

        //User prompt to enter a number
        System.out.print("Please enter a number: ");

        //Loop to verify the user entered an Integer 1-4
        while (!scan.hasNextInt()) {
            String input = scan.next();
            System.out.print(input + " is not a valid number, please try again! ");
        }
        //Variable to store user input
        int userPick = scan.nextInt();

        try {
            //Store GET request in a String variable. URL is determined by user input
            String urlResponse = CustomHttpClient.sendGET(determineURL(userPick));

            //Creates a List of Clues
            ClueListDTO allClues = CustomHttpClient.getClueList(urlResponse);

            //Number of questions tracker
            int questionCount = 0;

            //User's score counter
            int score = 0;

            do {
                //Instantiate a new Scanner object. The previous Scanner object would answer the first questions w/out an input
                Scanner scanner = new Scanner(System.in);

                //Instantiate a Random object to pull a random clue
                Random random = new Random();

                //Random number generator is based on the size of the List of Clues
                int randomNum = random.nextInt(allClues.getClues().size());

                //Create a random Clue object from the List of Clues
                ClueDTO clue = getRandomClue(allClues,randomNum);

                //Displays Category and Question
                System.out.println(clue.toString());

                //Prompts for user's input
                System.out.print("Enter your answer here: ");
                String userInput = scanner.nextLine();

                //Checks for the correct answer. +1 is correct or the correct answer is displayed if wrong
                if (isCorrect(clue, userInput)) {
                    score++;
                    System.out.println("Correct, plus 1 point! You current score is: " + score + "\n");
                } else {
                    System.out.println("Wrong answer, the correct answer is: " + clue.getAnswer() + "\n");
                }

                //Increment the question tracker
                questionCount++;

                //Prompts user after 10 questions have been displayed if they want to continue.
                if (questionCount == 10) {
                    if (continuePlay()) {
                        questionCount = 0;
                    } else {
                        break;
                    }
                }
            } while (questionCount < 10);

            //Displays final score
            System.out.println("Your final score is: " + score);

        } catch (Exception e) {
            //Catch statement to catch all Exceptions that bubble up
            System.out.println(e.getMessage());
        }
    }
}

