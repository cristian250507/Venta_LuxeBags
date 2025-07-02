package com.Venta_LB.cl.VentaLuxeBags.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Integer idCliente;
    private String rut;
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private String correoElectronico;
    private String telefono;
    
}



