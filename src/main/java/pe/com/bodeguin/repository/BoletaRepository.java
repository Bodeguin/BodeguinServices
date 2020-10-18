package pe.com.bodeguin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.bodeguin.model.Boleta;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Integer>{

}