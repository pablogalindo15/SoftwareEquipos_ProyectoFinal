package co.edu.uniandes.dse.Vivienda.entities;

import javax.persistence.Entity;

import lombok.Data;
@Data 
@Entity
public class PropietarioEntity extends BaseEntity {
    private String foto;
    private String nombre; 
    private int celular;
    private String correo; 


}
