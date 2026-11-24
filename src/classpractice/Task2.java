package classpractice;
/*
* File: Task2.java
* Author: Jalen Brown
* Course: CISC230
* Lab: Lab6
* Date: 11/11/2026
* 
* Description:
* Program is a vowel finder game that reads words from a file
* and allows users to select various difficulties. User
* must then find the vowels at different character positions.
* Program tracks progression through various features like points,
* bonus points, and word length hints.
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/** CISC 230 - Lab 6, Task 2: Vowel Finder Challenge. */
public class Task2 extends Application {

    //declaring instance variables
	private ArrayList<String> allWords = new ArrayList<String>();

    private ArrayList<String> filteredWords = new ArrayList<String>();
    
    private TextField startField;
    private TextField positionField;
    
    private RadioButton easyRadio;
    private RadioButton mediumRadio;
    private RadioButton hardRadio;
    
    private CheckBox hintBox;
    private CheckBox bonusBox;
    
    private Button startButton;
    private Button checkButton;
    private Button nextButton;
    private Button resetButton;
    
    private Label wordLabel;
    private Label progressLabel;
    private Label scoreLabel;
    private Label feedbackLabel;
    private Label hintLabel;

    private int currentIndex = -1;
    private int score = 0;
    private boolean checked = false;
    private boolean active = false;

    public void start(Stage stage) throws FileNotFoundException {
    	
    		//loading words from file
    		loadWords("words.txt");
    		
    		//giving text fields their starting indexes
    		startField = new TextField("0");
    		positionField = new TextField();
    		    		
    		//creating buttons
    		easyRadio = new RadioButton("Easy");
    		mediumRadio = new RadioButton("Medium");
    		hardRadio = new RadioButton("Hard");
    		
    		//placing buttons in toggle group
    		ToggleGroup group = new ToggleGroup();
    		easyRadio.setToggleGroup(group);
    		mediumRadio.setToggleGroup(group);
    		hardRadio.setToggleGroup(group);
    		
    		//having the default button set to easy
    		easyRadio.setSelected(true);

    		//creating checkboxes for hints and bonus points
    		hintBox = new CheckBox("Show word length hint.");
    		bonusBox = new CheckBox("Enable bonus points.");
    		
    		hintBox.setSelected(true);
    		bonusBox.setSelected(false);
    		
    		//creating game buttons
    		startButton = new Button("Start Game");
    		checkButton = new Button("Check Vowel");
    		nextButton = new Button("Next Word");
    		resetButton = new Button("Reset");
    		
    		
    		//creating game labels
    		Label titleLabel = new Label("Vowel Finder Challenge");
    		wordLabel = new Label("Word: --");
    		progressLabel = new Label("Progress: 0");
    		scoreLabel = new Label("Score: 0");
    		feedbackLabel = new Label("Choose a difficulty and start.");
    		hintLabel = new Label("");
    		
    		//setting buttons to method calls
    		startButton.setOnAction(this::onStart);
    		checkButton.setOnAction(this::onCheck);
    		nextButton.setOnAction(this::onNext);
    		resetButton.setOnAction(this::onReset);
    		hintBox.setOnAction(this::onHintChanged);
    		
    		//storing difficulty buttons in hbox
    		HBox boxDifficulty = new HBox(10);
    		boxDifficulty.getChildren().addAll(
    		    easyRadio, mediumRadio, hardRadio
    		);
    		    		
    		//storing game buttons in hbox
    		HBox boxButtons = new HBox(10);
    		boxButtons.getChildren().addAll(
    		    startButton, checkButton, nextButton, resetButton
    		);

    		//storing text fields in hbox
    		HBox boxText = new HBox(10);
    		boxText.getChildren().addAll(new Label("Starting word index: "), 
    				startField, new Label("Vowel Index: "), positionField);

    		//organizing checkboxes in hbox
    		HBox boxCheck = new HBox(10);
    		boxCheck.getChildren().addAll(hintBox, bonusBox);
    		
    		//establishing the main layout for game components
    		VBox vbox = new VBox(20);
    		vbox.setPadding(new Insets(25));

    		vbox.getChildren().addAll(
    		    titleLabel,
    		    boxDifficulty,
    			boxText,
    		    boxCheck,
    		    boxButtons,
    		    wordLabel,
    		    progressLabel,
    		    scoreLabel,
    		    feedbackLabel,
    		    hintLabel
    		);
    		
    		//creating and displaying scene
    		Scene scene = new Scene(vbox, 600, 400);
    		stage.setScene(scene);
    		stage.setTitle("Vowel Finder Challenge");    		
    		stage.show();  
    		
    		//setting the initial button states
    		updateButtons();
    }

