package pe.com.bodeguin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.bodeguin.model.Bodega;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Integer> {

}
