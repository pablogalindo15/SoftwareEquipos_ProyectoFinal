package co.edu.uniandes.dse.Vivienda.dto;

import lombok.Data;

@Data
public class ViviendaDTO {
    private Long id;
    private String nombre;
    private Float precio;
    private String descripcion;
    private String fotos;
    private Integer tamano;
    public enum posiblesEstratos{
        uno,  dos, tres, cuatro, cinco, seis
    };
    private posiblesEstratos estrato;
    private String restricciones;
    public enum tipoVivienda{
        apartaestudio, apartamentoComp, habitaconEnApto, habitacionFamilia
    };
    private tipoVivienda tipo;
    private String contacto;
    private String direccion;
    private Boolean ocupada;
    private Double coordX;
    private Double coordY;

    private PropietarioDTO propietario;

}
