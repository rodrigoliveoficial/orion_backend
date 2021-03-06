package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.InspecaoQualidadeLanctoPeca;

@Repository
public interface InspecaoQualidadeLanctoPecaRepository extends JpaRepository<InspecaoQualidadeLanctoPeca, Long> {
	
	void deleteByIdInspecao(long idInspecao);
	
}
