package classpractice;
/*
* File: Task1.java
* Author: Jalen Brown
* Course: CISC230
* Lab: Lab6
* Date: 11/11/2026
* 
* Description:
* This program creates a Campus Library system that reads book
* information from a file allowing users to manage the library.
* Management includes various features such as searching, borrowing,
* returning and more. Also includes mission system where users complete tasks
* to earn points.
*/
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** CISC 230 Lab 6 — Task 1. Student: _____Jalen_____ */
public class Task1 {
    public static void main(String[] args) throws IOException {
        
        //calling the library class
    		Library library = new Library();
        //creating arraylist for borrowed transactions
    		ArrayList<String> borrowedTransactions = new ArrayList<String>();
        
    		//creating scanner objects
    		Scanner input = new Scanner(System.in);
        Scanner scan = new Scanner(new File("books.txt"));
        
		//reads book information and adds valid books to library
        while(scan.hasNextLine()) {
			
			String line = scan.nextLine();
			
			//condition that checks file contains the required fields
			if(!line.trim().isEmpty()) {
				String[] fields = line.split(",");
				if(fields.length == 4) {
					String title = fields[0].trim();
					String author = fields[1].trim();
					String category = fields[2].trim();
					String copies = fields[3].trim();
					
					//checks if all required book information is present
					if(!title.isEmpty() && !author.isEmpty() && !category.isEmpty() && !copies.isEmpty()) {
						
						int i = 0;
						boolean validCopies = true;
						
						//checks available copies
						while(i < copies.length()) {
							
							if(!(copies.charAt(i) >= '0') || !(copies.charAt(i) <= '9')) {
								validCopies = false;
							}
							i++;
						}
						
						//creates and adds book if number of copies is valid
						if(validCopies) {
							int availableCopies = Integer.parseInt(copies);
							
							if(availableCopies >= 0) {
								Book book = new Book(title, author, category, availableCopies);
								library.addBook(book);
							}								
							
						}
					}
				}		
			}
		}
		
		scan.close();
        // TODO: Use a while loop for the menu.
		
		//initializing menu items, points, and completion status
		int menuInput = 0;
		int gamePoints = 0;
		boolean mission1 = false;
		boolean mission2 = false;
		boolean mission3 = false;
		boolean mission4 = false;
		boolean mission5 = false;

		//loop that displays menu until user selects exit
		while(menuInput != 9) {
			displayMenu();
			
			System.out.println("Enter your choice: ");
			menuInput = input.nextInt();
			input.nextLine();
			
			if(menuInput == 1) {
				//displays books in library
				library.displayBooks();
			} else if(menuInput == 2) {
				//searches for a book
				System.out.println("What book are you looking for: ");
				String bookSearch = input.nextLine();
				
				library.searchBook(bookSearch);
				
			    if(library.isAIBook(bookSearch) && !mission1) {
			        mission1 = true;
			        gamePoints += 20;
			        System.out.println("Mission 1 completed! +20 points!");
			    }
								
			 } else if(menuInput == 3) {
			    //borrows a book and tracks successful borrowing transactions
				 System.out.println("What book are you borrowing: ");
			    String bookBorrow = input.nextLine();

			    if(library.borrowBook(bookBorrow)) {
			        borrowedTransactions.add(bookBorrow);
			        System.out.println("Book successfully borrowed!");

			        if(!mission2) {
			            mission2 = true;
			            gamePoints += 20;
			        }
			    } else {
			        System.out.println("Book not found or no copies available.");
			    }

			} else if(menuInput == 4) {
				//checks if the book was borrowed
				System.out.println("What book are you returning: ");
			    String bookReturn = input.nextLine();

			    int i = 0;
			    boolean borrowedDuringRun = false;

			    while(i < borrowedTransactions.size()) {
			        if(borrowedTransactions.get(i).equalsIgnoreCase(bookReturn)) {
			            borrowedDuringRun = true;
			            i = borrowedTransactions.size();
			        } else {
			            i++;
			        }
			    }

			    if(borrowedDuringRun && library.returnBook(bookReturn)) {
			        System.out.println("Book successfully returned!");

			        //removing the returned book from transaction list
			        i = 0;
			        while(i < borrowedTransactions.size()) {
			            if(borrowedTransactions.get(i).equalsIgnoreCase(bookReturn)) {
			                borrowedTransactions.remove(i);
			                i = borrowedTransactions.size();
			            } else {
			                i++;
			            }
			        }

			        if(!mission3) {
			            mission3 = true;
			            gamePoints += 20;
			        }
			    } else {
			        System.out.println("Book was not borrowed during this program run.");
			    }
			    
			} else if(menuInput == 5) {
				//collects book information and adds it if valid
				System.out.println("Enter book title: ");
				String title = input.nextLine();
				
				System.out.println("Enter book author: ");
				String author = input.nextLine();
				
				System.out.println("Enter book category: ");
				String category = input.nextLine();
				
				System.out.println("Enter available copies: ");
				int availableCopies = input.nextInt();
				input.nextLine();
				
				if(availableCopies >= 0) {
					Book book = new Book(title, author, category, availableCopies);
					
					if(library.addBook(book)) {
						System.out.println("Book successfully added!");
						
						if(!mission4) {
							mission4 = true;
							gamePoints += 20;
						}
					} else {
						System.out.println("A book with that title already exists.");
						}
					
					} else {
						System.out.println("Copies cannot be negative.");
					}
				
			}else if(menuInput == 6) {
				//removes book from library using its title
				System.out.println("What book are you removing: ");
				String bookRemove = input.nextLine();
				
				if(library.removeBook(bookRemove)) {
					System.out.println("Book successfully removed!");
				} else {
					System.out.println("Book not found.");
				}
				
			} else if(menuInput == 7) {
				//displaying library statistics
				library.displayStatistics();
				
			} else if(menuInput == 8) {
				//checks mission completion and displays user's progress/points
				if(!mission5) {
				    System.out.println("Enter the title of a book with zero available copies: ");
				    String outOfStockBook = input.nextLine();

				    if(library.isOutOfStock(outOfStockBook)) {
				        mission5 = true;
				        gamePoints += 20;
				        System.out.println("Mission 5 completed! +20 points!");
				    } else {
				        System.out.println("Incorrect! That book is not out of stock.");
				    }
				}
				
				System.out.println("\n===== MISSION PROGRESS =====");

			    System.out.println("1. Book Detective: " + mission1);
			    System.out.println("2. First Borrow: " + mission2);
			    System.out.println("3. Safe Return: " + mission3);
			    System.out.println("4. Collection Expansion: " + mission4);
			    System.out.println("5. Inventory Inspector: " + mission5);

			    System.out.println("Total Game Points: " + gamePoints + "/100");
			} else if(menuInput == 9) {
				//exits program
				System.out.println("Thank you for playing.");
			} else {
				System.out.println("Invalid choice. Please select 1-9");
			}
		}
    }
    	//displays library menu options
    public static void displayMenu() {
        System.out.println("\n===== CAMPUS LIBRARY RESCUE =====");
        System.out.println("1. Display All Books");
        System.out.println("2. Search for a Book");
        System.out.println("3. Borrow a Book");
        System.out.println("4. Return a Book");
        System.out.println("5. Add a New Book");
        System.out.println("6. Remove a Book");
        System.out.println("7. Library Statistics");
        System.out.println("8. View Mission Progress");
        System.out.println("9. Exit");
    }
}
