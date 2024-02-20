package co.edu.uniandes.dse.Vivienda.services;

import java.util.Collection;
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
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service

public class PropietarioService {
        //Atributos
        @Autowired
        private PropietarioRepository PropietarioRepository;

        //Metodos

        //Metodo para crear un Propietario junto a sus respectivas validaciones
        @Transactional
        public PropietarioEntity createPropietario(PropietarioEntity propietarioEntity) throws EntityNotFoundException, IllegalOperationException {
                log.info("Inicia el proceso de creación de un Propietario");

            if (propietarioEntity.getCelular() == null)
                throw new IllegalOperationException("Es obligatorio ingresar un numero de Celular");

            if (propietarioEntity.getNombre() == null || propietarioEntity.getNombre().trim().isEmpty())
                throw new IllegalOperationException("Es obligatorio ingresar un Nombre"); 
            
            if (propietarioEntity.getNombre().length() < 2) 
                throw new IllegalOperationException("El nombre debe contener al menos 2 caracteres");
            
            if (propietarioEntity.getCorreo() == null || propietarioEntity.getCorreo().isEmpty())
                throw new IllegalOperationException("Es obligatorio ingresar un correo electrónico"); 
            
            if (!PropietarioRepository.findByCorreo(propietarioEntity.getCorreo()).isEmpty())
                throw new IllegalOperationException("El correo indicado ya se encuentra registrado en la base de datos.");
                      
            return PropietarioRepository.save(propietarioEntity);
            
        }

        // Metodo para traer todos los Propietarios existentes 
        @Transactional
        public List<PropietarioEntity> getPropietarios(){
            log.info("Inicia el proceso de consultar todos los Propietarios");
            return PropietarioRepository.findAll();
        }

        // Metodo para obtener un Propietario especifico    
        @Transactional
        public PropietarioEntity getPropietario(Long id) throws EntityNotFoundException{
            log.info("Inicia el proceso de consultar un propietario con el ID proporcionado", id);
            Optional<PropietarioEntity> propietarioEntity = PropietarioRepository.findById(id);
            if (propietarioEntity.isEmpty())
                throw new EntityNotFoundException("No se encontró el Propietario"); 
            log.info("Termina el proceso de consultar un propietario con el ID proporcionado", id);
            return propietarioEntity.get();
        }

        // Metodo para actualizar un propietario 
        @Transactional
        public PropietarioEntity updatePropietario(Long id, PropietarioEntity Propietario)
                throws EntityNotFoundException, IllegalOperationException {

            log.info("Inicia proceso de actualizar el propietario con id = {0}", id);
            Optional<PropietarioEntity> propietarioEntity = PropietarioRepository.findById(id);   
            if (propietarioEntity.isEmpty())
                throw new EntityNotFoundException("No se encontro el propietario");
            Propietario.setId(id);
            log.info("Termina proceso de actualizar el propietario con id = {0}", id);
            return PropietarioRepository.save(Propietario);
            }
        
        @Transactional
        public void deletePropietario(Long id) throws EntityNotFoundException, IllegalOperationException {
            log.info("Inicia proceso de borrar el Propietario con id = {0}", id);
            Optional<PropietarioEntity> propietarioEntity = PropietarioRepository.findById(id);
            if (propietarioEntity.isEmpty())
                throw new EntityNotFoundException("No se encontro el propietario");
            PropietarioRepository.deleteById(id);
            log.info("Termina proceso de borrar el propietario con id = {0}", id);
            }

    }