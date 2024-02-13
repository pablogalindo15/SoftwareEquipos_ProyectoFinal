package co.edu.uniandes.dse.Vivienda.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.ManyToAny;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class ViviendaEntity extends BaseEntity{
    
    private String nombre;
    private float precio;
    private String descripcion;
    private String fotos;
    private int tamano;
    private int estrato;
    private String restricciones;
    public enum tipoVivienda{
        apartaestudio, apartamentoComp, habitaconEnApto, habitacionFamilia
    };
    private String contacto;
    private String direccion;
    private Boolean ocupada;
    private double coordX;
    private double coordY;
    @ManyToMany
    private List<HabitanteEntity> historial = new ArrayList<>();
    @OneToMany(mappedBy = "vivienda", orphanRemoval = true)
    private List<HabitanteEntity> habitantes_acutales = new ArrayList<>();



}
