package com.Venta_LB.cl.VentaLuxeBags.Model;

import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cupon")
public class Cupon {
    @Id
    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(nullable = false)
    private BigDecimal descuento;

    @Column(nullable = false)
    private String fechaExpiracion;

    @Column(nullable = false)
    private Integer usosRestantes;

    @Column(nullable = false)
    private boolean activo;


    

}
