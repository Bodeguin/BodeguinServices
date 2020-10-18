package pe.com.bodeguin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.bodeguin.model.TipoPago;

@Repository
public interface TipoPagoRepository extends JpaRepository<TipoPago, Integer>{

}
