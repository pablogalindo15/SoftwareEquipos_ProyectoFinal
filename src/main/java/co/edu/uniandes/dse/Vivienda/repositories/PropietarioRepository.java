package co.edu.uniandes.dse.Vivienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.Vivienda.entities.PropietarioEntity;
import java.util.List;


@Repository
public interface PropietarioRepository extends JpaRepository<PropietarioEntity, Long>{
    List<PropietarioEntity> findByCorreo(String correo);
}
