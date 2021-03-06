package com.dotcom.costaverde.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dotcom.costaverde.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{
	
	@Query("select p from Position p where p.id in (select max(p2.id) from Position p2 group by p2.placa) and data >= subtime( :dataFim , '1:0:0.000000' ) 	order by p.data")
	List<Position> findMaxId(@Param("dataFim") String dataFim);

	@Query("select p from Position p where p.id in (select max(p2.id) from Position p2 where p2.placa = :placa " + 
			"      and data >= subtime(:dataFim , '4:0:0.000000' )" +
			" group by nome ) order by p.data")
	List<Position> findOnibus(@Param("placa") String placa, @Param("dataFim") String dataFim);

	@Query("select NOW() from Controle")
	String getHoraDB();
	
  @Modifying
  @Transactional
	@Query("delete Position p where p.data < subtime(:dataIni , '48:0:0.000000' )")
	void limpaBanco(@Param("dataIni") String dataIni);
	
}


