package co.edu.uniandes.dse.Vivienda.entities;

import javax.persistence.Entity;

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

}
