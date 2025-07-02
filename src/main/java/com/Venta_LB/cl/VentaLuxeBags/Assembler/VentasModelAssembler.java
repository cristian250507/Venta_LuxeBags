package com.Venta_LB.cl.VentaLuxeBags.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.Venta_LB.cl.VentaLuxeBags.Controller.VentaControllerV2;
import com.Venta_LB.cl.VentaLuxeBags.Model.Venta;

@Component
public class VentasModelAssembler {

    public EntityModel<Venta> toModel(Venta venta){
        return EntityModel.of(venta,
        linkTo(methodOn(VentaControllerV2.class).getVentaById(venta.getId())).withSelfRel(),
        linkTo(methodOn(VentaControllerV2.class).getAllVentas()).withRel("Ventas"));
        
    }



}

