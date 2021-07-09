package io.celeiro.beerapi.repositories;

import io.celeiro.beerapi.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, Long> {
}
