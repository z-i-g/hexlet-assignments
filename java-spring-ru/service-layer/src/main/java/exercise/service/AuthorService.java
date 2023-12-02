package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.BooDto;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public List<BooDto> findAll() {
        var cars = authorRepository.findAll();
        return cars.stream()
                .map(p -> authorMapper.map(p))
                .toList();
    }

    public BooDto findById(long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));
        var authorDTO = authorMapper.map(author);
        return authorDTO;
    }

    public BooDto create(AuthorCreateDTO authorCreateDTO) {
        var author = authorMapper.map(authorCreateDTO);
        authorRepository.save(author);
        return authorMapper.map(author);
    }

    public BooDto update(long id, AuthorUpdateDTO authorUpdateDTO) {
        var author =  authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));

//        var category =  authorRepository.findById(authorUpdateDTO.getCategoryId().get())
//                .orElseThrow(() -> new ConstraintViolationException(new HashSet<>()));

//        author.setCategory(category);
        authorMapper.update(authorUpdateDTO, author);
        authorRepository.save(author);

        return authorMapper.map(author);
    }

    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }
    // END
}
