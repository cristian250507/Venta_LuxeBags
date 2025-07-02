package com.Venta_LB.cl.VentaLuxeBags.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.Venta_LB.cl.VentaLuxeBags.Assembler.VentasModelAssembler;
import com.Venta_LB.cl.VentaLuxeBags.Model.Venta;
import com.Venta_LB.cl.VentaLuxeBags.Service.VentaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/ventas/V2")
public class VentaControllerV2 {

    @Autowired
    private VentaService ventaservice;

    @Autowired
    private VentasModelAssembler Assembler;
    
    
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Venta>> getAllVentas() {
        List<EntityModel<Venta>> ventas = ventaservice.listarVentas().stream()
                .map(Assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(ventas,
                linkTo(methodOn(VentaControllerV2.class).getAllVentas()).withSelfRel());
    }


    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Venta> getVentaById(@PathVariable Integer id) {
        Venta venta = ventaservice.buscarPoriD(id);
        return Assembler.toModel(venta);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> createVenta(@RequestBody Venta venta) {
    String resultado = ventaservice.realizarVenta(venta);

    if (resultado.contains("ya está asociado")) {
        return ResponseEntity.badRequest().body(resultado);
    }
    Venta guardada = ventaservice.buscarPoriD(venta.getId());

    return ResponseEntity
            .created(linkTo(methodOn(VentaControllerV2.class).getVentaById(guardada.getId())).toUri())
            .body(Assembler.toModel(guardada));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> updateVenta(@PathVariable Integer id, @RequestBody Venta venta) {
        venta.setId(id);
        String resultado = ventaservice.actualizarVenta(venta);

        Venta actualizada = ventaservice.buscarPoriD(id);
        if (actualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta no encontrada tras la actualización.");
        }

        return ResponseEntity.ok(Assembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteVenta(@PathVariable Integer id) {
        ventaservice.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }











}
