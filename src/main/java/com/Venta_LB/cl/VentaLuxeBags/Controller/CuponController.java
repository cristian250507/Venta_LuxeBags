package com.Venta_LB.cl.VentaLuxeBags.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Venta_LB.cl.VentaLuxeBags.Model.Cupon;
import com.Venta_LB.cl.VentaLuxeBags.Service.CuponService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cupones")
@Tag(name = "Gestión de Cupones", description = "CRUD y operaciones sobre cupones de descuento")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @GetMapping("/listar")
    public ResponseEntity<List<Cupon>> listarCupones() {
        List <Cupon> cupon = cuponService.listarCupones();
        if (cupon.isEmpty()) {
            return ResponseEntity.noContent().build(); 
            
        }
        
        return ResponseEntity.ok(cupon);
    }

    @GetMapping("/buscar/{codigo}")
    @Operation(
        summary = "Buscar cupón por código",
        description = "Retorna un cupón según su código. Si no existe, retorna 404."
    )
    public ResponseEntity<Cupon> buscarCuponPorCodigo(@PathVariable String codigo) {
        Cupon cupon = cuponService.buscarPorCodigo(codigo);
        if (cupon == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(cupon);
    }

    @PostMapping("/guardar")
    @Operation(
        summary = "Guardar nuevo cupón",
        description = "Registra un nuevo cupón en la base de datos. Si el código ya existe, retorna 400."
    )
    public ResponseEntity<String> guardarCupon(@RequestBody Cupon cupon) {
        if (cuponService.existePorCodigo(cupon.getCodigo())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Ya existe un cupón con ese código.");
        }
        String mensaje = cuponService.guardarCupon(cupon);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @PostMapping("/guardar-multiple")
    @Operation(
        summary = "Guardar múltiples cupones",
        description = "Guarda una lista de cupones. Retorna 201 si se guardan correctamente."
    )
    public ResponseEntity<String> guardarCupones(@RequestBody List<Cupon> cupones) {
        String mensaje = cuponService.guardarCupones(cupones);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @PutMapping("/actualizar")
    @Operation(
        summary = "Actualizar un cupón",
        description = "Modifica los datos de un cupón existente. Si no se encuentra, retorna 404."
    )
    public ResponseEntity<String> actualizarCupon(@RequestBody Cupon cupon) {
        Cupon existente = cuponService.buscarPorCodigo(cupon.getCodigo());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró un cupón con el código: " + cupon.getCodigo());
        }
        String mensaje = cuponService.actualizarCupon(cupon);
        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/eliminar/{codigo}")
    @Operation(
        summary = "Eliminar cupón por código",
        description = "Elimina un cupón según su código. Retorna 404 si no se encuentra."
    )
    public ResponseEntity<String> eliminarCupon(@PathVariable String codigo) {
        Cupon cupon = cuponService.buscarPorCodigo(codigo);
        if (cupon == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró un cupón con el código: " + codigo);
        }
        String mensaje = cuponService.eliminarCupon(codigo);
        return ResponseEntity.ok(mensaje);
    }













}
