package co.edu.uniandes.dse.Vivienda.services;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.Vivienda.entities.PropietarioEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.repositories.PropietarioRepository;
import co.edu.uniandes.dse.Vivienda.repositories.ViviendaRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ViviendaPropietarioService {
    @Autowired
    private PropietarioRepository propietarioRepository;

    @Autowired
    private ViviendaRepository viviendaRepository;

    @Transactional
    public ViviendaEntity addPropietarioToVivienda(Long viviendaId, Long propietarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de agregar un propietario a una vivienda con id = {0}", viviendaId);

        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if(viviendaEntity.isEmpty())
            throw new EntityNotFoundException("Vivienda not found");

        Optional<PropietarioEntity> propietarioEntity = propietarioRepository.findById(propietarioId);
        if(propietarioEntity.isEmpty())
            throw new EntityNotFoundException("Propietario not found");
    
        viviendaEntity.get().setPropietario(propietarioEntity.get());
        log.info("Termina proceso de agregarle un habitante a una vivienda con id = {0}", viviendaId);
        return viviendaEntity.get();
        }

    @Transactional
    public void removePropietarioFromVivienda(Long viviendaId, Long propietarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de removerle el propietario a una vivienda con id = {0}", viviendaId);

        Optional<ViviendaEntity> ViviendaEntity = viviendaRepository.findById(viviendaId);
        if(ViviendaEntity.isEmpty())
            throw new EntityNotFoundException("Vivienda not found");

        Optional<PropietarioEntity> propietarioEntity = propietarioRepository.findById(propietarioId);
        if(propietarioEntity.isEmpty())
            throw new EntityNotFoundException("Propietario not found");

        ViviendaEntity.get().setPropietario(null);
        log.info("Termina proceso de removerle el propietario a una vivienda con id = {0}", viviendaId);
            }

    @Transactional
    public PropietarioEntity getPropietario (long viviendaId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el propietario de una vivienda con id = {0}", viviendaId);
    
        Optional<ViviendaEntity> ViviendaEntity = viviendaRepository.findById(viviendaId);
        if(ViviendaEntity.isEmpty())
            throw new EntityNotFoundException("Vivienda not found");
    
        log.info("Termina proceso de consultar el propietario de una vivienda con id = {0}", viviendaId);
        return ViviendaEntity.get().getPropietario();
            
            }
}

