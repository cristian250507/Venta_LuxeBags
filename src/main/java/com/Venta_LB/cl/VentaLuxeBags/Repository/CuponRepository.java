package com.Venta_LB.cl.VentaLuxeBags.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Venta_LB.cl.VentaLuxeBags.Model.Cupon;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, String>{


    


}
