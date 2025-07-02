package com.Venta_LB.cl.VentaLuxeBags.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Venta_LB.cl.VentaLuxeBags.Model.Venta;
import com.Venta_LB.cl.VentaLuxeBags.Service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/venta")
@Tag(name = "Ventas", description = "Operaciones relacionadas con Ventas")
public class VentaController {

    @Autowired
    private VentaService ventaservice;

    @GetMapping("/listar")
    @Operation(
        summary = "Listar todas las ventas",
        description = "Retorna una lista de todas las ventas registradas. Si no hay ventas, retorna 204 (No Content).")
    public ResponseEntity<List<Venta>> listarVentas() {
        List <Venta> ventas = ventaservice.listarVentas();
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build(); 
            
        }
        
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/listar/id/{id}")
    @Operation(
        summary = "Buscar venta por ID",
        description = "Retorna una venta específica según su ID. Si no existe, retorna 404 (Not Found).")
    public ResponseEntity<Venta> ListarVentaPorID(@PathVariable Integer id) {
        Venta venta = ventaservice.buscarPoriD(id);
        if (venta==null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(venta);
    }
    

    @PostMapping("/guardar")
    @Operation(
        summary = "Guardar una nueva venta",
        description = "Registra una nueva venta. Si hay errores en los datos, retorna 400 (Bad Request).")
    public ResponseEntity<String> guardarVenta(@RequestBody Venta venta) {

        String mensaje =  ventaservice.realizarVenta(venta);

        if (mensaje.contains("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }


    @PostMapping("/guardar-Ventas")
    @Operation(
        summary = "Guardar múltiples ventas",
        description = "Registra una lista de ventas en un solo llamado. Retorna 201 si todo sale bien.")
    public ResponseEntity<String> guardarVentas(@RequestBody List<Venta> ventas) {
         
        
        String mensaje =  ventaservice.guardarVentas(ventas);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(
        summary = "Eliminar una venta por ID",
        description = "Elimina una venta existente usando su ID. Retorna 404 si no existe.")
    public ResponseEntity<String> eliminarVenta(@PathVariable Integer id){
        Venta ventaExiste = ventaservice.buscarPoriD(id);
        if (ventaExiste == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra esa venta");
        }

        String mensaje = ventaservice.eliminarVenta(id);
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("Actualizar")
    @Operation(
        summary = "Actualizar una venta",
        description = "Modifica los datos de una venta existente. Retorna 404 si no se encuentra la venta.")
    public ResponseEntity<String> actualizarVenta(@RequestBody Venta venta) {
        Venta ventaExiste = ventaservice.buscarPoriD(venta.getId());
        if (ventaExiste == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay un cliente con ese ID");
        }
        ventaExiste.setIdCliente(venta.getIdCliente());
        ventaExiste.setFecha(venta.getFecha());
        ventaExiste.setIdProducto(venta.getIdProducto());
        ventaExiste.setTotal(venta.getTotal());
        ventaExiste.setDetalleventa(venta.getDetalleventa());
        ventaExiste.setCupon(venta.getCupon());
        String mensaje = ventaservice.actualizarVenta(ventaExiste);
        return ResponseEntity.ok(mensaje);
    }
    
}
