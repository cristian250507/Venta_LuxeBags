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

import com.Venta_LB.cl.VentaLuxeBags.Model.Detalle_Venta;

import com.Venta_LB.cl.VentaLuxeBags.Service.DetalleVentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("/detalles")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Detalle_Venta>> listarVentas() {
        List <Detalle_Venta> detalle_Ventas = detalleVentaService.listarVentas();
        if (detalle_Ventas.isEmpty()) {
            return ResponseEntity.noContent().build(); //204
            
        }
        
        return ResponseEntity.ok(detalle_Ventas);
    }
    
    @GetMapping("/buscar/{id}")
    @Operation(
        summary = "Buscar detalle de venta por ID",
        description = "Retorna un detalle de venta específico según su ID."
    )
    public ResponseEntity<Detalle_Venta> buscarPorId(@PathVariable Integer id) {
        Detalle_Venta detalle = detalleVentaService.buscarPorId(id);
        if (detalle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(detalle);
    }

    @PostMapping("/guardar")
    @Operation(
        summary = "Guardar nuevo detalle de venta",
        description = "Registra un nuevo detalle de venta con producto, cantidad, precio unitario y total."
    )
    public ResponseEntity<String> guardar(@RequestBody Detalle_Venta detalle) {
        if (detalleVentaService.existePorId(detalle.getId())) {
            return ResponseEntity.badRequest().body("El ID ya existe.");
        }
        String mensaje = detalleVentaService.guardar(detalle);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @PutMapping("/actualizar")
    @Operation(
        summary = "Actualizar detalle de venta",
        description = "Actualiza los campos de un detalle de venta existente según su ID."
    )
    public ResponseEntity<String> actualizar(@RequestBody Detalle_Venta detalle) {
        Detalle_Venta existente = detalleVentaService.buscarPorId(detalle.getId());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Detalle no encontrado.");
        }

        existente.setIdProducto(detalle.getIdProducto());
        existente.setCantidad(detalle.getCantidad());
        existente.setPrecioUnitario(detalle.getPrecioUnitario());
        existente.setTotal(detalle.getTotal());

        String mensaje = detalleVentaService.actualizar(existente);
        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(
        summary = "Eliminar detalle de venta por ID",
        description = "Elimina un detalle de venta en base a su identificador único."
    )
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        Detalle_Venta detalle = detalleVentaService.buscarPorId(id);
        if (detalle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Detalle no encontrado.");
        }
        String mensaje = detalleVentaService.eliminar(id);
        return ResponseEntity.ok(mensaje);
    }







}
