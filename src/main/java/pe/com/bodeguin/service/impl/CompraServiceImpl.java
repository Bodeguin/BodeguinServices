package pe.com.bodeguin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bodeguin.model.Boleta;
import pe.com.bodeguin.model.Compra;
import pe.com.bodeguin.model.Factura;
import pe.com.bodeguin.repository.CompraRepository;
import pe.com.bodeguin.service.CompraService;

@Service
public class CompraServiceImpl implements CompraService{
	
	@Autowired
	private CompraRepository compraRepository;

	@Override
	public List<Compra> findAll() throws Exception {
		return compraRepository.findAll();
	}

	@Override
	public Optional<Compra> findById(Integer id) throws Exception {
		return compraRepository.findById(id);
	}

	@Override
	public Compra save(Compra t) throws Exception {
		if(t.isConBoleta() == true) {
			Boleta bo = new Boleta();
			t.setBoleta(bo);
			t.setFactura(null);
		}else {
			if(t.isConFactura() == true) {
				Factura fa = new Factura();
				t.setFactura(fa);
				t.setBoleta(null);
			}else {
				if(t.isSoloEfectivo() == true) {
					t.setFactura(null);
					t.setBoleta(null);
				}
			}
		}
		return compraRepository.save(t);
	}

	@Override
	public Compra update(Compra t) throws Exception {
		return compraRepository.save(t);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		compraRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		compraRepository.deleteAll();
	}
}
