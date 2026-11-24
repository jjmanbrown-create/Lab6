package classpractice;
import java.util.ArrayList;

public class Library {
	private ArrayList<Book> books;
		
	public Library() {
		books = new ArrayList<Book>();
	}
	
	public boolean addBook(Book book) {
		int i = 0;
		
		while(i < books.size()) {
			if(books.get(i).getTitle().equalsIgnoreCase(book.getTitle())) {
				return false;
			}
			i++;
		}
		
		books.add(book);
		return true;
	}
	
	public boolean removeBook(String title) {
		int i = 0;
		
		while(i < books.size()) {
			if(books.get(i).getTitle().equalsIgnoreCase(title)) {
			books.remove(i);
			return true;
		}
			i++;
	}	
		return false;
}
	
	public void searchBook(String keyword) {
		int i = 0;
		
		while(i < books.size()) {
			if(books.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase())) {
				System.out.println(books.get(i));
		}
		i++;
	}
}
	
	public boolean isAIBook(String title) {
	    int i = 0;

	    while(i < books.size()) {
	    	if(books.get(i).getTitle().toLowerCase().contains(title.toLowerCase())
	                && books.get(i).getCategory().equalsIgnoreCase("AI")) {
	            return true;
	        }
	        i++;
	    }

	    return false;
	}
	
	public boolean isOutOfStock(String title) {
	    int i = 0;

	    while(i < books.size()) {
	        if(books.get(i).getTitle().equalsIgnoreCase(title)
	                && books.get(i).getCopies() == 0) {
	            return true;
	        }
	        i++;
	    }

	    return false;
	}
	
	public boolean borrowBook(String title) {
		int i = 0;
		
		while(i < books.size()) {
			if(books.get(i).getTitle().equalsIgnoreCase(title)) {
				return books.get(i).borrowBook();
			}
			i++;
		}
		
		return false;
	}
	
	public boolean returnBook(String title) {
		int i = 0;
		
		while(i < books.size()) {
			if(books.get(i).getTitle().equalsIgnoreCase(title)) {
			 books.get(i).returnBook();
			 return true;
			}
			i++;
		}
		
		return false;
	}
	
	public void displayBooks() {
		
		int i = 0;
		
		while(i < books.size()) {
			System.out.println(books.get(i).getTitle());
			i++;
		}
		
	}
	
	public void displayStatistics() {
		int i = 0;
		int outOfStock = 0;
		int availableCopies = 0;
		int titleCount = books.size();
		
		while(i < books.size()) {
									
			if(books.get(i).getCopies() == 0) {
				outOfStock++;
			}
			
			availableCopies += books.get(i).getCopies();
			i++;
		}
		System.out.println("Title Count: " + titleCount);
		System.out.println("Available Copies: " + availableCopies);
		System.out.println("Out-of-Stock Titles: " + outOfStock);
	}
}