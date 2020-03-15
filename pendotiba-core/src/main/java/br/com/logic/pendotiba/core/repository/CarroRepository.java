package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
	
	Optional<Carro> findByNumeroDeOrdemAndAtivoIsTrue(String numeroDeOrdem);
	
	//List<Carro> findByOrderByNumeroDeOrdem();
	
	Optional<Carro> findById(Long idCarro);

	List<Carro> findByAtivoIsTrueOrderByNumeroDeOrdem();

}
