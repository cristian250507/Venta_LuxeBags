package com.Venta_LB.cl.VentaLuxeBags.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Venta_LB.cl.VentaLuxeBags.Model.Detalle_Venta;

import com.Venta_LB.cl.VentaLuxeBags.Repository.DetalleventaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleVentaService {
    @Autowired
    private DetalleventaRepository detalleventaRepository;

    public List<Detalle_Venta> listarVentas(){
        return detalleventaRepository.findAll();
    }


    public String guardar(Detalle_Venta detalle) {
        
        if (detalle.getId() != null && detalleventaRepository.existsById(detalle.getId())) {
            return "Ese ID de Detalle de Venta ya está registrado.";
        }
        detalleventaRepository.save(detalle);
        return "Detalle de venta con ID: " + detalle.getId() + " guardado con éxito.";
    }

    public Detalle_Venta buscarPorId(Integer id) {
        return detalleventaRepository.findById(id).orElse(null);
    }

    public String actualizar(Detalle_Venta detalle) {
        detalleventaRepository.save(detalle);
        return "Detalle de venta con ID: " + detalle.getId() + " actualizado correctamente.";
    }

    public String eliminar(Integer id) {
        detalleventaRepository.deleteById(id);
        return "Detalle de venta con ID: " + id + " eliminado con éxito.";
    }

    public String guardarVarios(List<Detalle_Venta> detalles) {
        detalleventaRepository.saveAll(detalles);
        return "Se guardaron " + detalles.size() + " detalles de venta con éxito.";
    }

    public boolean existePorId(Integer id) {
        return detalleventaRepository.existsById(id);
    }


}
