package co.edu.uniandes.dse.Vivienda.dto;

import lombok.Data;

@Data
public class PropietarioDTO {
    private long id; 
    private String foto;
    private String nombre; 
    private Integer celular;
    private String correo; 
}
