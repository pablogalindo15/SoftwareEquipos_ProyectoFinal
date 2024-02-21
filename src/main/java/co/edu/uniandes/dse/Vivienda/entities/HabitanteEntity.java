package co.edu.uniandes.dse.Vivienda.entities;

import java.util.ArrayList;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class HabitanteEntity extends BaseEntity {
     
     private String nombre;
     
     private int cedula;
@PodamExclude
@ManyToMany(mappedBy = "comentarios")
private List<ComentarioEntity> comentarios = new ArrayList<>();

@PodamExclude
@ManyToMany(mappedBy = "historial")
private List<ViviendaEntity> viviendas = new ArrayList<>();

@PodamExclude
@ManyToOne
private ViviendaEntity vivienda;
}