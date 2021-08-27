package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.InspecaoQualidade;

@Repository
public interface InspecaoQualidadeRepository extends JpaRepository<InspecaoQualidade, Long> {
	
	@Query("SELECT i FROM InspecaoQualidade i where i.id = :id")
	InspecaoQualidade findById(long id);
	
	@Query("SELECT i FROM InspecaoQualidade i where i.ordemProducao = :ordemProducao and i.ordemConfeccao = :ordemConfeccao and i.codEstagio = :codEstagio order by i.id")
	List<InspecaoQualidade> findAllByOrdemProdConfecEstagio(int ordemProducao, int ordemConfeccao, int codEstagio); 
	
}
