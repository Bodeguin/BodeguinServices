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
import pe.com.bodeguin.model.Bodega;
import pe.com.bodeguin.service.BodegaService;

@Api(value = "Endpoints de Bodega")
@RestController
@RequestMapping("/api/bodegas")
public class BodegaRestController {
	
	@Autowired
	private BodegaService bodegaService;
	
	@ApiOperation(value = "EndPoint que permite listar las bodegas")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Bodega>> listar(){
		ResponseEntity<List<Bodega>> response;
		try {
			List<Bodega> bodegas = bodegaService.findAll();
			response = new ResponseEntity<List<Bodega>>(bodegas, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			return new ResponseEntity<List<Bodega>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value ="Endpoint que permite buscar una bodega por id")
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bodega> getBodega(@PathVariable("id") int id){
		try {
			Optional<Bodega> bodega = bodegaService.findById(id);
			if(bodega.isPresent()) {
				return new ResponseEntity<Bodega>(bodega.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<Bodega>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Bodega>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite grabar una bodega")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bodega> nuevo(@RequestBody Bodega bodega) {
		try {
			Bodega nuevaBodega = bodegaService.save(bodega);
			return new ResponseEntity<Bodega>(nuevaBodega, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Bodega>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite actualizar una bodega")
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bodega> actualizar(@PathVariable("id") Integer id, @RequestBody Bodega bodega) {
		try {
			if (id.equals(bodega.getId())) {
				Optional<Bodega> bod = bodegaService.findById(id);
				if (bod.isPresent()) {
					Bodega bodegaUpdate = bodegaService.update(bodega);
					return new ResponseEntity<Bodega>(bodegaUpdate, HttpStatus.OK);
				} else {
					return new ResponseEntity<Bodega>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<Bodega>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Bodega>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping(path = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
		try {
			Optional<Bodega> bodega = bodegaService.findById(id);
			if (bodega.isPresent()) {
				bodegaService.deleteById(id);
				return new ResponseEntity<String>("Eliminado", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
