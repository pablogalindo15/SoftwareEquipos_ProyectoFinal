package co.edu.uniandes.dse.Vivienda.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.Vivienda.entities.ServicioEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.ServicioRepository;
import co.edu.uniandes.dse.Vivienda.repositories.ViviendaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ViviendaServicioService {
    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ViviendaRepository viviendaRepository;

    @Transactional
    public ServicioEntity addServicio (Long viviendaId, Long servicioId) throws EntityNotFoundException {
        log.info("Inicia proceso de agregarle un servicio a una vivienda con id = {0}", viviendaId);

        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if(servicioEntity.isEmpty())
            throw new EntityNotFoundException("Servicio not found");

        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if(viviendaEntity.isEmpty())
            throw new EntityNotFoundException("Vivienda not found");

        viviendaEntity.get().getServiciosVivienda().add(servicioEntity.get());
        servicioEntity.get().getViviendasServicio().add(viviendaEntity.get());
        log.info("Termina proceso de agregarle un servicio a una vivienda con id = {0}", viviendaId);
        return servicioEntity.get();

        }

    @Transactional
    public ServicioEntity getServicio (Long viviendaId, Long servicioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia pproceso de consultar un servicio con una vivienda con id = {0}", viviendaId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);

        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException("El servicio esta vacio.");

        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");
        log.info("Termina proceso de consultar un servicio con una vivienda con id = {0}", viviendaId);
        if (!viviendaEntity.get().getServiciosVivienda().contains(servicioEntity.get()))
            throw new IllegalOperationException("The servicio is not associated to the vivienda");
        
        return servicioEntity.get();
    }

    @Transactional
    public List<ServicioEntity> getServicios(Long viviendaId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar todos los servicios de la vivienda con id = {0}", viviendaId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");
        log.info("Finaliza proceso de consultar todos los servicios de la vivienda con id = {0}", viviendaId);
        return viviendaEntity.get().getServiciosVivienda();
    }

    @Transactional
    public List<ServicioEntity> replaceServicios (Long viviendaId, List<ServicioEntity> list) throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar los servicios de la vivienda con id = {0}", viviendaId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        for (ServicioEntity servicio : list) {
            Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicio.getId());
            if (servicioEntity.isEmpty())
                throw new EntityNotFoundException("El servicio esta vacio.");

            if (!viviendaEntity.get().getServiciosVivienda().contains(servicioEntity.get()))
                viviendaEntity.get().getServiciosVivienda().add(servicioEntity.get());
        }
        log.info("Termina proceso de reemplazar los servicios de la vivienda con id = {0}", viviendaId);
        return getServicios(viviendaId);}

    @Transactional
    public void removeServicio (Long viviendaId, Long servicioId) throws EntityNotFoundException, NoSuchElementException {
        log.info("Inicia proceso de removerle un servicio a una vivienda con id = {0}", viviendaId);

        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);

        if(servicioEntity.isEmpty())
            throw new EntityNotFoundException("Servicio not found");
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        viviendaEntity.get().getServiciosVivienda().remove(servicioEntity.get()); 
        log.info("Termina proceso de removerle un servicio a una vivienda con id = {0}", viviendaId);
        }    


}
