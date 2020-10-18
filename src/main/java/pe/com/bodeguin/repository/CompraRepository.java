package pe.com.bodeguin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.bodeguin.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer>{
	
}
