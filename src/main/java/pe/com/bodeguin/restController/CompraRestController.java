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
import pe.com.bodeguin.model.Compra;
import pe.com.bodeguin.service.CompraService;

@Api(value = "Endpoints de compra")
@RestController
@RequestMapping("/api/compras")
public class CompraRestController {
	
	@Autowired
	private CompraService compraService;
	
	@ApiOperation(value = "EndPoint que permite listar las compras")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Compra>> listar(){
		ResponseEntity<List<Compra>> response;
		try {
			List<Compra> compras = compraService.findAll();
			response = new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			return new ResponseEntity<List<Compra>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value ="Endpoint que permite buscar una compra por id")
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Compra> getCompra(@PathVariable("id") int id){
		try {
			Optional<Compra> compra = compraService.findById(id);
			if(compra.isPresent()) {
				return new ResponseEntity<Compra>(compra.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<Compra>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Compra>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite grabar una compra")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Compra> nuevo(@RequestBody Compra compra) {
		try {
			Compra nuevaCompra = compraService.save(compra);
			return new ResponseEntity<Compra>(nuevaCompra, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Compra>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite actualizar una compra")
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Compra> actualizar(@PathVariable("id") Integer id, @RequestBody Compra compra) {
		try {
			if (id.equals(compra.getId())) {
				Optional<Compra> com = compraService.findById(id);
				if (com.isPresent()) {
					Compra compraUpdate = compraService.update(compra);
					return new ResponseEntity<Compra>(compraUpdate, HttpStatus.OK);
				} else {
					return new ResponseEntity<Compra>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<Compra>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Compra>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping(path = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
		try {
			Optional<Compra> compra = compraService.findById(id);
			if (compra.isPresent()) {
				compraService.deleteById(id);
				return new ResponseEntity<String>("Eliminado", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