    //loop that reads the file words and stores them in arraylist
    private void loadWords(String filename) throws FileNotFoundException {
    	
		Scanner scan = new Scanner(new File(filename));
    		
    		while(scan.hasNextLine()) {
    			
    			String line = scan.nextLine();
    			line = line.trim();
    			
    			if(!line.isEmpty()) {
    				allWords.add(line);
    				
    		}
    				
    		}
    		
    		scan.close();
    }

   //filters words by difficulty
    private void onStart(ActionEvent e) {
    	
    		filteredWords.clear();
    		
    		//adds words to filtered list based on difficulty
    		int i = 0;
    		
    		while(i < allWords.size()) {
    			String currentWord = allWords.get(i);
    			
    			if(easyRadio.isSelected()) {
    				if(currentWord.length() >= 3 && currentWord.length() <=5 ) {
    					filteredWords.add(currentWord);
    				}
    			} else if(mediumRadio.isSelected()) {
    				if(currentWord.length() >= 6 && currentWord.length() <=8 ) {
    					filteredWords.add(currentWord);
    				} 
    			} else {
    				if(currentWord.length() >= 9) {
    					filteredWords.add(currentWord);
    				}
    			}
				i++;
    		}
    		
    		//validating the starting index
    		boolean validInput = true;
    		
    		if(startField.getText().isEmpty()) {
    			feedbackLabel.setText("Please enter a starting index");
    			validInput = false;
    		}
    		
    		//checks if starting index contains only digits
    		i = 0;

    		while(i < startField.getText().length() && validInput) {
    			
    			char c = startField.getText().charAt(i);
    			
    			if(Character.isDigit(c)) {

    			} else {
    				feedbackLabel.setText("Invalid input");
    				validInput = false;
    			}
    			
    			i++;
    		}
    		
    		//checks if words are empty
    		if(filteredWords.isEmpty()) {
    	        feedbackLabel.setText("No words available for this difficulty.");
    	        validInput = false;
    	    }
    		
    		//checks the length of the text
    		if(validInput) {

    		    if(startField.getText().length() > 10 ||
    		       (startField.getText().length() == 10 &&
    		       startField.getText().compareTo("2147483647") > 0)) {

    		        feedbackLabel.setText("Starting index is too large.");
    		        validInput = false;
    		    }

    		    //starts game if index is valid
    		    if(validInput) {

    		        int startIndex = Integer.parseInt(startField.getText());

    		        if(startIndex >= 0 && startIndex < filteredWords.size()) {

    		            currentIndex = startIndex;
    		            score = 0;
    		            checked = false;
    		            active = true;

    		            positionField.clear();

    		            feedbackLabel.setText("");

    		            updateView();

    		        } else {
    		            feedbackLabel.setText("Index is outside of available word list.");
    		        }
    		    }
    		}

    		updateButtons();
    }

