package co.edu.uniandes.dse.Vivienda.dto;
import lombok.Data;

@Data
public class LugarDTO {
    private Long id;
    private String nombre;
    private String fotos;
    private Integer tiempoLlegada;
    private Boolean gratis;
    private Double precioMin;
    private Double precioMax;
    private Double coordenadaX;
    private Double coordenadaY;
    public enum tipoLugar {Universidad, Restaurante, Supermercado, Parque, CentroComercial }
    private tipoLugar tipo;
}
