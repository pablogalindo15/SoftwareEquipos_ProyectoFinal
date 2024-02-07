package co.edu.uniandes.dse.Vivienda.entities;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class ServicioEntity extends BaseEntity {
    private String nombre;
    private float costoAdicional;
    public enum tipoServicio {parqueadero, gimansio, lavanderia, deposito, restaurante};

    
}

