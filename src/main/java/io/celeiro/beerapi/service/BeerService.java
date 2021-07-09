package io.celeiro.beerapi.service;

import io.celeiro.beerapi.dto.BeerDTO;
import io.celeiro.beerapi.entities.Beer;
import io.celeiro.beerapi.mapper.BeerMapper;
import io.celeiro.beerapi.repositories.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public List<BeerDTO> listAll() {
        List<Beer> beers =  beerRepository.findAll();

        return beers.stream()
             .map(beerMapper::toDTO)
             .collect(Collectors.toList());
    }
}
