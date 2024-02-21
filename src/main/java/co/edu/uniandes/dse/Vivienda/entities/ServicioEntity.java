

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
public class ServicioEntity extends BaseEntity {

    public String nombre;
   
    public Float costoAdicional;

    public enum tipoServicio {parqueadero, gimansio, lavanderia, deposito, restaurante};

    public tipoServicio tipo;
    
    @PodamExclude
    @ManyToMany
    private List<ServicioEntity> viviendasServicio = new ArrayList<>();


    
}
