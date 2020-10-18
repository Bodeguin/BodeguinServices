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
import pe.com.bodeguin.model.Boleta;
import pe.com.bodeguin.service.BoletaService;

@Api(value = "Endpoints de Boleta")
@RestController
@RequestMapping("/api/boletas")
public class BoletaRestController {
	
	@Autowired
	private BoletaService boletaService;
	
	@ApiOperation(value = "EndPoint que permite listar las boletas")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Boleta>> listar(){
		ResponseEntity<List<Boleta>> response;
		try {
			List<Boleta> boletas = boletaService.findAll();
			response = new ResponseEntity<List<Boleta>>(boletas, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			return new ResponseEntity<List<Boleta>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value ="Endpoint que permite buscar una boleta por id")
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boleta> getBoleta(@PathVariable("id") int id){
		try {
			Optional<Boleta> boleta = boletaService.findById(id);
			if(boleta.isPresent()) {
				return new ResponseEntity<Boleta>(boleta.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<Boleta>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Boleta>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite grabar una boleta")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boleta> nuevo(@RequestBody Boleta boleta) {
		try {
			Boleta nuevaBoleta = boletaService.save(boleta);
			return new ResponseEntity<Boleta>(nuevaBoleta, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Boleta>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite actualizar una boleta")
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boleta> actualizar(@PathVariable("id") Integer id, @RequestBody Boleta boleta) {
		try {
			if (id.equals(boleta.getId())) {
				Optional<Boleta> bol = boletaService.findById(id);
				if (bol.isPresent()) {
					Boleta boletaUpdate = boletaService.update(boleta);
					return new ResponseEntity<Boleta>(boletaUpdate, HttpStatus.OK);
				} else {
					return new ResponseEntity<Boleta>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<Boleta>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Boleta>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping(path = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
		try {
			Optional<Boleta> boleta = boletaService.findById(id);
			if (boleta.isPresent()) {
				boletaService.deleteById(id);
				return new ResponseEntity<String>("Eliminado", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
