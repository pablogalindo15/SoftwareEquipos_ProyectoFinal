package co.edu.uniandes.dse.Vivienda.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.ArrayList;

import lombok.Data;
@Data 
@Entity
public class PropietarioEntity extends BaseEntity {
    private String foto;
    private String nombre; 
    private int celular;
    private String correo; 

    @OneToMany(mappedBy="propietario")
    private List<ViviendaEntity> viviendas = new ArrayList<>(); 
}
