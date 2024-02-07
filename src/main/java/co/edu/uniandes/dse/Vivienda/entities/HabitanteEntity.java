package co.edu.uniandes.dse.Vivienda.entities;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class HabitanteEntity extends BaseEntity {
     private String nombre;
     private int cedula;

    
}