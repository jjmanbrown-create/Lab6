package classpractice;
/*
* File: Task3.java
* Author: Jalen Brown
* Course: CISC230
* Lab: Lab6
* Date: 11/11/2026
* 
* Description:
* This program creates an interactive Shape Designer.
* Allows the user to select circles/rectangles and enter 
* different dimensions. Once program validates the user input
* it then calculates total area and perimeter/circumference.
* User can use buttons for different functions as well such as
* grid lines or hiding the calculated results.
*/
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import javafx.scene.shape.Line;
import java.util.Scanner;

/**
 * CISC 230 - Lab 6 - Task 3: Interactive Shape Designer
 * Student name: ____________Jalen Brown______________
 *
 * This file compiles and opens a WINDOW with empty sections.
 * YOU must create and position the GUI controls, attach event handlers,
 * validate input, use MyCircle/MyRectangle, and draw shapes.
 */
public class Task3 extends Application {
	
	//declaring instance variables
	private RadioButton circleRadio, rectangleRadio;
	private TextField radiusField, widthField, heightField, quantityField;
	private CheckBox showAreaBox, showBoundaryBox, showGridBox;
	private Button calculateButton, clearButton;
	private Label areaLabel, boundaryLabel, messageLabel;
	private ToggleGroup shapeGroup;
    private Pane drawingPane;

    public void start(Stage primaryStage) {
        
    	//creating borderpane
    	BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        //creating title for window
        Label title = new Label("Interactive Shape Designer");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        root.setTop(title);

        //creating gridpane for different inputs
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(12);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(12));
        inputGrid.setPrefWidth(350);

        //creating vbox to hold all controls
        VBox controls = new VBox(14);
        controls.setAlignment(Pos.TOP_LEFT);
        controls.setPadding(new Insets(10));
        controls.setPrefWidth(370);
        
        //creating radio buttons
        circleRadio = new RadioButton("Circle");
        rectangleRadio = new RadioButton("Rectangle");
        
		//creating toggle group for switching between buttons
        shapeGroup = new ToggleGroup();
		circleRadio.setToggleGroup(shapeGroup);
		rectangleRadio.setToggleGroup(shapeGroup);
		
		//setting circle radio button as default
		circleRadio.setSelected(true);
        
		//creating hbox for all radiobuttons
		HBox radioButtons = new HBox(15);
		radioButtons.getChildren().addAll(circleRadio, rectangleRadio);
		
        //adding radio buttons to controls vbox
		controls.getChildren().add(radioButtons);
        
        //creating textfields and setting the columns
		radiusField = new TextField("20");
        radiusField.setPrefColumnCount(8);
        
        widthField = new TextField("50");
        widthField.setPrefColumnCount(8);

        heightField = new TextField("40");
        heightField.setPrefColumnCount(8);

        quantityField = new TextField("3");
        quantityField.setPrefColumnCount(8);
        
        //adding labels and fields to different rows in inputGrid
        inputGrid.addRow(0, new Label("Radius: "), radiusField);
        inputGrid.addRow(1, new Label("Width: "),widthField);
        inputGrid.addRow(2, new Label("Height: "),heightField);
        inputGrid.addRow(3, new Label("Quantity(1-10) : "),quantityField);
        
        //adding input grid to controls vbox
        controls.getChildren().add(inputGrid);

        //creating checkboxes
        showAreaBox = new CheckBox("Show Area");
        showAreaBox.setSelected(true);
        
        showBoundaryBox = new CheckBox("Show Perimeter / Circumference");
        showBoundaryBox.setSelected(true);
        
        showGridBox = new CheckBox("Show Grid Lines");
        showGridBox.setSelected(false);
        
        //adding check boxes to vbox
        VBox vbox = new VBox(8);
        vbox.getChildren().addAll(showAreaBox, showBoundaryBox, showGridBox);
        
        //adding vbox to controls vbox
        controls.getChildren().add(vbox);
        
        //creating buttons
        calculateButton = new Button("Calculate & Draw");
        clearButton = new Button("Clear");
        
        //creating hbox and adding buttons
        HBox buttonBox = new HBox(12);
        buttonBox.getChildren().addAll(calculateButton,clearButton);
        
        //adding hbox to vbox
        controls.getChildren().add(buttonBox);
        
        //setting buttons to methods
        calculateButton.setOnAction(this::onCalculate);
        clearButton.setOnAction(this::onClear);
        
