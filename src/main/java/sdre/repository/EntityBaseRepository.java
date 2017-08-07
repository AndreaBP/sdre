package sdre.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import sdre.domain.Entitybase;

public interface EntityBaseRepository extends PagingAndSortingRepository<Entitybase, Long> {
}
