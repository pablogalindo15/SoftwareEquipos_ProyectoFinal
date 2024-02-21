package co.edu.uniandes.dse.Vivienda.dto;

import java.util.List;

import lombok.Data;

@Data
public class ViviendaDetailDTO extends ViviendaDTO{

    private List<LugarDTO> lugarDTO;
    private List<ServicioDTO> servicioDTO;
    private List<HabitanteDTO> habitanteDTO;
    private List<HabitanteDTO> historialDTO;
    
}
