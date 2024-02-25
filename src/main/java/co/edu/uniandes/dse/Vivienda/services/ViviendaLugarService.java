package co.edu.uniandes.dse.Vivienda.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.Vivienda.entities.LugarEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.LugarRepository;
import co.edu.uniandes.dse.Vivienda.repositories.ViviendaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ViviendaLugarService {
    @Autowired
    private ViviendaRepository viviendaRepository;

    @Autowired
    private LugarRepository lugarRepository;

    @Transactional
    public LugarEntity addLugar(Long viviendaId, Long lugarId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociarle un lugar una vivienda con id = {0}", viviendaId);
        Optional<LugarEntity> lugarEntity = lugarRepository.findById(lugarId);
        if (lugarEntity.isEmpty())
            throw new EntityNotFoundException("El lugar esta vacio.");

        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        viviendaEntity.get().getLugarDeInteres_cercano().add(lugarEntity.get());
        log.info("Termina proceso de asociarle un lugar una vivienda con id = {0}", viviendaId);
        return lugarEntity.get();
    }
    @Transactional
    public List<LugarEntity> getLugares(Long viviendaId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar todos los lugares de la vivienda con id = {0}", viviendaId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");
        log.info("Finaliza proceso de consultar todos los lugares de la vivienda con id = {0}", viviendaId);
        return viviendaEntity.get().getLugarDeInteres_cercano();
    }
    @Transactional
    public LugarEntity getLugar(Long viviendaId, Long lugarId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia pproceso de consultar un lugar con una vivienda con id = {0}", viviendaId);
        Optional<LugarEntity> lugarEntity = lugarRepository.findById(lugarId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);

        if (lugarEntity.isEmpty())
            throw new EntityNotFoundException("El lugar esta vacio.");

        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");
        log.info("Termina proceso de consultar un lugar con una vivienda con id = {0}", viviendaId);
        if (!viviendaEntity.get().getLugarDeInteres_cercano().contains(lugarEntity.get()))
            throw new IllegalOperationException("The lugar is not associated to the vivienda");
        
        return lugarEntity.get();
    }
    @Transactional
    public List<LugarEntity> replaceLugares(Long viviendaId, List<LugarEntity> list) throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar los lugares de la vivienda con id = {0}", viviendaId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        for (LugarEntity Lugar : list) {
            Optional<LugarEntity> LugarEntity = lugarRepository.findById(Lugar.getId());
            if (LugarEntity.isEmpty())
                throw new EntityNotFoundException("El lugar esta vacio.");

            if (!viviendaEntity.get().getLugarDeInteres_cercano().contains(LugarEntity.get()))
                viviendaEntity.get().getLugarDeInteres_cercano().add(LugarEntity.get());
        }
        log.info("Termina proceso de reemplazar los lugares de la vivienda con id = {0}", viviendaId);
        return getLugares(viviendaId);
    }

    @Transactional
    public void removeLugar(Long viviendaId, Long LugarId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar un lugar de la vivienda con id = {0}", viviendaId);
        Optional<LugarEntity> LugarEntity = lugarRepository.findById(LugarId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);

        if (LugarEntity.isEmpty())
            throw new EntityNotFoundException("El lugar esta vacio.");

        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        viviendaEntity.get().getLugarDeInteres_cercano().remove(LugarEntity.get());

        log.info("Termina proceso de borrar un lugar de la vivienda con id = {0}", viviendaId);
    }
}


