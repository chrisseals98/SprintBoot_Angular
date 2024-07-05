package seals.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import seals.demo.models.document;

public interface documentRepository extends CrudRepository<document, Long> {}
