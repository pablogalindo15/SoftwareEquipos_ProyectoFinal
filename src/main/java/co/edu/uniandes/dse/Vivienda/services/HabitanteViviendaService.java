package co.edu.uniandes.dse.Vivienda.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.Vivienda.repositories.HabitanteRepository;
import co.edu.uniandes.dse.Vivienda.repositories.ViviendaRepository;
import lombok.extern.slf4j.Slf4j;
import co.edu.uniandes.dse.Vivienda.entities.HabitanteEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;





@Slf4j
@Service

public class HabitanteViviendaService {

        @Autowired
        private HabitanteRepository habitanteRepository;

        @Autowired
        private ViviendaRepository viviendaRepository;

        /*
         * Se asocia una vivienda con el habitante
         */
        @Transactional
        public ViviendaEntity addVivienda(Long viviendaId, Long habitanteId) throws EntityNotFoundException{
            log.info("Inicia proceso de asociar la vivienda con id = {0} al habitante con id = " + habitanteId, viviendaId);
            Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
            if (viviendaEntity.isEmpty()) throw new EntityNotFoundException("Vivienda not found");
            Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
            if (habitanteEntity.isEmpty()) throw new EntityNotFoundException("Habitante not found");
            habitanteEntity.get().setVivienda(viviendaEntity.get());
            log.info("Termina proceso de asociar la vivienda con id = {0} al habitante con id = {1}", viviendaId, habitanteId);
            return viviendaEntity.get();
        }


        /*
         * Se obtiene la vivienda de un habitante
         */
        @Transactional
        public ViviendaEntity getVivienda(Long habitanteId) throws EntityNotFoundException{
            log.info("Inicia proceso de consultar la vivienda del habitante con id = {0}", habitanteId);
            Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
            if (habitanteEntity.isEmpty()) throw new EntityNotFoundException("Habitante not found");
            if (habitanteEntity.get().getVivienda() == null) throw new EntityNotFoundException("El habitante no tiene vivienda");
            log.info("Termina proceso de consultar la vivienda del habitante con id = {0}", habitanteId);
            return habitanteEntity.get().getVivienda();
        }

        /*
         * Se elimina una vivienda del habitante
         */
        @Transactional
        public void removeVivienda(Long habitanteId) throws EntityNotFoundException{
            log.info("inicia el proceso de borrar la vivienda del habitante con id = {0}", habitanteId);
            Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
            if (habitanteEntity.isEmpty()) throw new EntityNotFoundException("Habitante not found");
            if (habitanteEntity.get().getVivienda() == null) throw new EntityNotFoundException("El habitante no tiene vivienda");
            Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(habitanteEntity.get().getVivienda().getId());

            viviendaEntity.ifPresent(vivienda->{
                habitanteEntity.get().setVivienda(null);
                vivienda.getHabitantes_actuales().remove(habitanteEntity.get());
            });

            log.info("termina el proceso de eliminar la vivienda del habitante con id = " + habitanteId);
        }

        /*
         * Se actualiza la vivienda de un habitante
         */
        @Transactional
        public ViviendaEntity updateVivienda(Long habitanteId, Long viviendaId) throws EntityNotFoundException {
            log.info("Inicia proceso de actualizar la vivienda de un habitante con id = {0}", habitanteId);
            Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
            if(habitanteEntity.isEmpty())
                throw new EntityNotFoundException("Habitante not found");
            Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
            if(viviendaEntity.isEmpty())
                throw new EntityNotFoundException("Vivienda not found");
            habitanteEntity.get().setVivienda(viviendaEntity.get());
            log.info("Termina proceso de actualizar la vivienda de un habitante con id = {0}", habitanteId);
            return viviendaEntity.get();
        }

        /*
         * Se reemplaza la vivienda de un habitante
         */
        @Transactional
        public ViviendaEntity replaceVivienda(Long habitanteId, Long viviendaId) throws EntityNotFoundException {
            log.info("Inicia proceso de reemplazar la vivienda de un habitante con id = {0}", habitanteId);
            Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
            if(habitanteEntity.isEmpty())
                throw new EntityNotFoundException("Habitante not found");
            Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
            if(viviendaEntity.isEmpty())
                throw new EntityNotFoundException("Vivienda not found");
            habitanteEntity.get().setVivienda(viviendaEntity.get());
            log.info("Termina proceso de reemplazar la vivienda de un habitante con id = {0}", habitanteId);
            return viviendaEntity.get();
        }

        /*
         * Se obtienen todas las viviendas de un habitante
         */
        @Transactional
        public List<ViviendaEntity> getViviendas(Long habitanteId) throws EntityNotFoundException {
            log.info("Inicia proceso de consultar las viviendas de un habitante con id = {0}", habitanteId);
            Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
            if(habitanteEntity.isEmpty())
                throw new EntityNotFoundException("Habitante not found");
            log.info("Termina proceso de consultar las viviendas de un habitante con id = {0}", habitanteId);
            return habitanteEntity.get().getViviendas();
        }

        /*
         * Se reemplazan las viviendas de un habitante
         */
        @Transactional
        public List<ViviendaEntity> replaceViviendas(Long habitanteId, List<ViviendaEntity> viviendas) throws EntityNotFoundException {
            log.info("Inicia proceso de reemplazar las viviendas de un habitante con id = {0}", habitanteId);
            Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
            if(habitanteEntity.isEmpty())
                throw new EntityNotFoundException("Habitante not found");
            habitanteEntity.get().setViviendas(viviendas);
            log.info("Termina proceso de reemplazar las viviendas de un habitante con id = {0}", habitanteId);
            return viviendas;
        }

        


        
        

}   

