package exercise.service;

import exercise.dto.BookDTO;
import exercise.dto.BookCreateDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> findAll() {
        var books = bookRepository.findAll();
        return books.stream()
                .map(p -> bookMapper.map(p))
                .toList();
    }

    public BookDTO findById(long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
        var bookDto = bookMapper.map(book);
        return bookDto;
    }

    public BookDTO create(BookCreateDTO bookCreateDTO) {
        var book = bookMapper.map(bookCreateDTO);
        var author = authorRepository.findById(bookCreateDTO.getAuthorId())
                .orElseThrow(() -> new ConstraintViolationException(new HashSet<>()));
        book.setAuthor(author);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public BookDTO update(long id, BookUpdateDTO bookUpdateDTO) {
        var book =  bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
        bookMapper.update(bookUpdateDTO, book);
        bookRepository.save(book);

        return bookMapper.map(book);
    }

    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
    // END
}
