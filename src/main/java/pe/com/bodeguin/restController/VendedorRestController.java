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
import pe.com.bodeguin.model.Vendedor;
import pe.com.bodeguin.service.VendedorService;

@Api(value = "Endpoints de vendedor")
@RestController
@RequestMapping("/api/vendedores")
public class VendedorRestController {
	
	@Autowired
	private VendedorService vendedorService;
	
	@ApiOperation(value = "EndPoint que permite listar los vendedores")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Vendedor>> listar(){
		ResponseEntity<List<Vendedor>> response;
		try {
			List<Vendedor> vendedores = vendedorService.findAll();
			response = new ResponseEntity<List<Vendedor>>(vendedores, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			return new ResponseEntity<List<Vendedor>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value ="Endpoint que permite buscar un vendedor por id")
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vendedor> getVendedor(@PathVariable("id") int id){
		try {
			Optional<Vendedor> vendedor = vendedorService.findById(id);
			if(vendedor.isPresent()) {
				return new ResponseEntity<Vendedor>(vendedor.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<Vendedor>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Vendedor>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite grabar un vendedor")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vendedor> nuevo(@RequestBody Vendedor vendedor) {
		try {
			Vendedor nuevoUsuario = vendedorService.save(vendedor);
			return new ResponseEntity<Vendedor>(nuevoUsuario, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Vendedor>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite actualizar un vendedor")
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vendedor> actualizar(@PathVariable("id") Integer id, @RequestBody Vendedor vendedor) {
		try {
			if (id.equals(vendedor.getId())) {
				Optional<Vendedor> ven = vendedorService.findById(id);
				if (ven.isPresent()) {
					Vendedor vendedorUpdate = vendedorService.update(vendedor);
					return new ResponseEntity<Vendedor>(vendedorUpdate, HttpStatus.OK);
				} else {
					return new ResponseEntity<Vendedor>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<Vendedor>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Vendedor>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping(path = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
		try {
			Optional<Vendedor> vendedor = vendedorService.findById(id);
			if (vendedor.isPresent()) {
				vendedorService.deleteById(id);
				return new ResponseEntity<String>("Eliminado", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
