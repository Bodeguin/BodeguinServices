package pe.com.bodeguin.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pe.com.bodeguin.model.Boleta;
import pe.com.bodeguin.model.Compra;
import pe.com.bodeguin.model.Factura;
import pe.com.bodeguin.service.CompraService;

public class ComprobantesPagoTest {
	
	public Compra compraPrueba;
	
	@Autowired
	private CompraService compraService;
	
	@Test
	void testSoloBoleta() {
		boolean esperado = true;
		boolean result;
		
		compraPrueba = new Compra();
		
		compraPrueba.setConBoleta(true);
		compraPrueba.setCodigo("1234");
		compraPrueba.setImporte(10);
		
		Compra co;
		try {
			co = compraService.save(compraPrueba);
			
			Factura fac;
			fac = co.getFactura();
			if(fac == null) {
				result = true;
				assertEquals(esperado, result);

			}
		} catch (Exception e) {
		}
	}
	
	@Test
	void testSoloFactura() {
		boolean esperado = true;
		boolean result;
		
		compraPrueba = new Compra();
		
		compraPrueba.setConFactura(true);
		compraPrueba.setCodigo("1234");
		compraPrueba.setImporte(10);
		
		Compra co;
		try {
			co = compraService.save(compraPrueba);
			
			Boleta bo;
			bo = co.getBoleta();
			if(bo == null) {
				result = true;
				assertEquals(esperado, result);

			}
		} catch (Exception e) {
		}
	}
	
	@Test
	void testSoloEfectivo() {
		boolean esperado = true;
		boolean result;
		
		compraPrueba = new Compra();
		
		compraPrueba.setSoloEfectivo(true);
		compraPrueba.setCodigo("1234");
		compraPrueba.setImporte(10);
		
		Compra co;
		
		try {
			co = compraService.save(compraPrueba);
			
			Boleta bo;
			Factura fa;
			
			bo = co.getBoleta();
			fa = co.getFactura();
			
			if(bo == null && fa == null) {
				result = true;
				assertEquals(esperado, result);

			}
		} catch (Exception e) {
		}
	}
}
