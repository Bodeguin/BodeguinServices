package pe.com.bodeguin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.bodeguin.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

}
