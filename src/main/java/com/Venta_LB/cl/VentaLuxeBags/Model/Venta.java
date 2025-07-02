package com.Venta_LB.cl.VentaLuxeBags.Model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer idProducto;

    @Column(nullable = false)
    private Integer idCliente;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private String fecha;

    @OneToOne
    private Detalle_Venta detalleventa;

    @ManyToOne
    @JoinColumn(name = "id_cupon", nullable = true )
    private Cupon cupon;
}
