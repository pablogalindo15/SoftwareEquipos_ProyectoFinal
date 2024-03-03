package co.edu.uniandes.dse.Vivienda.dto;

import java.util.List;

import lombok.Data;

@Data
public class ViviendaDetailDTO extends ViviendaDTO{

    private List<LugarDTO> lugarDeInteres_cercano;
    private List<ServicioDTO> serviciosVivienda;
    private List<HabitanteDTO> habitantes_actuales;
    private List<HabitanteDTO> historial;
    
}
