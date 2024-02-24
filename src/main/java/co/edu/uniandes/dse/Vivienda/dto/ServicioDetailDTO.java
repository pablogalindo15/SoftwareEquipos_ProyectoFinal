package co.edu.uniandes.dse.Vivienda.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class ServicioDetailDTO extends ServicioDTO{
    private List<ViviendaDTO> viviendasServicio = new ArrayList<>();
    
    
}
