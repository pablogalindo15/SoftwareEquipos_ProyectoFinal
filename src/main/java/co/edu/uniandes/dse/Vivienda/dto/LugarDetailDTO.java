package co.edu.uniandes.dse.Vivienda.dto;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LugarDetailDTO extends LugarDTO{
    private List<ViviendaDTO> viviendas = new ArrayList<>();
    
}
