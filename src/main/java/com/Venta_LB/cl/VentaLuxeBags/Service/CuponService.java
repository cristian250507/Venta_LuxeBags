package com.Venta_LB.cl.VentaLuxeBags.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Venta_LB.cl.VentaLuxeBags.Model.Cupon;

import com.Venta_LB.cl.VentaLuxeBags.Repository.CuponRepository;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    public List<Cupon> listarCupones(){
        return cuponRepository.findAll();
    }


    public String guardarCupon(Cupon cupon) {
        if (cuponRepository.existsById(cupon.getCodigo())) {
            return "Ese código de cupón ya existe.";
        }
        cuponRepository.save(cupon);
        return "Cupón con código: " + cupon.getCodigo() + " guardado con éxito.";
    }

    public Cupon buscarPorCodigo(String codigo) {
        return cuponRepository.findById(codigo).orElse(null);
    }

    public String actualizarCupon(Cupon cupon) {
        cuponRepository.save(cupon);
        return "Cupón con código: " + cupon.getCodigo() + " actualizado correctamente.";
    }

    public String eliminarCupon(String codigo) {
        cuponRepository.deleteById(codigo);
        return "Cupón con código: " + codigo + " eliminado con éxito.";
    }

    public String guardarCupones(List<Cupon> cupones) {
        cuponRepository.saveAll(cupones);
        return "Se guardaron " + cupones.size() + " cupones con éxito.";
    }

    public boolean existePorCodigo(String codigo) {
        return cuponRepository.existsById(codigo);
    }


}
