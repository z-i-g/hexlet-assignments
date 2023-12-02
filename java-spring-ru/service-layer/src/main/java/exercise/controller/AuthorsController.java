package exercise.controller;

import exercise.dto.BooDto;
import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    // BEGIN
    @GetMapping(path = "")
    public List<BooDto> index() {
        return authorService.findAll();
    }

    @GetMapping(path = "/{id}")
    public BooDto show(@PathVariable long id) {
        return authorService.findById(id);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public BooDto create(@RequestBody AuthorCreateDTO authorCreateDTO) {
        return authorService.create(authorCreateDTO);
    }

    @PutMapping(path = "/{id}")
    public BooDto update(@PathVariable long id, @RequestBody AuthorUpdateDTO authorUpdateDTO) {
        return authorService.update(id, authorUpdateDTO);
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable long id) {
        authorService.deleteById(id);
    }
    // END
}
