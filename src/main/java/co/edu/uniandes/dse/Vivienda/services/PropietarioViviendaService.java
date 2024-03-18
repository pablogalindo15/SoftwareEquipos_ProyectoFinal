package co.edu.uniandes.dse.Vivienda.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.Vivienda.entities.PropietarioEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.PropietarioRepository;
import co.edu.uniandes.dse.Vivienda.repositories.ViviendaRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class PropietarioViviendaService {
    
        @Autowired
        private PropietarioRepository propietarioRepository;

        @Autowired
        private ViviendaRepository viviendaRepository;

        @Transactional
        public PropietarioEntity addViviendaToPropietario(Long viviendaId, Long propietarioId) throws EntityNotFoundException {
            log.info("Inicia proceso de agregarle una vivienda a un propietario con id = {0}", propietarioId);

            Optional<ViviendaEntity> ViviendaEntity = viviendaRepository.findById(viviendaId);
            if(ViviendaEntity.isEmpty())
                throw new EntityNotFoundException("Vivienda not found");

            Optional<PropietarioEntity> propietarioEntity = propietarioRepository.findById(propietarioId);
            if(propietarioEntity.isEmpty())
                throw new EntityNotFoundException("Propietario not found");
        
            propietarioEntity.get().getViviendas().add(ViviendaEntity.get());
            log.info("Termina proceso de agregarle un habitante a una vivienda con id = {0}", viviendaId);
            return propietarioEntity.get();
            }

        @Transactional
        public void removeViviendaFromPropietario(Long viviendaId, Long propietarioId) throws EntityNotFoundException {
            log.info("Inicia proceso de removerle una vivienda a un Propietario con id = {0}", propietarioId);

            Optional<ViviendaEntity> ViviendaEntity = viviendaRepository.findById(viviendaId);
            if(ViviendaEntity.isEmpty())
                throw new EntityNotFoundException("Vivienda not found");

            Optional<PropietarioEntity> propietarioEntity = propietarioRepository.findById(propietarioId);
            if(propietarioEntity.isEmpty())
                throw new EntityNotFoundException("Propietario not found");

            propietarioEntity.get().getViviendas().remove(ViviendaEntity.get());
            log.info("Termina proceso de removerle una vivienda a un propietario con id = {0}", propietarioId);
            }
        
        @Transactional
        public List<ViviendaEntity> getViviendas (long propietarioId) throws EntityNotFoundException {
            log.info("Inicia proceso de consultar todas las viviendas de un propietario con id = {0}", propietarioId);

            Optional<PropietarioEntity> propietarioEntity = propietarioRepository.findById(propietarioId);
            if(propietarioEntity.isEmpty())
                throw new EntityNotFoundException("Propietario not found");

            log.info("Termina proceso de consultar todas las viviendas de un propeitario con id = {0}", propietarioId);
            return propietarioEntity.get().getViviendas();
        
        }

       @Transactional
	public ViviendaEntity getVivienda(Long propietarioId, Long viviendaId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar la vivienda con id = {0} del propietario con id = " + propietarioId, viviendaId);
		
		Optional<PropietarioEntity> propietarioEntity = propietarioRepository.findById(propietarioId);
		if(propietarioEntity.isEmpty())
			throw new EntityNotFoundException("Propietario not found");
		
		Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
		if(viviendaEntity.isEmpty())
			throw new EntityNotFoundException("Vivienda not found");
				
		log.info("Termina proceso de consultar la vivienda con id = {0} del propietario con id = " + propietarioId, viviendaId);
		
		if(!propietarioEntity.get().getViviendas().contains(viviendaEntity.get()))
			throw new IllegalOperationException("La vivienda no está asociada al propietario");
		
		return viviendaEntity.get();
	}

    @Transactional
	public List<ViviendaEntity> replaceViviendas(Long propietarioId, List<ViviendaEntity> viviendas) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar las viviendas de un propietario con id = {0}", propietarioId);
		Optional<PropietarioEntity> propietarioEntity = propietarioRepository.findById(propietarioId);
		if(propietarioEntity.isEmpty())
			throw new EntityNotFoundException("Propietario not found");
		
		for(ViviendaEntity vivienda : viviendas) {
			Optional<ViviendaEntity> b = viviendaRepository.findById(vivienda.getId());
			if(b.isEmpty())
				throw new EntityNotFoundException("Vivienda not found");
			
			b.get().setPropietario(propietarioEntity.get());
		}		
		return viviendas;
	}



}
