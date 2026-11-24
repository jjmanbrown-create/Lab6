package classpractice;

public class Book {
	
	private String title;
	private String author;
	private String category;
	private int availableCopies;
	
	public Book(String bTitle, String bookAuthor, String bCategory, int copies) {
		title = bTitle;
		author = bookAuthor;
		category = bCategory;
		
		if(copies >= 0) {
		    availableCopies = copies;
		} else {
		    availableCopies = 0;
		}	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getCategory() {
		return category;
	}
	
	public int getCopies() {
		return availableCopies;
	}
	
	public boolean borrowBook() {
		if(availableCopies >= 1) {
			availableCopies--;			
			return true;
		}
		return false;
	}
	
	public void returnBook() {
		availableCopies++;
	}
	
	public String toString() {
		return title + " | " + author + " | " + category
				+ " | Copies: " + availableCopies; 
	}
}