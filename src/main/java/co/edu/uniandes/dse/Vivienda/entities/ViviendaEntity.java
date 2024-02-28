package co.edu.uniandes.dse.Vivienda.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


import javax.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class ViviendaEntity extends BaseEntity{
    
    private String nombre;
    private Float precio;
    private String descripcion;
    private String fotos;
    private Integer tamano;
    public enum posiblesEstratos{
        uno,  dos, tres, cuatro, cinco, seis
    };
    private posiblesEstratos estrato;
    private String restricciones;
    public enum tipoVivienda{
        apartaestudio, apartamentoComp, habitaconEnApto, habitacionFamilia
    };
    private tipoVivienda tipo;
    private String contacto;
    private String direccion;
    private Boolean ocupada;
    private Double coordX;
    private Double coordY;

    @PodamExclude
    @ManyToMany
    private List<HabitanteEntity> historial = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "vivienda")
    private List<HabitanteEntity> habitantes_actuales = new ArrayList<>();

    @PodamExclude
    @ManyToOne
    private PropietarioEntity propietario;

    @PodamExclude
    @ManyToMany 
    private List<LugarEntity> lugarDeInteres_cercano = new ArrayList<>();
    
    @PodamExclude
    @ManyToMany
    private List<ServicioEntity> serviciosVivienda = new ArrayList<>(); 




}

