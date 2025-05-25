package com.Venta_LB.cl.VentaLuxeBags.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Venta_LB.cl.VentaLuxeBags.Model.Venta;
import com.Venta_LB.cl.VentaLuxeBags.Service.VentaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaservice;

    @GetMapping("/listar")
    public ResponseEntity<List<Venta>> listarVentas() {
        List <Venta> ventas = ventaservice.listarVentas();
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build(); //204
            
        }
        
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/listar/id/{id}")
    public ResponseEntity<Venta> ListarVentaPorID(@PathVariable Integer id) {
        Venta venta = ventaservice.buscarPoriD(id);
        if (venta==null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(venta);
    }
    

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarVenta(@RequestBody Venta venta) {

        String mensaje =  ventaservice.realizarVenta(venta);

        if (mensaje.contains("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }


    @PostMapping("/guardar-Ventas")
    public ResponseEntity<String> guardarVentas(@RequestBody List<Venta> ventas) {
         
        
        String mensaje =  ventaservice.guardarVentas(ventas);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Integer id){
        Venta ventaExiste = ventaservice.buscarPoriD(id);
        if (ventaExiste == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra esa venta");
        }

        String mensaje = ventaservice.eliminarVenta(id);
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("Actualizar")
    public ResponseEntity<String> actualizarVenta(@RequestBody Venta venta) {
        Venta ventaExiste = ventaservice.buscarPoriD(venta.getId());
        if (ventaExiste == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay un cliente con ese ID");
        }
        ventaExiste.setCliente(venta.getCliente());
        ventaExiste.setFecha(venta.getFecha());
        ventaExiste.setProducto(venta.getProducto());
        ventaExiste.setTotal(venta.getTotal());
        ventaExiste.setDetalleventa(venta.getDetalleventa());
        ventaExiste.setCupon(venta.getCupon());
        String mensaje = ventaservice.actualizarVenta(ventaExiste);
        return ResponseEntity.ok(mensaje);
    }


    


    





    
}
