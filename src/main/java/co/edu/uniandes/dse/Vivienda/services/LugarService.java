package co.edu.uniandes.dse.Vivienda.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.Vivienda.entities.LugarEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.entities.LugarEntity.tipoLugar;

import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.LugarRepository;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class LugarService {
    @Autowired
    private LugarRepository LugarRepository;
    @Transactional
    public LugarEntity createLugar(LugarEntity lugarEntity)throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creación del lugar");
        // Realizar validaciones
        if (lugarEntity == null) {
            throw new IllegalOperationException("La entidad lugar no puede ser nula");
            }
        if (lugarEntity.getNombre() == null || lugarEntity.getNombre().isEmpty()) {
            throw new IllegalOperationException("El nombre no es válido");
            }
        if (lugarEntity.getCoordenadaX() == null || lugarEntity.getCoordenadaY() == null) {
            throw new IllegalOperationException("Las coordenadas no son válidas");
             }
        
        if (lugarEntity.getCoordenadaX()==null | lugarEntity.getCoordenadaY()==null){
            throw new IllegalOperationException("Las coordenadas no son validas");}
        if (lugarEntity.getFotos().isEmpty()){
            throw new IllegalOperationException("La foto no es valida");}
        if (!(lugarEntity.getGratis()|!lugarEntity.getGratis())){
            throw new IllegalOperationException("No es valido el valor de gratis");}
        if (lugarEntity.getPrecioMin()==null | lugarEntity.getPrecioMax()==null){
            throw new IllegalOperationException("Las coordenadas no son validas");}
        if (lugarEntity.getTiempoLlegada()==0){
            throw new IllegalOperationException("El tiempo de llegada no es valido");}
        if ((lugarEntity.getTipo() instanceof tipoLugar) != true){
            throw new IllegalOperationException(("El tipo no es de un tipo esperado"));
        }
        //commo probar enum.
        LugarEntity savedLugarEntity = LugarRepository.save(lugarEntity);

        log.info("Termina proceso de creación del lugar");
        return savedLugarEntity;}
        
    public List<LugarEntity> getLugares(){
        log.info("Inicia proceso de consultar todos los lugares");
        return LugarRepository.findAll();
    }
    public LugarEntity getLugar(Long lugarId)throws EntityNotFoundException{
        log.info("Inicia proceso de consultar el lugar con id = {0}", lugarId);
        Optional<LugarEntity> lugarEntity = LugarRepository.findById(lugarId);
        if (lugarEntity.isEmpty())
                throw new EntityNotFoundException("No se encontro el lugar");
        log.info("Termina proceso de consultar el lugarcon id = {0}", lugarId);
        return lugarEntity.get();
    }
    
    public LugarEntity updateLugar(Long lugarId, LugarEntity lugar)throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de actualizar el lugar con id = {0}", lugarId);
        Optional<LugarEntity> lugarEntity = LugarRepository.findById(lugarId);
        if (lugarEntity.isEmpty())
                throw new EntityNotFoundException("No se encontro el lugar");
        if (lugar.getNombre()==null){
                throw new IllegalOperationException("El nombre no es valido");}
        if (lugar.getNombre().isEmpty()){
                throw new IllegalOperationException("El nombre no es valido");}
        lugar.setId(lugarId);
        log.info("Termina proceso de actualizar el lugar con id = {0}", lugarId);
        return LugarRepository.save(lugar);
    }
    
    public void deleteLugar(Long lugarId)throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de borrar el libro con id = {0}", lugarId);
        Optional<LugarEntity> lugarEntity = LugarRepository.findById(lugarId);
        if (lugarEntity.isEmpty())
                throw new EntityNotFoundException("No se encontro el lugar");

        
        List<ViviendaEntity> viviendas = lugarEntity.get().getViviendas_cercanas();

        if (!viviendas.isEmpty())
                throw new IllegalOperationException("Unable to delete lugar because it has associated viviendas");

        LugarRepository.deleteById(lugarId);
        log.info("Termina proceso de borrar el lugar con id = {0}", lugarId);
    }

}

