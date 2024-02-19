package co.edu.uniandes.dse.Vivienda.entities;

import java.util.ArrayList;

import java.util.List;
import javax.persistence.Entity;

import javax.persistence.ManyToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class ComentarioEntity extends  BaseEntity
{
    
    
    private String titulo;
    private String texto;
    private Integer calificacion;
    
    private String nombre;
    @PodamExclude
    @ManyToMany(mappedBy = "comentarios")
    private List<HabitanteEntity> estudiantes = new ArrayList<>();
    
}
