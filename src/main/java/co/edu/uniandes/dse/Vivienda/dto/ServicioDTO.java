package co.edu.uniandes.dse.Vivienda.dto;

import lombok.Data;

@Data
public class ServicioDTO {
    public String nombre;
    public Float costoAdicional;
    public enum tipoServicio {parqueadero, gimansio, lavanderia, deposito, restaurante};
    public tipoServicio tipServicio;
}

