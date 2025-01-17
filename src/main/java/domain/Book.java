package domain;

public class Book {
    public static int countId;
    private final int id;
    private final String title;
    private final String writer;
    private final int page;
    private BookState state;

    public Book(String title, String writer, int page) {
        this.id = countId++;
        this.title = title;
        this.writer = writer;
        this.page = page;
        this.state = BookState.AVAILABLE;
    }

    public Book(int id, String title, String writer, int page, BookState state) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.page = page;
        this.state = state;
    }

    public String toString() {
        return System.lineSeparator() + "도서번호 : " + id
                + System.lineSeparator() + "제목 : " + title
                + System.lineSeparator() + "작가 이름 : " + writer
                + System.lineSeparator() + "페이지 수: " + page + "페이지"
                + System.lineSeparator() + "상태 : " + state.getState()
                + System.lineSeparator() + System.lineSeparator()
                + "------------------------------";
    }

    public String fileLine() {
        return String.valueOf(id) + "," + title + ","
                + writer + "," + page + "," + state.getState() + "\n";
    }

    public int getId() {
        return id;
    }
    public BookState getState() {
        return state;
    }

    public void changeStateToAvailable() {
        this.state = BookState.AVAILABLE;
    }

    public void changeStateToRenting() {
        this.state = BookState.RENTING;
    }

    public void changeStateToOrganizing() {
        this.state = BookState.ORGANIZING;
    }

    public void changeStateToLost() {
        this.state = BookState.LOST;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isSameId(int id) {
        return id == this.id;
    }

    public boolean containsTitleWord(String titleWord) {
        return title.contains(titleWord);
    }
}
