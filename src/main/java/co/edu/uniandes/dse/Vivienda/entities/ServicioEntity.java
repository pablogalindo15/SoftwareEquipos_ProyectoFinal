package co.edu.uniandes.dse.Vivienda.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class ServicioEntity extends BaseEntity {

    private String nombre;
    private float costoAdicional;
    public enum tipoServicio {
        parqueadero, gimansio, lavanderia, deposito, restaurante
    };
    
    @ManyToOne
    ViviendaEntity vivienda;

    
}

