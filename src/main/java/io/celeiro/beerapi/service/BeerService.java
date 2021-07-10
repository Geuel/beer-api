package io.celeiro.beerapi.service;

import io.celeiro.beerapi.dto.BeerDTO;
import io.celeiro.beerapi.entities.Beer;
import io.celeiro.beerapi.exception.BeerNotFoundException;
import io.celeiro.beerapi.mapper.BeerMapper;
import io.celeiro.beerapi.repositories.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper = BeerMapper.INSTANCE;

    public List<BeerDTO> listAll() {
        List<Beer> beers =  beerRepository.findAll();

        return beers.stream()
             .map(beerMapper::toDTO)
             .collect(Collectors.toList());
    }

    public BeerDTO findById(Long id) throws BeerNotFoundException {
        Beer beer = verifyIfExists(id);

        return beerMapper.toDTO(beer);
    }

    public BeerDTO insert(BeerDTO beerDTO) {
        Beer beerToSave = beerMapper.toModel(beerDTO);
        Beer savedBeer = beerRepository.save(beerToSave);
        return beerMapper.toDTO(savedBeer);
    }

    public BeerDTO update(Long id, BeerDTO beerDTO) throws BeerNotFoundException {
        verifyIfExists(id);
        Beer beerToSave = beerMapper.toModel(beerDTO);
        Beer savedBeer = beerRepository.save(beerToSave);
        return beerMapper.toDTO(savedBeer);
    }

    public void delete(Long id) throws BeerNotFoundException {
        verifyIfExists(id);
        beerRepository.deleteById(id);
    }

    private Beer verifyIfExists(Long id) throws BeerNotFoundException {
        return beerRepository.findById(id)
                .orElseThrow(() -> new BeerNotFoundException(id));
    }



}
