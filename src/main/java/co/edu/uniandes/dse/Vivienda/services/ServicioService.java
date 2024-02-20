package co.edu.uniandes.dse.Vivienda.services;
import java.util.Optional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.Vivienda.entities.ServicioEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.ServicioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServicioService {

    @Autowired
    static
    ServicioRepository servicioRepository;
    
    @Transactional
    public static ServicioEntity crearServicio(ServicioEntity servicioEntity) throws IllegalOperationException{
        log.info("inicia el proceso de crear un servico");
        if (servicioEntity == null) {
            throw new IllegalOperationException("La entidad lugar no puede ser nula");
            }
        if (servicioEntity.getId()==null){
            throw new IllegalOperationException("El Servicio debe tener un id");
        }
        if (servicioEntity.getNombre()==null|servicioEntity.getNombre().isEmpty()){
            throw new IllegalOperationException("El Servicio debe tener un nombre");
        }
        if (servicioEntity.getCostoAdicional() == null){
            throw new IllegalOperationException("El servicio debe tener un valoir asigando");
        }
        if (servicioEntity.getCostoAdicional() < 0| servicioEntity.getCostoAdicional()==null){
            throw new IllegalOperationException("El servicio debe tener un costo no negativo");
        }
        log.info("Termina el proceso de la creacion del servicio");
        return  servicioRepository.save(servicioEntity);
    }
    
    public ServicioEntity getServicio(Long ServicioID) throws EntityNotFoundException{
        log.info("Inicia el proceso de consultar el servicio con el Id = {0}");
        Optional<ServicioEntity> servicioEntityContainer = servicioRepository.findById(ServicioID);
        if (servicioEntityContainer.isEmpty()){
            throw new EntityNotFoundException("ErrorMessage.SERVICIO_NOT_FOUND");
        }
        log.info("Termina el proceso de consultar un Servicio por su ID");
        return servicioEntityContainer.get();
    }
    @Transactional
    public ServicioEntity updateServicio(long servicioId, ServicioEntity servicio) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia el prceso de actualizar el servicio con su Id ", servicioId);
        Optional<ServicioEntity> servicioEntityContainer = servicioRepository.findById(servicioId);
        if (servicioEntityContainer.isEmpty()){
            throw new EntityNotFoundException("no existe servicio con este Id");
        }
        if (servicio.getNombre()==null){
                throw new IllegalOperationException("El nombre no es valido");}
        if (servicio.getNombre().isEmpty()){
                throw new IllegalOperationException("El nombre no es valido");}
        servicio.setId(servicioId);
        log.info("termina el proceso de actualizar el servicio con id", servicioId);
        return servicioRepository.save(servicio);
    }

    @Transactional
    public void deleteServicio(Long servicioId)throws EntityNotFoundException{
        log.info("Inicia el proceso de eliminar un Servicio por su Id");
        Optional<ServicioEntity> servicioEntityContainer = servicioRepository.findById(servicioId);
        if(servicioEntityContainer.isEmpty()){
            throw new EntityNotFoundException("No existe servicio con este Id");
        }
        log.info("Termina le proceso de eliminar un servicio por su ID");
        servicioRepository.deleteById(servicioId);
    }

    
}