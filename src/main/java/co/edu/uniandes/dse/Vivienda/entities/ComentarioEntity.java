package co.edu.uniandes.dse.Vivienda.entities;

import java.util.ArrayList;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class ComentarioEntity extends  BaseEntity
{
    
    private String titulo;
    private String texto;
    private int calificacion;
    
    private String nombre;
    @ManyToMany(mappedBy = "comentarios")
    private List<HabitanteEntity> estudiantes = new ArrayList<>();
    
}
