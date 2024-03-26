package co.edu.uniandes.dse.Vivienda.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.Vivienda.entities.HabitanteEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.HabitanteRepository;
import co.edu.uniandes.dse.Vivienda.repositories.ViviendaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
/*
 * Segunda relacion con Habitantes, donde se modelan el historial de una vivienda
 */
public class ViviendaHabitante2Service {
    @Autowired
    private HabitanteRepository habitanteRepository;

    @Autowired
    private ViviendaRepository viviendaRepository;

    @Transactional
    public HabitanteEntity addHabitante(Long viviendaId, Long habitanteId) throws EntityNotFoundException {
        log.info("Inicia proceso de agregarle un habitante al historial de una vivienda con id = {0}", viviendaId);

        Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
        if(habitanteEntity.isEmpty())
            throw new EntityNotFoundException("Habitante not found");

        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if(viviendaEntity.isEmpty())
            throw new EntityNotFoundException("Vivienda not found");

        viviendaEntity.get().getHistorial().add(habitanteEntity.get());
        habitanteEntity.get().getViviendas().add(viviendaEntity.get());
        log.info("Termina proceso de agregarle un habitante al historial de una vivienda con id = {0}", viviendaId);
        return habitanteEntity.get();

        }

    @Transactional
    public HabitanteEntity getHabitante(Long viviendaId, Long habitanteId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de consultar un habitante del historial de la vivienda con id = {0}", viviendaId);
        Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);

        if (habitanteEntity.isEmpty())
            throw new EntityNotFoundException("El habitante esta vacio.");

        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");
        log.info("Termina proceso de consultar un habitante del historial de una vivienda con id = {0}", viviendaId);
        if (!viviendaEntity.get().getHistorial().contains(habitanteEntity.get()))
            throw new IllegalOperationException("The habitante is not associated to the vivienda");
        
        return habitanteEntity.get();
    }

    @Transactional
    public List<HabitanteEntity> getHabitantes(Long viviendaId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar todos los habitantes del historial de la vivienda con id = {0}", viviendaId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");
        log.info("Finaliza proceso de consultar todos los habitantes del historial de la vivienda con id = {0}", viviendaId);
        return viviendaEntity.get().getHistorial();
    }

    @Transactional
    public List<HabitanteEntity> replaceHabitantes(Long viviendaId, List<HabitanteEntity> list) throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar los habitantes del historial de la vivienda con id = {0}", viviendaId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        for (HabitanteEntity habitante : list) {
            Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitante.getId());
            if (habitanteEntity.isEmpty())
                throw new EntityNotFoundException("El habitante esta vacio.");

            if (!viviendaEntity.get().getHistorial().contains(habitanteEntity.get()))
                viviendaEntity.get().getHistorial().add(habitanteEntity.get());
        }
        log.info("Termina proceso de reemplazar los habitantes del historial de la vivienda con id = {0}", viviendaId);
        return getHabitantes(viviendaId);}

    @Transactional
    public void removeHabitante(Long viviendaId, Long habitanteId) throws EntityNotFoundException, NoSuchElementException {
        log.info("Inicia proceso de removerle un habitante del historial a una vivienda con id = {0}", viviendaId);

        Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);

        if(habitanteEntity.isEmpty())
            throw new EntityNotFoundException("Habitante not found");
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        viviendaEntity.get().getHistorial().remove(habitanteEntity.get()); 
        log.info("Termina proceso de removerle un habitante del historial a una vivienda con id = {0}", viviendaId);
        }    

}
