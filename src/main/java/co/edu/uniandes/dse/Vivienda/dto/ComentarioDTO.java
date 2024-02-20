package co.edu.uniandes.dse.Vivienda.dto;
import lombok.Data;
@Data
public class ComentarioDTO {
    private Long id;
    private String nombre;
    private String titulo;
    private String texto;
    private Integer calificacion;
}
