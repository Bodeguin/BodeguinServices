package pe.com.bodeguin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.bodeguin.model.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Integer>{
	
}
