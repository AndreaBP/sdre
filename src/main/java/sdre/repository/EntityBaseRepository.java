package sdre.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import sdre.domain.EntityBase;

public interface EntityBaseRepository extends PagingAndSortingRepository<EntityBase, Long> {
}
