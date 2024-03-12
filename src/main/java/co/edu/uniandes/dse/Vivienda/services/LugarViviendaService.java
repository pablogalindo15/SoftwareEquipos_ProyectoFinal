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
public class LugarViviendaService {
    @Autowired
    private ViviendaRepository viviendaRepository;

    @Autowired
    private LugarRepository lugarRepository;

    @Transactional
    public ViviendaEntity addVivienda(Long lugarId, Long viviendaId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociarle una vivienda a un lugar con id = {0}", lugarId);
        Optional<LugarEntity> lugarEntity = lugarRepository.findById(lugarId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        if (lugarEntity.isEmpty())
            throw new EntityNotFoundException("El lugar esta vacio.");

        viviendaEntity.get().getLugarDeInteres_cercano().add(lugarEntity.get());
        log.info("Termina proceso de asociarle una vivienda a un lugar con id = {0}", lugarId);
        return viviendaEntity.get();
    }

    @Transactional
    public List<ViviendaEntity> getViviendas(Long lugarId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar todos las viviendas del lugar con id = {0}", lugarId);
        Optional<LugarEntity> lugarEntity = lugarRepository.findById(lugarId);
        if (lugarEntity.isEmpty())
            throw new EntityNotFoundException("El lugar esta vacio.");

        log.info("Termina proceso de consultar todas las viviendas del lugar con id = {0}", lugarId);
        return lugarEntity.get().getViviendas_cercanas();
    }

    @Transactional
    public ViviendaEntity getVivienda(Long lugarId, Long viviendaId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de consultar la vivienda con id = {0} del lugar con id = " + lugarId, viviendaId);
        Optional<LugarEntity> lugarEntity = lugarRepository.findById(lugarId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);

        if (lugarEntity.isEmpty())
            throw new EntityNotFoundException("El lugar esta vacio.");

        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        log.info("Termina proceso de consultar la vivienda con id = {0} del lugar con id = " + lugarId, viviendaId);
        if (!viviendaEntity.get().getLugarDeInteres_cercano().contains(lugarEntity.get()))
            throw new IllegalOperationException("The vivienda is not associated to the lugar");
        
        return viviendaEntity.get();
    }

    @Transactional
    public List<ViviendaEntity> addViviendas(Long lugarId, List<ViviendaEntity> viviendas) throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar las viviendas asociados al lugar con id = {0}", lugarId);
        Optional<LugarEntity> lugarEntity = lugarRepository.findById(lugarId);
        if (lugarEntity.isEmpty())
            throw new EntityNotFoundException("El lugar esta vacio.");

        for (ViviendaEntity vivienda : viviendas) {
            Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(vivienda.getId());
            if (viviendaEntity.isEmpty())
                throw new EntityNotFoundException("La vivienda esta vacia.");

            if (!viviendaEntity.get().getLugarDeInteres_cercano().contains(lugarEntity.get()))
                viviendaEntity.get().getLugarDeInteres_cercano().add(lugarEntity.get());
        }
        log.info("Finaliza proceso de reemplazar las viviendas asociados al lugar con id = {0}", lugarId);
        lugarEntity.get().setViviendas_cercanas(viviendas);
        return lugarEntity.get().getViviendas_cercanas();
    }

    @Transactional
    public void removeVivienda(Long lugarId, Long viviendaId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar una vivienda de un lugar con id = {0}", lugarId);
        Optional<LugarEntity> lugarEntity = lugarRepository.findById(lugarId);

        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        if (lugarEntity.isEmpty())
            throw new EntityNotFoundException("El lugar esta vacio.");


        viviendaEntity.get().getLugarDeInteres_cercano().remove(lugarEntity.get());
        lugarEntity.get().getViviendas_cercanas().remove(viviendaEntity.get());
        log.info("Finaliza proceso de borrar un lugar de la vivienda con id = {0}", lugarId);
    }
}
    



