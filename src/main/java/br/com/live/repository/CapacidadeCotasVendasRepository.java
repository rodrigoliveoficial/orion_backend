package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.CapacidadeCotasVendasCapa;

@Repository
public interface CapacidadeCotasVendasRepository extends JpaRepository<CapacidadeCotasVendasCapa, String> {
	
	
	@Query("SELECT u FROM CapacidadeCotasVendasCapa u where u.id = :idCotasVendas")
	CapacidadeCotasVendasCapa findByIdCapacidadeCotasVendas(String idCotasVendas);
	
	@Query("SELECT u FROM CapacidadeCotasVendasCapa u where u.periodo = :periodo and u.categoria = :categoria and u.linha = :linha")
	CapacidadeCotasVendasCapa findByPeriodoCategoriaLinha(int periodo, int categoria, int linha);
}
