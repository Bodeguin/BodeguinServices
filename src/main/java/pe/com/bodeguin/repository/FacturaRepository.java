package pe.com.bodeguin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.bodeguin.model.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {

}
