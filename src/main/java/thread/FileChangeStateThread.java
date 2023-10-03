package thread;

import domain.BookState;
import exception.ThreadInterruptException;
import repository.Book;

import java.io.File;
import java.util.List;

import static repository.FileRepository.updateFile;

public class FileChangeStateThread extends Thread {
    private Book book;
    private File file;
    private List<Book> books;
    public FileChangeStateThread(Book book, File file, List<Book> books) {
        this.book = book;
        this.file = file;
        this.books = books;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(300000);
            book.setState(BookState.AVAILABLE);
            updateFile(books, file);
        } catch (InterruptedException e) {
            throw new ThreadInterruptException();
        }
    }
}