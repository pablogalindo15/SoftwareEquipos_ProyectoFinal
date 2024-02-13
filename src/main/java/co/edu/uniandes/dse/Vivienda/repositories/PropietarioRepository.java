package co.edu.uniandes.dse.Vivienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;

@Repository
public interface PropietarioRepository extends JpaRepository<ViviendaEntity, Long>{
    
}
