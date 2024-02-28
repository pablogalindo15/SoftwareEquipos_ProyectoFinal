package co.edu.uniandes.dse.Vivienda.dto;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;


@Data
public class ComentarioDetailDTO extends ComentarioDTO {
    
    private List<HabitanteDTO> Estudiantes = new ArrayList<>();
}
