package com.Venta_LB.cl.VentaLuxeBags.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Venta_LB.cl.VentaLuxeBags.Model.Detalle_Venta;

@Repository
public interface DetalleventaRepository extends JpaRepository <Detalle_Venta, Integer>{

    


}