    //checks whethee the caharcter is a vowl
    private void onCheck(ActionEvent e) {
    	
    			//does not check if game inactive or word already checked
    			if(!active || checked) {
    				return;
    			}
    	    			
    			//validates the character position
    			boolean validInput = true;
    			if(positionField.getText().isEmpty()) {
    				feedbackLabel.setText("Please enter a character position.");
    		        validInput = false;
    			}    			
    			
    			//makes sure position contains digits 
    			int i = 0;

    			    while(i < positionField.getText().length() && validInput) {

    			        char c = positionField.getText().charAt(i);

    			        if(Character.isDigit(c)) {

    			        } else {
    			            feedbackLabel.setText("Invalid input. Enter a nonnegative whole number.");
    			            validInput = false;
    			        }

    			        i++;
    			    }
    			    	//checks if entered position is too large
    			    if(validInput) {

    			        if(positionField.getText().length() > 10 ||
    			           (positionField.getText().length() == 10 &&
    			            positionField.getText().compareTo("2147483647") > 0)) {

    			            feedbackLabel.setText("Character position is too large.");
    			            validInput = false;
    			        }
    			    }

    			    //checks if selected character position is valid
    			    if(validInput) {

    			        int position = Integer.parseInt(positionField.getText());

    			        String currentWord = filteredWords.get(currentIndex);

    			        if(position >= 0 && position < currentWord.length()) {

    			            char selectedChar = currentWord.charAt(position);

    			            selectedChar = Character.toLowerCase(selectedChar);

    			            //determines whether selected character is a vowel
    			            if(selectedChar == 'a' || selectedChar == 'e' ||
    			               selectedChar == 'i' || selectedChar == 'o' ||
    			               selectedChar == 'u') {

    			                score += 10;

    			                //adds bonus points if option selected
    			                if(bonusBox.isSelected()) {
    			                    score += 5;
    			                }

    			                feedbackLabel.setText("Correct! You found a vowel.");

    			            } else {
    			                feedbackLabel.setText("That character is not a vowel.");
    			            }

    			            checked = true;

    			            scoreLabel.setText("Score: " + score);

    			        } else {
    			            feedbackLabel.setText("Character position is outside the word.");
    			        }
    			    }

    			    updateButtons();
    			}
    //moves to next word or ends game if no more words
    private void onNext(ActionEvent e) {
    	
    		if(!active || !checked) {
    			return;
    		}
    		
    		if(currentIndex < filteredWords.size() - 1) {
    			
    			currentIndex++;
    			
    			checked = false;
    			
    			positionField.clear();
    			
    			feedbackLabel.setText("");
    			
    			updateView();
    		} else {
    			
    			//ends game after final word
    			active = false;
    			
    			wordLabel.setText("Game Over!");
    			
    			feedbackLabel.setText("Final Score: " + score);
    			
    			hintLabel.setText("");
    			
    			updateButtons();
    		}
    }

    //reset game and restores default values
    private void onReset(ActionEvent e) {
    	
    		easyRadio.setSelected(true);
    		
    		hintBox.setSelected(true);
    		bonusBox.setSelected(false);
    		
    		currentIndex = -1;
    		score = 0;
    		checked = false;
    		active = false;
    		
    		filteredWords.clear();
    		
    		startField.setText("0");
    		positionField.clear();
    		
    		wordLabel.setText("");
    		progressLabel.setText("");
    		scoreLabel.setText("Score: 0");
    		feedbackLabel.setText("");
    		hintLabel.setText("");
    		
    		updateButtons();
    }
    //updates word length hint when checkbox changes
    private void onHintChanged(ActionEvent e) {
    	
    		updateHint();
    
    }
    //displays current word's length if hints are enabled
    private void updateHint() {
    	    
    		if(active && hintBox.isSelected()) {
    			
    			String currentWord = filteredWords.get(currentIndex);
    			
    			hintLabel.setText("Word Length: " + currentWord.length());
    		} else {
    			hintLabel.setText("");
    		}
    	}

    //updates the labels
    private void updateView() {
    	
    	 String currentWord = filteredWords.get(currentIndex);
    	 
    	    wordLabel.setText(currentWord);
    	    wordLabel.setFont(Font.font("Arial", 32));

    	    progressLabel.setText("Word " + (currentIndex + 1) +
    	            " of " + filteredWords.size());

    	    scoreLabel.setText("Score: " + score);

    	    updateHint();

    	    updateButtons();
    }

    //enables/disables buttons based on game status
    private void updateButtons() {
    
    	    checkButton.setDisable(!active || checked);

    	    nextButton.setDisable(!active || !checked);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
