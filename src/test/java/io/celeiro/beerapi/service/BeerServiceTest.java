package io.celeiro.beerapi.service;

import io.celeiro.beerapi.builder.BeerDTOBuilder;
import io.celeiro.beerapi.dto.BeerDTO;
import io.celeiro.beerapi.entities.Beer;
import io.celeiro.beerapi.exception.BeerAlreadyRegisteredException;
import io.celeiro.beerapi.exception.BeerNotFoundException;
import io.celeiro.beerapi.mapper.BeerMapper;
import io.celeiro.beerapi.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private BeerRepository beerRepository;

    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    @Test
    void whenBeerInformedThenItShouldBeInserted() throws BeerAlreadyRegisteredException {
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedSavedBeer =  beerMapper.toModel(expectedBeerDTO);

        //when
        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());
        when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

        //then
        BeerDTO createdBeerDTO = beerService.insert(expectedBeerDTO);

        assertThat(createdBeerDTO.getId(), is(equalTo(expectedBeerDTO.getId())));
        assertThat(createdBeerDTO.getName(), is(equalTo(expectedBeerDTO.getName())));
        assertThat(createdBeerDTO.getQuantity(), is(equalTo(expectedBeerDTO.getQuantity())));
    }

    @Test
    void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrow() throws BeerAlreadyRegisteredException {
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer duplicatedBeer =  beerMapper.toModel(expectedBeerDTO);

        //when
        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.of(duplicatedBeer));

        //then
        assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.insert(expectedBeerDTO)); ;
    }

    @Test
    void whenValidBeerNameIsGivenThenReturnABeer() throws BeerNotFoundException {
        //given
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedFoundBeer =  beerMapper.toModel(expectedFoundBeerDTO);

        //when
        when(beerRepository.findByName(expectedFoundBeer.getName())).thenReturn(Optional.of(expectedFoundBeer));

        //then
        BeerDTO foundBeerDTO = beerService.findByName(expectedFoundBeerDTO.getName());

        assertThat(foundBeerDTO, is(equalTo(expectedFoundBeerDTO)));
    }

    @Test
    void whenNoRegisteredBeerNameIsGivenValidThenThrowException() throws BeerNotFoundException {
        //given
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        //when
        when(beerRepository.findByName(expectedFoundBeerDTO.getName())).thenReturn(Optional.empty());

        //then
        assertThrows(BeerNotFoundException.class, () -> beerService.findByName(expectedFoundBeerDTO.getName())); ;
    }

    @Test
    void whenListBeerIsCalledThenReturnAListOfBeers() {
        //given
        BeerDTO expectedFoundBeersDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedFoundBeers =  beerMapper.toModel(expectedFoundBeersDTO);

        //when
        when(beerRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundBeers));

        //then
        List<BeerDTO> foundBeersDTO  = beerService.listAll();

        assertThat(foundBeersDTO, is(not(empty())));
        assertThat(foundBeersDTO.get(0), is(equalTo(expectedFoundBeersDTO)));
    }
}
