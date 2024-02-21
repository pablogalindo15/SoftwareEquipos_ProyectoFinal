package co.edu.uniandes.dse.Vivienda.entities;

import java.util.ArrayList;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class HabitanteEntity extends BaseEntity {
     
private String nombre;
     
private int cedula;

@ManyToMany
private List<ComentarioEntity> comentarios = new ArrayList<>();
@ManyToMany(mappedBy = "historial")
private List<ViviendaEntity> viviendas = new ArrayList<>();
@ManyToOne
private ViviendaEntity vivienda;
}