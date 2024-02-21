package co.edu.uniandes.dse.Vivienda.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;

import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;

public class PropietarioDetailDTO extends PropietarioDTO {
        @OneToMany(mappedBy="propietario")
        private List<ViviendaEntity> viviendas = new ArrayList<>(); 

}
