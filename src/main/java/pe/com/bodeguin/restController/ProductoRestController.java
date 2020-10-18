package pe.com.bodeguin.restController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.com.bodeguin.model.Producto;
import pe.com.bodeguin.service.ProductoService;

@Api(value = "Enpoint de productos")
@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {
	
	@Autowired
	private ProductoService productoService;
	
	@ApiOperation(value = "EndPoint que permite listar los productos")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Producto>> listar(){
		ResponseEntity<List<Producto>> response;
		try {
			List<Producto> productos = productoService.findAll();
			response = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value ="Endpoint que permite buscar un producto por id")
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Producto> getProducto(@PathVariable("id") int id){
		try {
			Optional<Producto> producto = productoService.findById(id);
			if(producto.isPresent()) {
				return new ResponseEntity<Producto>(producto.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Producto>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite grabar un producto")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Producto> nuevo(@RequestBody Producto producto) {
		try {
			Producto nuevoProducto = productoService.save(producto);
			return new ResponseEntity<Producto>(nuevoProducto, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Producto>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite actualizar un producto")
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Producto> actualizar(@PathVariable("id") Integer id, @RequestBody Producto producto) {
		try {
			if (id.equals(producto.getId())) {
				Optional<Producto> pro = productoService.findById(id);
				if (pro.isPresent()) {
					Producto productoUpdate = productoService.update(producto);
					return new ResponseEntity<Producto>(productoUpdate, HttpStatus.OK);
				} else {
					return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<Producto>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping(path = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
		try {
			Optional<Producto> producto = productoService.findById(id);
			if (producto.isPresent()) {
				productoService.deleteById(id);
				return new ResponseEntity<String>("Eliminado", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
