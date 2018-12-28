package com.dotcom.costaverde.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dotcom.costaverde.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{
	
	@Query("select p from Position p where p.id in (select max(p2.id) from Position p2 group by p2.placa) and data >= subtime(NOW() , '3:0:0.000000' ) 	order by p.data")
	List<Position> findMaxIdPrd();

	@Query("select p from Position p where p.id in (select max(p2.id) from Position p2 group by p2.placa) and data >= subtime(NOW() , '1:0:0.000000' ) 	order by p.data")
	List<Position> findMaxIdDev();
	
	@Query("select p from Position p where p.id in (select max(p2.id) from Position p2 where p2.placa = :placa " + 
			"      and data >= subtime(NOW() , '5:0:0.000000' )" +
			" group by p2.latitude, p2.longitude ) order by p.data")
	List<Position> findOnibusPrd(@Param("placa") String placa);

	@Query("select p from Position p where p.id in (select max(p2.id) from Position p2 where p2.placa = :placa " + 
			"      and data >= subtime(NOW() , '3:0:0.000000' )" +
			" group by p2.latitude, p2.longitude ) order by p.data")
	List<Position> findOnibusDev(@Param("placa") String placa);
	
}