        //creating labels
        areaLabel = new Label("Area: ");
        boundaryLabel = new Label("Perimeter/Circumference: ");
        messageLabel = new Label("Select a shape, then calculate and draw:");
        
        //adding labels to vbox
        controls.getChildren().addAll(areaLabel, boundaryLabel, messageLabel);

        //creating new pane 
        drawingPane = new Pane();
        drawingPane.setPrefSize(390, 330);
        drawingPane.setMinSize(390, 330);
        drawingPane.setStyle("-fx-background-color: #f5f7fb; "
                + "-fx-border-color: #aab5c4; -fx-border-width: 1;");

        //creating new vbox and adding pane
        VBox preview = new VBox(8);
        preview.getChildren().addAll(new Label("Shape Preview"), drawingPane);
        preview.setPadding(new Insets(10));

        root.setLeft(controls);
        root.setCenter(preview);
        
        // When Circle is selected, enable the radius field and disable rectangle fields.
        circleRadio.setOnAction(e -> {
        		radiusField.setDisable(false);
            widthField.setDisable(true);
            heightField.setDisable(true);
        });
        // When Rectangle is selected, enable width and height and disable radius.
        rectangleRadio.setOnAction(e -> {
        		radiusField.setDisable(true);
            widthField.setDisable(false);
            heightField.setDisable(false);        	
        });
        // Setting the starting field availability to match the selected circle option.
        radiusField.setDisable(false);
        widthField.setDisable(true);
        heightField.setDisable(true);

