package co.edu.uniandes.dse.Vivienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;

public interface ViviendaRepository extends JpaRepository<ViviendaEntity, Long>{
    
}
