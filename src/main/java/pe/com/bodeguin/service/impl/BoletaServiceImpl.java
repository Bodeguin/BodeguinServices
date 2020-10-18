package pe.com.bodeguin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bodeguin.model.Boleta;
import pe.com.bodeguin.repository.BoletaRepository;
import pe.com.bodeguin.service.BoletaService;

@Service
public class BoletaServiceImpl implements BoletaService {

	@Autowired
	private BoletaRepository boletaRepository;

	@Override
	public List<Boleta> findAll() throws Exception {
		return boletaRepository.findAll();
	}

	@Override
	public Optional<Boleta> findById(Integer id) throws Exception {
		return boletaRepository.findById(id);
	}

	@Override
	public Boleta save(Boleta t) throws Exception {
		return boletaRepository.save(t);
	}

	@Override
	public Boleta update(Boleta t) throws Exception {
		return boletaRepository.save(t);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		boletaRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		boletaRepository.deleteAll();
	}
}