        // Creating the scene, setting up the stage, and displaying the window.
        Scene scene = new Scene(root, 830, 480);
        primaryStage.setTitle("Shape Designer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Runs when the user clicks Calculate & Draw.
    private void onCalculate(ActionEvent e) {
    	 String rField = radiusField.getText().trim();
    	    String wField = widthField.getText().trim();
    	    String hField = heightField.getText().trim();
    	    String qField = quantityField.getText().trim();

    	    if (qField.isEmpty() ||
    	        (circleRadio.isSelected() && rField.isEmpty()) ||
    	        (!circleRadio.isSelected() && (wField.isEmpty() || hField.isEmpty()))) {

    	        messageLabel.setText("Please enter values in all required fields.");
    	    }
	        // Variables that will hold the input values after they are validated.
    	    else {

    	        double radius = 0;
    	        double width = 0;
    	        double height = 0;
    	        int quantity = 0;

    	        // Using Scanners to check that the entered dimensions are numeric values.
    	        Scanner rScanner = new Scanner(rField);
    	        boolean validRadius = rScanner.hasNextDouble();
    	        if (validRadius) {
    	            radius = rScanner.nextDouble();
    	            validRadius = !rScanner.hasNext() && Double.isFinite(radius);
    	        }
    	        rScanner.close();

    	        // Checking the width input.
    	        Scanner wScanner = new Scanner(wField);
    	        boolean validWidth = wScanner.hasNextDouble();
    	        if (validWidth) {
    	            width = wScanner.nextDouble();
    	            validWidth = !wScanner.hasNext() && Double.isFinite(width);
    	        }
    	        wScanner.close();

    	        // Checking the height input.
    	        Scanner hScanner = new Scanner(hField);
    	        boolean validHeight = hScanner.hasNextDouble();
    	        if (validHeight) {
    	            height = hScanner.nextDouble();
    	            validHeight = !hScanner.hasNext() && Double.isFinite(height);
    	        }
    	        hScanner.close();

    	        // Checking that quantity is an integer.
    	        Scanner qScanner = new Scanner(qField);
    	        boolean validQuantity = qScanner.hasNextInt();
    	        if (validQuantity) {
    	            quantity = qScanner.nextInt();
    	            validQuantity = !qScanner.hasNext();
    	        }
    	        qScanner.close();

    	        // Displaying an error if an input is not a valid number.
    	        if (!validQuantity ||
    	            (circleRadio.isSelected() && !validRadius) ||
    	            (!circleRadio.isSelected() && (!validWidth || !validHeight))) {

    	            messageLabel.setText("Please enter numeric values.");
    	        }
    	        // Making sure dimensions are positive and quantity is between 1 and 10.
    	        else if ((circleRadio.isSelected() && radius <= 0) ||
    	                 (!circleRadio.isSelected() && (width <= 0 || height <= 0)) ||
    	                 quantity < 1 || quantity > 10) {

    	            messageLabel.setText("Invalid input. Check dimensions and quantity.");
    	        }
    	        else {

    	            // Variables to store the area and perimeter/circumference of one shape.
    	        	double area = 0;
    	            double boundary = 0;

    	            // Creating the selected shape object and getting its calculated values.
    	            if (circleRadio.isSelected()) {
    	                MyCircle circle = new MyCircle(radius);
    	                area = circle.getArea();
    	                boundary = circle.getCircumference();
    	            }
    	            else {
    	                MyRectangle rectangle = new MyRectangle(width, height);
    	                area = rectangle.getArea();
    	                boundary = rectangle.getPerimeter();
    	            }

    	            // Adding the area and boundary values for the requested number of shapes.
    	            double totalArea = 0;
    	            double totalBoundary = 0;
    	            int count = 0;

    	            // Repeating the addition until the total reaches the requested quantity.
    	            while (count < quantity) {
    	                totalArea += area;
    	                totalBoundary += boundary;
    	                count++;
    	            }
    			// Removing the previous drawing before displaying the new shapes.
				drawingPane.getChildren().clear();
				
				// Drawing grid lines only if the user checked Show Grid Lines.
				if (showGridBox.isSelected()) {
				    int position = 0;
				
				    while (position <= 390) {
				        Line verticalLine = new Line(position, 0, position, 330);
				        verticalLine.setStroke(Color.LIGHTGRAY);
				        drawingPane.getChildren().add(verticalLine);
				        position += 20;
				    }
				
				    position = 0;
				    while (position <= 330) {
				        Line horizontalLine = new Line(0, position, 390, position);
				        horizontalLine.setStroke(Color.LIGHTGRAY);
				        drawingPane.getChildren().add(horizontalLine);
				        position += 20;
				    }
				}

				// Starting position and counter for drawing the requested number of shapes.
				int drawingCount = 0;
				int x = 20;
				int y = 20;
				
				// Drawing one shape at a time until the requested quantity is reached.
				while (drawingCount < quantity) {
				    if (circleRadio.isSelected()) {
				        Circle circle = new Circle(x + 20, y + 20, 20);
				        circle.setFill(Color.LIGHTBLUE);
				        circle.setStroke(Color.BLACK);
				        drawingPane.getChildren().add(circle);
				    } else {
				        Rectangle rectangle = new Rectangle(x, y, 40, 40);
				        rectangle.setFill(Color.LIGHTGREEN);
				        rectangle.setStroke(Color.BLACK);
				        drawingPane.getChildren().add(rectangle);
				    }
				
				    x += 70;
				
				    if (x > 300) {
				        x = 20;
				        y += 70;
				    }
				
				    drawingCount++;
				}

	            // Formatting the displayed results to two decimal places.    
				DecimalFormat df = new DecimalFormat("0.00");

	            // Showing or hiding the total area based on the checkbox selection.    
				if (showAreaBox.isSelected()) {
    	                areaLabel.setText("Area: " + df.format(totalArea));
    	            }
    	            else {
    	                areaLabel.setText("Area: ");
    	            }
	            // Showing or hiding the total perimeter/circumference.
    	            if (showBoundaryBox.isSelected()) {
    	                boundaryLabel.setText(
    	                    "Perimeter/Circumference: " + df.format(totalBoundary)
    	                );
    	            }
    	            else {
    	                boundaryLabel.setText("Perimeter/Circumference: ");
    	            }

    	            messageLabel.setText("Successfully created " + quantity + " shapes.");
    	        }
    	    }
    	}

    // Runs when the user clicks Clear and restores the program to its starting state.
    private void onClear(ActionEvent e) {
       
    	// Restoring the default values in all text fields.
    	radiusField.setText("20");
    	widthField.setText("50"); 
    	heightField.setText("40");
    	quantityField.setText("3");
    	
    	 circleRadio.setSelected(true);

    	    // Restore CheckBox defaults
    	    showAreaBox.setSelected(true);
    	    showBoundaryBox.setSelected(true);
    	    showGridBox.setSelected(false);

    	    // Restore enabled/disabled fields
    	    radiusField.setDisable(false);
    	    widthField.setDisable(true);
    	    heightField.setDisable(true);

    	    // Reset result and status labels
    	    areaLabel.setText("Area: ");
    	    boundaryLabel.setText("Perimeter/Circumference: ");
    	    messageLabel.setText("Select a shape, then calculate and draw:");
    	    // Clear drawing
    	    drawingPane.getChildren().clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
