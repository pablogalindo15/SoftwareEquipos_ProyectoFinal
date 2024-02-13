package co.edu.uniandes.dse.Vivienda.entities;

import java.util.ArrayList;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class HabitanteEntity extends BaseEntity {
     
     private String nombre;
     
     private int cedula;

@ManyToMany
private List<ComentarioEntity> comentarios = new ArrayList<>();
}