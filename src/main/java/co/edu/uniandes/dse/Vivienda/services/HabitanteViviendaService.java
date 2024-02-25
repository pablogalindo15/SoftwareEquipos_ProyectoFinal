package co.edu.uniandes.dse.Vivienda.services;

import java.util.Optional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.Vivienda.repositories.HabitanteRepository;
import co.edu.uniandes.dse.Vivienda.repositories.ViviendaRepository;
import lombok.extern.slf4j.Slf4j;
import co.edu.uniandes.dse.Vivienda.entities.HabitanteEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import javax.transaction.Transactional;




@Slf4j
@Service

public class HabitanteViviendaService {

        @Autowired
        private HabitanteRepository habitanteRepository;

        @Autowired
        private ViviendaRepository viviendaRepository;

        @Transactional
        public HabitanteEntity addHabitante(Long viviendaId, Long habitanteId) throws EntityNotFoundException {
            log.info("Inicia proceso de agregarle un habitante a una vivienda con id = {0}", viviendaId);

            Optional<HabitanteEntity> HabitanteEntity = habitanteRepository.findById(habitanteId);
            if(HabitanteEntity.isEmpty())
                throw new EntityNotFoundException("Habitante not found");

            Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
            if(viviendaEntity.isEmpty())
                throw new EntityNotFoundException("Vivienda not found");

            viviendaEntity.get().getHabitantes_actuales().add(HabitanteEntity.get());
            HabitanteEntity.get().setVivienda(viviendaEntity.get());
            log.info("Termina proceso de agregarle un habitante a una vivienda con id = {0}", viviendaId);
            return HabitanteEntity.get();


            }

        @Transactional
        public void removeHabitante(Long viviendaId, Long habitanteId) throws EntityNotFoundException {
            log.info("Inicia proceso de removerle un habitante a una vivienda con id = {0}", viviendaId);

            Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
            if(habitanteEntity.isEmpty())
                throw new EntityNotFoundException("Habitante not found");

            Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(habitanteEntity.get().getVivienda().getId());
            viviendaEntity.ifPresent(vivienda -> vivienda.getHabitantes_actuales().remove(habitanteEntity.get())); 
            habitanteEntity.get().setVivienda(null);
            log.info("Termina proceso de removerle un habitante a una vivienda con id = {0}", viviendaId);
            }    
            
        @Transactional
        public ViviendaEntity getVivienda(Long habitanteId) throws EntityNotFoundException {
            log.info("Inicia proceso de consultar la vivienda de un habitante con id = {0}", habitanteId);
            Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
            if(habitanteEntity.isEmpty())
                throw new EntityNotFoundException("Habitante not found");
            log.info("Termina proceso de consultar la vivienda de un habitante con id = {0}", habitanteId);
            return habitanteEntity.get().getVivienda();
        }

        @Transactional
        public HabitanteEntity getHabitante(Long viviendaId) throws EntityNotFoundException {
            log.info("Inicia proceso de consultar el habitante de una vivienda con id = {0}", viviendaId);
            Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
            if(viviendaEntity.isEmpty())
                throw new EntityNotFoundException("Vivienda not found");
            log.info("Termina proceso de consultar el habitante de una vivienda con id = {0}", viviendaId);
            return viviendaEntity.get().getHabitantes_actuales().get(0);
        }


        @Transactional
        public HabitanteEntity replaceHabitante(Long viviendaId, Long habitanteId) throws EntityNotFoundException {
            log.info("Inicia proceso de reemplazar el habitante de una vivienda con id = {0}", viviendaId);
            Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
            if(habitanteEntity.isEmpty())
                throw new EntityNotFoundException("Habitante not found");

            Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
            if(viviendaEntity.isEmpty())
                throw new EntityNotFoundException("Vivienda not found");

            viviendaEntity.get().getHabitantes_actuales().add(habitanteEntity.get());
            habitanteEntity.get().setVivienda(viviendaEntity.get());
            log.info("Termina proceso de reemplazar el habitante de una vivienda con id = {0}", viviendaId);
            return habitanteEntity.get();
        }


}   

