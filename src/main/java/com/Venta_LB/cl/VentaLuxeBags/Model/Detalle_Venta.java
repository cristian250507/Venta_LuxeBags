package com.Venta_LB.cl.VentaLuxeBags.Model;


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
@Table(name = "detalle_venta")
public class Detalle_Venta {
    @Id
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private Integer idProducto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private double precioUnitario;

    @Column(nullable = false)
    private double total;
    
}
