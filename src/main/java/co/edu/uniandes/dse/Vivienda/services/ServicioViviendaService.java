package co.edu.uniandes.dse.Vivienda.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.Vivienda.entities.ServicioEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.ServicioRepository;
import co.edu.uniandes.dse.Vivienda.repositories.ViviendaRepository;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ServicioViviendaService {
    @Autowired
    private ViviendaRepository viviendaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Transactional
    public ViviendaEntity addVivienda(Long servicioId, Long viviendaId) throws EntityNotFoundException {
        log.info("Inicia proceso de agregarle una vivienda a un servicio con el id = {0}", servicioId);
        Optional<ServicioEntity> ServicioEntityContainer = servicioRepository.findById(servicioId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);

        if (ServicioEntityContainer.isEmpty())
            throw new EntityNotFoundException("El servicio esta vacio.");

        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        viviendaEntity.get().getServiciosVivienda().add(ServicioEntityContainer.get());
        log.info("Termina proceso de gregarle una vivienda a un servicio con id = {0}", servicioId);
        return viviendaEntity.get();
    }

    @Transactional
    public List<ViviendaEntity> getViviendas(Long servicioId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar todos las viviendas del servicio con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException("El servicio esta vacio.");

        log.info("Termina proceso de consultar todas las viviendas del servicio con id = {0}", servicioId);
        return servicioEntity.get().getViviendasServicio();
    }

    @Transactional
    public ViviendaEntity getVivienda(Long servicioId, Long viviendaId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de consultar la vivienda con id = {0} del servicio con id = " + servicioId, viviendaId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
 
        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException("El servicio esta vacio.");

        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        log.info("Termina proceso de consultar la vivienda con id = {0} del servicio con id = " + servicioId, servicioId);
        if (!viviendaEntity.get().getServiciosVivienda().contains(servicioEntity.get()))
            throw new IllegalOperationException("La vivienda no esta asociada al servicio");
        
        return viviendaEntity.get();
    }

    @Transactional
    public List<ViviendaEntity> replaceViviendas(Long servicioId, List<ViviendaEntity> viviendas) throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar las viviendas asociados al lugar con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException("El servicio esta vacio.");

        for (ViviendaEntity vivienda : viviendas) {
            Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(vivienda.getId());
            if (viviendaEntity.isEmpty())
                throw new EntityNotFoundException("La vivienda esta vacia.");

            if (!viviendaEntity.get().getServiciosVivienda().contains(servicioEntity.get()))
                viviendaEntity.get().getServiciosVivienda().add(servicioEntity.get());
        }
        log.info("Finaliza proceso de reemplazar las viviendas asociados con el servicio con id = {0}", servicioId);
        servicioEntity.get().setViviendasServicio(viviendas);
        return servicioEntity.get().getViviendasServicio();
    }

    @Transactional
    public void removeVivienda(Long servicioId, Long viviendaId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar un lugar de la vivienda con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty())
            throw new EntityNotFoundException("El servicio esta vacio.");

        Optional<ViviendaEntity> viviendaEntity = viviendaRepository.findById(viviendaId);
        if (viviendaEntity.isEmpty())
            throw new EntityNotFoundException("La vivienda esta vacia.");

        viviendaEntity.get().getServiciosVivienda().remove(servicioEntity.get());
        servicioEntity.get().getViviendasServicio().remove(viviendaEntity.get());
        log.info("Finaliza proceso de borrar un servicio con id = {0}", servicioId);
    }
}
    


