package pe.com.bodeguin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bodeguin.model.TipoPago;
import pe.com.bodeguin.repository.TipoPagoRepository;
import pe.com.bodeguin.service.TipoPagoService;

@Service
public class TipoPagoServiceImpl implements TipoPagoService {

	@Autowired
	private TipoPagoRepository tipoPagoRepository;

	@Override
	public List<TipoPago> findAll() throws Exception {
		return tipoPagoRepository.findAll();
	}

	@Override
	public Optional<TipoPago> findById(Integer id) throws Exception {
		return tipoPagoRepository.findById(id);
	}

	@Override
	public TipoPago save(TipoPago t) throws Exception {
		return tipoPagoRepository.save(t);
	}

	@Override
	public TipoPago update(TipoPago t) throws Exception {
		return tipoPagoRepository.save(t);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		tipoPagoRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		tipoPagoRepository.deleteAll();
	}
}
