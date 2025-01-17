package service;

import domain.Book;
import domain.BookState;
import message.ExecuteMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.MemoryRepository;
import repository.Repository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceTest {

    Repository repository = new MemoryRepository();
    Service service = new Service(repository);

    @BeforeEach
    void beforeEach() {
        List<Book> books = repository.getList();
        books.add(new Book(1, "rolling girl", "miku", 543, BookState.AVAILABLE));
        books.add(new Book(2, "yellow", "coldplay", 45, BookState.RENTING));
        books.add(new Book(3, "1998", "라쿠나", 4543, BookState.LOST));
    }

    @AfterEach
    void AfterEach() {
        List<Book> books = repository.getList();
        books.clear();
    }

    @Test
    @DisplayName("도서 등록")
    void register() {
        List<Book> books = service.getList();
        service.register(new Book("welcome to the black parade", "MCR", 324));

        assertThat(4).isEqualTo(books.size());
    }

    @Test
    void getList() {
        List<Book> books = service.getList();

        Assertions.assertThat(3).isEqualTo(books.size());
    }

    @Test
    @DisplayName("도서 검색")
    void search() {
        List<Book> selectedBooks = service.search("ll");

        assertThat(2).isEqualTo(selectedBooks.size());
        assertThat("rolling girl").isEqualTo(selectedBooks.get(0).getTitle());
        assertThat("yellow").isEqualTo(selectedBooks.get(1).getTitle());
    }

    @Test
    @DisplayName("도서 대여 : 대여 가능")
    void rentalA() {
        ExecuteMessage message = service.rental(1);

        assertThat(message).isEqualTo(ExecuteMessage.RENTAL_AVAILABLE);
    }

    @Test
    @DisplayName("도서 대여 : 대여중")
    void rentalR() {
        ExecuteMessage message = service.rental(2);

        assertThat(message).isEqualTo(ExecuteMessage.RENTAL_RENTING);
    }

    @Test
    @DisplayName("도서 대여 : 분실됨")
    void rentalL() {
        ExecuteMessage message = service.rental(3);

        assertThat(message).isEqualTo(ExecuteMessage.RENTAL_LOST);
    }

    @Test
    @DisplayName("도서 대여 : 도서 정리중")
    void rentalO() {
        List<Book> books = service.getList();
        books.get(1).changeStateToOrganizing();
        ExecuteMessage message = service.rental(2);

        assertThat(message).isEqualTo(ExecuteMessage.RENTAL_ORGANIZING);
    }

    @Test
    @DisplayName("도서 대여 : id 없음")
    void rentalN() {
        ExecuteMessage message = service.rental(10);

        assertThat(message).isEqualTo(ExecuteMessage.NOT_EXIST);
    }

    @Test
    @DisplayName("도서 반납 : 대여 가능")
    void returnBookA() {
        ExecuteMessage message = service.returnBook(1);

        assertThat(message).isEqualTo(ExecuteMessage.RETURN_AVAILABLE);
    }

    @Test
    @DisplayName("도서 반납 : 대여중")
    void returnBookR() {
        ExecuteMessage message = service.returnBook(2);

        assertThat(message).isEqualTo(ExecuteMessage.RETURN_COMPLETE);
    }

    @Test
    @DisplayName("도서 반납 : 분실됨")
    void returnBookL() {
        ExecuteMessage message = service.returnBook(3);

        assertThat(message).isEqualTo(ExecuteMessage.RETURN_COMPLETE);
    }

    @Test
    @DisplayName("도서 반납 : 도서 정리중")
    void returnBookO() {
        List<Book> books = service.getList();
        books.get(1).changeStateToOrganizing();

        ExecuteMessage message = service.returnBook(2);

        assertThat(message).isEqualTo(ExecuteMessage.RETURN_IMPOSSIBLE);
    }

    @Test
    @DisplayName("도서 반납 : id 없음")
    void returnBookN() {
        ExecuteMessage message = service.returnBook(10);

        assertThat(message).isEqualTo(ExecuteMessage.NOT_EXIST);
    }

    @Test
    @DisplayName("도서 분실: 대여 가능")
    void lostBookA() {
        ExecuteMessage message = service.lostBook(1);

        assertThat(message).isEqualTo(ExecuteMessage.LOST_IMPOSSIBLE);
    }

    @Test
    @DisplayName("도서 분실: 대여중")
    void lostBookR() {
        ExecuteMessage message = service.lostBook(2);

        assertThat(message).isEqualTo(ExecuteMessage.LOST_COMPLETE);
    }

    @Test
    @DisplayName("도서 분실: 분실됨")
    void lostBookL() {
        ExecuteMessage message = service.lostBook(3);

        assertThat(message).isEqualTo(ExecuteMessage.LOST_ALREADY);
    }

    @Test
    @DisplayName("도서 분실: 도서 정리중")
    void lostBookO() {
        List<Book> books = service.getList();
        books.get(1).changeStateToOrganizing();

        ExecuteMessage message = service.lostBook(2);

        assertThat(message).isEqualTo(ExecuteMessage.LOST_IMPOSSIBLE);
    }

    @Test
    @DisplayName("도서 분실: id 없음")
    void lostBookN() {
        ExecuteMessage message = service.lostBook(10);

        assertThat(message).isEqualTo(ExecuteMessage.NOT_EXIST);
    }

    @Test
    @DisplayName("도서 삭제 : 성공")
    void deleteBookS() {
        ExecuteMessage message = service.deleteBook(1);

        assertThat(message).isEqualTo(ExecuteMessage.DELETE_COMPLETE);
    }

    @Test
    @DisplayName("도서 삭제 : 실패")
    void deleteBookF() {
        ExecuteMessage message = service.deleteBook(10);

        assertThat(message).isEqualTo(ExecuteMessage.NOT_EXIST);
    }
}
