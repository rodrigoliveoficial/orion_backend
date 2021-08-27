package br.com.live.custom;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProgramaBiCustom {
	
	private final JdbcTemplate jdbcTemplate;

	public ProgramaBiCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int findNextAtividade(String areaModulo) {

		Integer nextAtividade;

		String query = " select nvl(max(atividade),0) + 1 from orion_bi_001 a "
					+  " where a.area_modulo = '" + areaModulo + "'";
		try {
			nextAtividade = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextAtividade = 0;
		}
		return nextAtividade;
	}

}
