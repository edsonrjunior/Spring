package br.com.fiap.library.controller;

import br.com.fiap.library.dto.AutorDTO;
import br.com.fiap.library.dto.BookDTO;
import br.com.fiap.library.dto.CreateBookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livros")
public class BookController {

    private List<BookDTO> bookDTOList = new ArrayList<BookDTO>();

    public BookController(){
        BookDTO dto1 = new BookDTO(1, "Senhor dos Aneis 1", 800, "168146316", ZonedDateTime.now().minusYears(40), new AutorDTO());
        BookDTO dto2 = new BookDTO(2, "Senhor dos Aneis 2", 800, "168146316", ZonedDateTime.now().minusYears(40), new AutorDTO());
        BookDTO dto3 = new BookDTO(3, "Senhor dos Aneis 3", 800, "168146316", ZonedDateTime.now().minusYears(40), new AutorDTO());

        bookDTOList.add(dto1);
        bookDTOList.add(dto2);
        bookDTOList.add(dto3);
    }

    @GetMapping
    public List<BookDTO> getAll(@RequestParam("title") String livro){


        return  bookDTOList.stream()
                .filter(bookDTO -> livro == null || bookDTO.getTitulo().startsWith(livro))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public BookDTO findById(@PathVariable Integer id) {
        return bookDTOList.stream()
                .filter(bookDTO -> bookDTO.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody CreateBookDTO createBookDTO){
        BookDTO bookDTO = new BookDTO(createBookDTO, bookDTOList.size() + 1);
        bookDTOList.add(bookDTO);

        return bookDTO;
    }

    @PutMapping("{id}")
    public BookDTO update(@PathVariable Integer id, @RequestBody CreateBookDTO createBookDTO) {
        BookDTO bookDTO = findById(id);
        bookDTO.setDataLacamento(createBookDTO.getDataLacamento());
        bookDTO.setISBN(createBookDTO.getISBN());
        bookDTO.setQuantidadeDePaginas(createBookDTO.getQuantidadeDePaginas());
        bookDTO.setTitulo(createBookDTO.getTitulo());
        return bookDTO;
    }

    @PatchMapping("{id}")
    public BookDTO updatePatch(@PathVariable Integer id, @RequestBody CreateBookDTO createBookDTO) {

        BookDTO bookDTO = findById(id);
        bookDTO.setDataLacamento(createBookDTO.getDataLacamento());
        bookDTO.setISBN(createBookDTO.getISBN());
        bookDTO.setQuantidadeDePaginas(createBookDTO.getQuantidadeDePaginas());
        bookDTO.setTitulo(createBookDTO.getTitulo());
        return bookDTO;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        BookDTO bookDTO = findById(id);
        bookDTOList.remove(bookDTO);
    }
}
