package co.edu.uniandes.dse.Vivienda.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.dse.Vivienda.entities.ServicioEntity;
import lombok.Data;
@Data
public class ServicioDetailDTO extends ServicioDTO{
    private List<ServicioEntity> viviendasServicio = new ArrayList<>();
    
    
}
