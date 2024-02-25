package co.edu.uniandes.dse.Vivienda.dto;


import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class HabitanteDetailDTO extends HabitanteDTO{
    private List <ViviendaDTO> viviendas = new ArrayList<>(); 


    
}
