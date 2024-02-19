package co.edu.uniandes.dse.Vivienda.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity.tipoVivienda;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.ViviendaRepository;

@Service
public class ViviendaService {
    // atributos
    @Autowired
    private ViviendaRepository viviendaRepository;

    //metodos
    @Transactional
    public ViviendaEntity createVivienda(ViviendaEntity viviendaEntity) throws EntityNotFoundException, IllegalOperationException{

        // verificacion de atributos propios

        if (viviendaEntity.getNombre() == null || viviendaEntity.getNombre() == "" || viviendaEntity.getNombre() == " "){
            throw new IllegalOperationException(("La vivienda no tiene nombre"));
        }

        if (viviendaEntity.getPrecio() == 0.0 || viviendaEntity.getPrecio() == 0 || viviendaEntity.getPrecio() == 0.00){
            throw new IllegalOperationException(("La vivienda no tiene precio"));
        }

        if (viviendaEntity.getDescripcion() == null || viviendaEntity.getDescripcion() == "" || viviendaEntity.getDescripcion() == " "){
            throw new IllegalOperationException(("La vivienda no tiene descripcion"));
        }

        if (viviendaEntity.getFotos() == null || viviendaEntity.getFotos() == "" || viviendaEntity.getFotos() == " "){
            throw new IllegalOperationException(("La vivienda no tiene fotos"));
        }

        if (viviendaEntity.getTamano() == 0 || viviendaEntity.getTamano() == null){
            throw new IllegalOperationException(("La vivienda no tiene tamanio especificado"));
        }

        if (viviendaEntity.getEstrato() == 0 || viviendaEntity.getEstrato() == null){
            throw new IllegalOperationException(("La vivienda no tiene estrato especificado"));
        }

        if (viviendaEntity.getRestricciones() == null || viviendaEntity.getRestricciones() == "" || viviendaEntity.getRestricciones() == " "){
            throw new IllegalOperationException(("La vivienda no tiene restricciones"));
        }

        if (viviendaEntity.getContacto() == null || viviendaEntity.getContacto() == "" || viviendaEntity.getContacto() == " "){
            throw new IllegalOperationException(("La vivienda no tiene contacto"));
        }

        if (viviendaEntity.getDireccion() == null || viviendaEntity.getDireccion() == "" || viviendaEntity.getDireccion() == " "){
            throw new IllegalOperationException(("La vivienda no tiene direccion"));
        }

        if (viviendaEntity.getOcupada() != true && viviendaEntity.getOcupada()!= false){
            throw new IllegalOperationException(("La vivienda no tiene especificado el estado de su ocupacion"));
        }

        if (viviendaEntity.getCoordX()== 0){
            throw new IllegalOperationException(("La vivienda no tiene COORD X especificada"));
        }

        if (viviendaEntity.getCoordY()== 0){
            throw new IllegalOperationException(("La vivienda no tiene COORD Y especificada"));
        }

        if ((viviendaEntity.getTipo() instanceof tipoVivienda) != true){
            throw new IllegalOperationException(("La vivienda no es de un tipo esperado"));
        }

        // verificiacion de atributos de relaciones

        return viviendaRepository.save(viviendaEntity);
   
    }

    public Collection<ViviendaEntity> getViviendas(){
        return viviendaRepository.findAll();
        
    }

    public ViviendaEntity getVivienda(Long id) throws EntityNotFoundException, IllegalOperationException{

        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(id);
        if (viviendaEntity.isEmpty()){
            throw new EntityNotFoundException("NO SE ENCONTRO LA VIVIENDA");
        }

        return viviendaEntity.get();


    }

    public ViviendaEntity updateVivienda(Long id, ViviendaEntity vivienda) throws EntityNotFoundException, IllegalOperationException{
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(id);
        if (viviendaEntity.isEmpty()){
            throw new EntityNotFoundException("NO SE ENCONTRO LA VIVIENDA");
        }

        if (vivienda.getNombre() == null || vivienda.getNombre() == "" || vivienda.getNombre() == " "){
            throw new IllegalOperationException(("La vivienda no tiene nombre"));
        }

        vivienda.setId(id);

        return viviendaRepository.save(vivienda);

    }

    public void deleteVivienda(Long id) throws EntityNotFoundException{

        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(id);
        if (viviendaEntity.isEmpty()){
            throw new EntityNotFoundException("NO SE ENCONTRO LA VIVIENDA");
        }
        viviendaRepository.deleteById(id);

    }
    
}
