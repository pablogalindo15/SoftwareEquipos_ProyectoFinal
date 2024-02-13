package co.edu.uniandes.dse.Vivienda.entities;


import java.util.List; 
import java.util.ArrayList; 

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Data;
@Data
@Entity
public class LugarEntity extends BaseEntity {
    private String fotos;
    private int tiempoLlegada;
    private boolean gratis;
    private float precioMin;
    private float precioMax;
    private double coordenadaX;
    private double coordenadaY;
    public enum tipoLugar {Universidad, Restaurante, Supermercado, Parque, CentroComercial }
 
@ManyToMany
private List<ViviendaEntity> viviendas_cercanas = new ArrayList<>();

}
