package com.Venta_LB.cl.VentaLuxeBags.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.Venta_LB.cl.VentaLuxeBags.Model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer>{

    boolean existsByDetalleventaId(Integer detalleventaId);

    boolean existsByIdCliente(Integer idCliente);

    boolean existsByIdProducto(Integer idProducto);

}
