// package co.edu.uniandes.dse.Vivienda.controllers;

// import org.springframework.web.bind.annotation.RestController;

// import co.edu.uniandes.dse.Vivienda.dto.PropietarioDTO;
// import co.edu.uniandes.dse.Vivienda.dto.PropietarioDetailDTO;
// import co.edu.uniandes.dse.Vivienda.entities.PropietarioEntity;
// import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
// import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
// import co.edu.uniandes.dse.Vivienda.services.PropietarioService;

// import java.util.List;

// import org.modelmapper.ModelMapper;
// import org.modelmapper.TypeToken;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;


// // Clase que representa el recurso "Propietario" //

// @RestController
// @RequestMapping("/propietarios")
// public class PropietarioController {
  
//     @Autowired
//     private PropietarioService propietarioService;

//     @Autowired
//     private ModelMapper modelMapper; 



// @PostMapping    //Tag que vuelve el metodo un Post
// @ResponseStatus(code = HttpStatus.CREATED)    // Tag que devuelve un codigo cuando todo sale bien
// public PropietarioDTO post(@RequestBody PropietarioDTO propietarioDTO) throws IllegalOperationException, EntityNotFoundException{    // Tag que hace que el parametro sea el mismo de Postman
//     PropietarioEntity propietarioEntity = propietarioService.createPropietario(modelMapper.map(propietarioDTO, PropietarioEntity.class));
//     return modelMapper.map(propietarioEntity, PropietarioDTO.class);
// }       

// @GetMapping(value = "/{id}")    //Tag que vuelve el metodo un Get
// @ResponseStatus(code = HttpStatus.OK)    // Tag que devuelve un codigo cuando todo sale bien
// public PropietarioDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
//     PropietarioEntity propietarioEntity = propietarioService.getPropietario(id);
//     return modelMapper.map(propietarioEntity, PropietarioDetailDTO.class);
// }

// @GetMapping
// @ResponseStatus(code = HttpStatus.OK)
// public List<PropietarioDTO> findAll() {
//         List<PropietarioEntity> propietarios = propietarioService.getPropietarios();
//         return modelMapper.map(propietarios, new TypeToken<List<PropietarioDTO>>() {
//         }.getType());
// }

// @PutMapping(value = "/{id}")
// @ResponseStatus(code = HttpStatus.OK)
// public PropietarioDTO update(@PathVariable("id") Long id, @RequestBody PropietarioDTO propietarioDTO)
//                         throws EntityNotFoundException, IllegalOperationException {
//         PropietarioEntity propietarioEntity = propietarioService.updatePropietario(id, modelMapper.map(propietarioDTO, PropietarioEntity.class));
//         return modelMapper.map(propietarioEntity, PropietarioDTO.class);
// }

// @DeleteMapping(value = "/{id}")
// @ResponseStatus(code = HttpStatus.NO_CONTENT)
// public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
//         propietarioService.deletePropietario(id);
// }
// }
