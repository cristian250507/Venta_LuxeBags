package com.Venta_LB.cl.VentaLuxeBags.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.Venta_LB.cl.VentaLuxeBags.Model.Venta;

import com.Venta_LB.cl.VentaLuxeBags.Repository.VentaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventarepository;

  

    public List<Venta> listarVentas(){
        return ventarepository.findAll();
    }

    public String realizarVenta(Venta venta){
        if (ventarepository.existsByDetalleventaId(venta.getDetalleventa().getId())) {
            return "Ese ID de Detalle de Venta ya está asociado a otra venta.";
        }
        ventarepository.save(venta);
        return "La venta con el ID: " + venta.getId() +" fue agregada";
    }

    public List<Venta> buscarPorProducto(String producto){
        return ventarepository.findByProducto(producto);
    }


    public String guardarVentas(List<Venta> ventas){
        ventarepository.saveAll(ventas);
        return "Hay: " +  ventas.size() + " Ventas guardadas con exito";
    }

    public String eliminarVenta(Integer Idventa){
        ventarepository.deleteById(Idventa);
        return "Se elimino la venta con el ID: "+ Idventa ;
    }

    public Venta buscarPoriD (Integer id){
        return ventarepository.findById(id).orElse(null);
    }

    public String actualizarVenta (Venta venta){
        ventarepository.save(venta);
        return "Cliente con ID: "+ venta.getId() + " Actualizado";
    }




}
