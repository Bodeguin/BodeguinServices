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
import pe.com.bodeguin.model.TipoPago;
import pe.com.bodeguin.service.TipoPagoService;

@Api(value = "Endpoints de tipo de pago")
@RestController
@RequestMapping("/api/tipoPagos")
public class TipoPagoRestController {
	
	@Autowired
	private TipoPagoService tipoPagoService;
	
	@ApiOperation(value = "EndPoint que permite listar los tipos de pago")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TipoPago>> listar(){
		ResponseEntity<List<TipoPago>> response;
		try {
			List<TipoPago> tipoPagos = tipoPagoService.findAll();
			response = new ResponseEntity<List<TipoPago>>(tipoPagos, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			return new ResponseEntity<List<TipoPago>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value ="Endpoint que permite buscar un tipo de pago por id")
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipoPago> getTipoPago(@PathVariable("id") int id){
		try {
			Optional<TipoPago> tipoPagos = tipoPagoService.findById(id);
			if(tipoPagos.isPresent()) {
				return new ResponseEntity<TipoPago>(tipoPagos.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<TipoPago>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<TipoPago>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite grabar un tipo de pago")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipoPago> nuevo(@RequestBody TipoPago tipoPago) {
		try {
			TipoPago nuevoTipoPago = tipoPagoService.save(tipoPago);
			return new ResponseEntity<TipoPago>(nuevoTipoPago, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<TipoPago>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite actualizar un tipo de pago")
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipoPago> actualizar(@PathVariable("id") Integer id, @RequestBody TipoPago tipoPago) {
		try {
			if (id.equals(tipoPago.getId())) {
				Optional<TipoPago> tip = tipoPagoService.findById(id);
				if (tip.isPresent()) {
					TipoPago tipoPagoUpdate = tipoPagoService.update(tipoPago);
					return new ResponseEntity<TipoPago>(tipoPagoUpdate, HttpStatus.OK);
				} else {
					return new ResponseEntity<TipoPago>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<TipoPago>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<TipoPago>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping(path = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
		try {
			Optional<TipoPago> tipoPago = tipoPagoService.findById(id);
			if (tipoPago.isPresent()) {
				tipoPagoService.deleteById(id);
				return new ResponseEntity<String>("Eliminado", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
