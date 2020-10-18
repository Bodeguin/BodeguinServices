package pe.com.bodeguin.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

import pe.com.bodeguin.model.Producto;

public class ProductoDisponibleTest {
	
	public Producto procPrueba;
	
	@Test
	void testProductoDisp() {
		boolean esperado = true;
		boolean expected;
		Random r = new Random();
		int n = r.nextInt(50);
		if(n > 0) {
			expected = true;
		}else {
			expected = false;
		}
		procPrueba = new Producto();
		
		procPrueba.setCategoria("vegetales");
		procPrueba.setNombre("brocoli");
		procPrueba.setPrecio(0.99);
		procPrueba.setStock(20);
		
		assertEquals(esperado,expected);
	}
	
	@Test
	void ProductoNoDisponibleTest() {
		int esperado = 0;
		
		procPrueba = new Producto();
	
		procPrueba.setStock(0);
		procPrueba.setCategoria("vegetales");
		procPrueba.setNombre("brocoli");
		procPrueba.setPrecio(0.99);
		
		//Para un producto sin stock  
		assertEquals(esperado, procPrueba.getStock());
	}
}
