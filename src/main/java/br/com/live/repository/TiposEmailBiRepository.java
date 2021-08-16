package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.TiposEmailBi;

@Repository
public interface TiposEmailBiRepository extends JpaRepository<TiposEmailBi, String> {
	
	List<TiposEmailBi> findAll();
	
	@Query("SELECT u FROM TiposEmailBi u where u.idPrograma = :idProgramaBi")
	List<TiposEmailBi> findByIdPrograma(String idProgramaBi);
	
	void deleteByIdPrograma(String idPrograma);
	
}
