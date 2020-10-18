package pe.com.bodeguin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bodeguin.model.Producto;
import pe.com.bodeguin.repository.ProductoRepository;
import pe.com.bodeguin.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public List<Producto> findAll() throws Exception {
		return productoRepository.findAll();
	}

	@Override
	public Optional<Producto> findById(Integer id) throws Exception {
		return productoRepository.findById(id);
	}

	@Override
	public Producto save(Producto t) throws Exception {
		return productoRepository.save(t);
	}

	@Override
	public Producto update(Producto t) throws Exception {
		return productoRepository.save(t);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		productoRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		productoRepository.deleteAll();
	}
	
	
}
