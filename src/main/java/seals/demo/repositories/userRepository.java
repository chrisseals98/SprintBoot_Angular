package seals.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import seals.demo.models.user;

public interface userRepository extends CrudRepository<user, Long> {}
