package br.com.logic.pendotiba.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

}
