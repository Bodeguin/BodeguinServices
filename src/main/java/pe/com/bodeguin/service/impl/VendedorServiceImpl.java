package pe.com.bodeguin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bodeguin.model.Vendedor;
import pe.com.bodeguin.repository.VendedorRepository;
import pe.com.bodeguin.service.VendedorService;

@Service
public class VendedorServiceImpl implements VendedorService{

	@Autowired
	private VendedorRepository vendedorRepository;

	@Override
	public List<Vendedor> findAll() throws Exception {
		return vendedorRepository.findAll();
	}

	@Override
	public Optional<Vendedor> findById(Integer id) throws Exception {
		return vendedorRepository.findById(id);
	}

	@Override
	public Vendedor save(Vendedor t) throws Exception {
		return vendedorRepository.save(t);
	}

	@Override
	public Vendedor update(Vendedor t) throws Exception {
		return vendedorRepository.save(t);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		vendedorRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		vendedorRepository.deleteAll();
	}
}
