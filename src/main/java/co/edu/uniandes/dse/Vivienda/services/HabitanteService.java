package co.edu.uniandes.dse.Vivienda.services;
import javax.transaction.Transactional;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.Vivienda.entities.HabitanteEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.HabitanteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service 
public class HabitanteService {

    @Autowired
    private HabitanteRepository HabitanteRepository;
    @Transactional
    public HabitanteEntity createHabitante(HabitanteEntity habitante)throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creación de habitante");

        if (habitante.getNombre()==null){
            throw new IllegalOperationException("El nombre no es valido");}
        if (habitante.getNombre()==""){
            throw new IllegalOperationException("El nombre no es valido");}    
        if (habitante.getCedula() == 0){
            throw new IllegalOperationException("La cedula no es valida");}
        
        
        //if (!HabitanteRepository.findById(habitante.getId()).isEmpty()){
        //        throw new IllegalOperationException("El Id indicado ya se encuentra registrado en la base de datos.");}
        HabitanteEntity newHabitanteEntity = HabitanteRepository.save(habitante);
        log.info("Termina proceso de creación de habitante");
        return newHabitanteEntity;
    }
    public HabitanteEntity getHabitante(Long habitanteId)throws EntityNotFoundException{
        log.info("Inicia proceso de consultar el habitante con id = {0}", habitanteId);
        Optional<HabitanteEntity> HabitanteEntity = HabitanteRepository.findById(habitanteId);
        if (HabitanteRepository.findById(habitanteId).isEmpty()){
            throw new EntityNotFoundException("No se encontro el habitante");
        }
        log.info("Termina proceso de consultar el habitante con id = {0}", habitanteId);
        return HabitanteEntity.get();
    }
    
    public Collection<HabitanteEntity> getHabitantes(){
        log.info("Inicia proceso de consultar todos los habitantes");
        Collection<HabitanteEntity> habitantes = HabitanteRepository.findAll();
        log.info("Termina proceso de consultar todos los habitantes");
        return habitantes;
    }



    public HabitanteEntity updateHabitante(Long habitanteId, HabitanteEntity habitante)throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de actualizar el habitante con id = {0}", habitanteId);
        if (HabitanteRepository.findById(habitanteId).isEmpty()){
            throw new EntityNotFoundException("No se encontro el habitante");
        }
        if (habitante.getNombre()==null){
            throw new IllegalOperationException("El nombre no es valido");}
        if (habitante.getNombre()==""){
            throw new IllegalOperationException("El nombre no es valido");}
        HabitanteEntity newHabitanteEntity = HabitanteRepository.save(habitante);
        log.info("Termina proceso de actualizar el habitante con id = {0}", habitanteId);
        return newHabitanteEntity;
    }
    public void deleteHabitante(Long habitanteId)throws EntityNotFoundException{
        log.info("Inicia proceso de borrar el habitante con id = {0}", habitanteId);
        if (HabitanteRepository.findById(habitanteId).isEmpty()){
            throw new EntityNotFoundException("No se encontro el habitante");
        }
        HabitanteRepository.deleteById(habitanteId);
        log.info("Termina proceso de borrar el habitante con id = {0}", habitanteId);
    }




    

    
    
}
