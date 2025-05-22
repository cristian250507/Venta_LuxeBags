package com.Venta_LB.cl.VentaLuxeBags.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import com.Venta_LB.cl.VentaLuxeBags.Model.Venta;

@Controller
public interface VentaRepository extends JpaRepository<Venta, Integer>{

    List<Venta> findByProducto(String producto);

    boolean existsByDetalleventaId(Integer detalleventaId);

}
