package co.edu.uniandes.dse.Vivienda.entities;

import javax.persistence.Entity;

@Entity
public class ComentarioEntity extends  BaseEntity
{
    private String titulo;
    private String texto;
    private int calificacion;
    private String nombre;

}
