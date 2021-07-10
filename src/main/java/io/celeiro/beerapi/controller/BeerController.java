package io.celeiro.beerapi.controller;

import io.celeiro.beerapi.dto.BeerDTO;
import io.celeiro.beerapi.exception.BeerNotFoundException;
import io.celeiro.beerapi.service.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/beers")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerController {

    private BeerService beerService;

    @GetMapping
    public List<BeerDTO> listAll() {
        return beerService.listAll();
    }

    @GetMapping("/{id}")
    public BeerDTO findById(@PathVariable Long id) throws BeerNotFoundException {
        return beerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO insert(@RequestBody @Valid BeerDTO beerDTO) {
        return beerService.insert(beerDTO);
    }

    @PutMapping("/{id}")
    public BeerDTO update(@PathVariable Long id, @RequestBody BeerDTO beerDTO) throws BeerNotFoundException {
        return beerService.update(id, beerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws BeerNotFoundException {
        beerService.delete(id);
    }
}
