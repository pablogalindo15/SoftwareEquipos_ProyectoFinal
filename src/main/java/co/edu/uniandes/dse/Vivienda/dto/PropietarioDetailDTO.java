package co.edu.uniandes.dse.Vivienda.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import lombok.Data;

@Data
public class PropietarioDetailDTO extends PropietarioDTO {
        private List<ViviendaEntity> viviendas = new ArrayList<>(); 

}
