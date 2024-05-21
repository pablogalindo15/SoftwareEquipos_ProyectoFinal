insert into propietario_entity (id, foto, nombre, celular, correo) values (1, 'http://dummyimage.com/246x100.png/ff4444/ffffff', 'Sherlock', 30644438, 'smccreery0@amazon.com');
insert into propietario_entity (id, foto, nombre, celular, correo) values (2, 'http://dummyimage.com/232x100.png/ff4444/ffffff', 'Winslow', 82225165, 'wmckomb1@live.com');
insert into propietario_entity (id, foto, nombre, celular, correo) values (3, 'http://dummyimage.com/117x100.png/5fa2dd/ffffff', 'Tootsie', 97836070, 'tstinchcombe2@slate.com');
insert into propietario_entity (id, foto, nombre, celular, correo) values (4, 'http://dummyimage.com/230x100.png/5fa2dd/ffffff', 'Harriet', 35812767, 'hneesham3@ebay.com');
insert into propietario_entity (id, foto, nombre, celular, correo) values (5, 'http://dummyimage.com/145x100.png/ff4444/ffffff', 'Katuscha', 2994656, 'ksheriff4@epa.gov');
insert into propietario_entity (id, foto, nombre, celular, correo) values (6, 'http://dummyimage.com/164x100.png/cc0000/ffffff', 'Zitella', 82265421, 'zfollos5@squarespace.com');
insert into propietario_entity (id, foto, nombre, celular, correo) values (7, 'http://dummyimage.com/198x100.png/dddddd/000000', 'Krystalle', 25047373, 'kstainton6@nytimes.com');
insert into propietario_entity (id, foto, nombre, celular, correo) values (8, 'http://dummyimage.com/232x100.png/dddddd/000000', 'Myrlene', 24276441, 'mcockshutt7@clickbank.net');
insert into propietario_entity (id, foto, nombre, celular, correo) values (9, 'http://dummyimage.com/169x100.png/dddddd/000000', 'Denny', 16114748, 'dbinnion8@weather.com');
insert into propietario_entity (id, foto, nombre, celular, correo) values (10, 'http://dummyimage.com/133x100.png/5fa2dd/ffffff', 'Dorothy', 48827140, 'dkettell9@reverbnation.com');

insert into vivienda_entity (id, nombre, precio, descripcion, fotos, tamano, estrato, restricciones, tipo, contacto, direccion, ocupada, coordX, coordY, propietario_id) values (1, 'Casa de Manuel', 120000, 'Casa familiar de dos pisos, con jardín y garaje.', 'https://i.pinimg.com/originals/48/b0/e7/48b0e7f4774dbb07426c1c6aa1e3b1ec.jpg', 280, 2, 'No se permiten mascotas', 0, 'manuel@example.com', 'Calle 123 #45-67', false, 27.37, 2.04, 1);
insert into vivienda_entity (id, nombre, precio, descripcion, fotos, tamano, estrato, restricciones, tipo, contacto, direccion, ocupada, coordX, coordY, propietario_id) values (2, 'Apartamento de Giffer', 170000, 'Moderno apartamento con vista a la ciudad, ubicado en el centro.', 'https://i.pinimg.com/originals/d3/17/86/d3178636cf3d6b9c51c754c2f4d4213e.jpg', 70, 1, 'No fumadores', 2, 'giffer@example.com', 'Carrera 7 #85-29', false, 91.78, 19.3, 2);
insert into vivienda_entity (id, nombre, precio, descripcion, fotos, tamano, estrato, restricciones, tipo, contacto, direccion, ocupada, coordX, coordY, propietario_id) values (3, 'Penthouse de Nissie', 840000, 'Penthouse de lujo con piscina privada y amplia terraza.', 'https://i.pinimg.com/originals/03/45/a4/0345a49e0fedb4a23edf337ae0284ade.jpg', 523, 2, 'No se permiten eventos', 0, 'nissie@example.com', 'Avenida 68 #34-56', false, 89.41, 75.29, 3);
insert into vivienda_entity (id, nombre, precio, descripcion, fotos, tamano, estrato, restricciones, tipo, contacto, direccion, ocupada, coordX, coordY, propietario_id) values (4, 'Apartamento de Tybie', 670000, 'Amplio apartamento con gimnasio y zonas comunes.', 'https://i.pinimg.com/originals/47/46/f1/4746f1e9844bb23400194f41a761eeda.jpg', 1069, 0, 'No se permiten mascotas grandes', 1, 'tybie@example.com', 'Calle 45 #20-10', true, 10.28, 57.85, 4);
insert into vivienda_entity (id, nombre, precio, descripcion, fotos, tamano, estrato, restricciones, tipo, contacto, direccion, ocupada, coordX, coordY, propietario_id) values (5, 'Loft de Sayers', 910000, 'Loft industrial con diseño moderno y techos altos.', 'https://i.pinimg.com/originals/a9/17/ed/a917ed3c32945c9d56fdc6cdc29e578b.jpg', 5483, 0, 'Prohibido hacer ruido después de las 10 PM', 2, 'sayers@example.com', 'Carrera 12 #48-22', true, 77.09, 31.3, 5);
insert into vivienda_entity (id, nombre, precio, descripcion, fotos, tamano, estrato, restricciones, tipo, contacto, direccion, ocupada, coordX, coordY, propietario_id) values (6, 'Apartamento de Kala', 720000, 'Apartamento recién remodelado, con cocina equipada y balcón.', 'https://i.pinimg.com/originals/82/c0/4a/82c04a241cc017869391d8ca99205310.jpg', 5790, 1, 'No se permiten fiestas', 1, 'kala@example.com', 'Calle 80 #22-18', true, 33.31, 78.66, 6);
insert into vivienda_entity (id, nombre, precio, descripcion, fotos, tamano, estrato, restricciones, tipo, contacto, direccion, ocupada, coordX, coordY, propietario_id) values (7, 'Suite de Charlton', 460000, 'Suite en hotel con servicios incluidos y vista al mar.', 'https://i.pinimg.com/originals/82/03/f1/8203f15791b65a5f9af4c3c66d2f227a.jpg', 1663, 4, 'No se permiten visitas nocturnas', 1, 'charlton@example.com', 'Avenida 10 #5-77', true, 51.09, 62.45, 7);
insert into vivienda_entity (id, nombre, precio, descripcion, fotos, tamano, estrato, restricciones, tipo, contacto, direccion, ocupada, coordX, coordY, propietario_id) values (8, 'Casa de Quill', 590000, 'Casa de campo con amplio jardín y piscina.', 'https://i.pinimg.com/originals/4b/33/85/4b338512727ce7ce0b332523286a56be.jpg', 1036, 3, 'No se permite subarrendar', 0, 'quill@example.com', 'Vereda El Carmen', true, 41.65, 54.81, 8);
insert into vivienda_entity (id, nombre, precio, descripcion, fotos, tamano, estrato, restricciones, tipo, contacto, direccion, ocupada, coordX, coordY, propietario_id) values (9, 'Apartamento de Raeann', 830000, 'Apartamento céntrico con acceso a transporte público.', 'https://i.pinimg.com/originals/35/69/07/35690701a773f0fcb495877063623033.jpg', 1036, 0, 'No se permiten mascotas', 2, 'raeann@example.com', 'Calle 50 #23-40', false, 56.99, 63.91, 9);
insert into vivienda_entity (id, nombre, precio, descripcion, fotos, tamano, estrato, restricciones, tipo, contacto, direccion, ocupada, coordX, coordY, propietario_id) values (10,'Estudio de Garvin', 20000, 'Pequeño estudio ideal para estudiantes, cerca de la universidad.', 'https://i.pinimg.com/originals/b4/75/fa/b475faf3fc1ec368874eebf391ef500f.jpg', 622, 3, 'No se permiten mascotas', 0, 'garvin@example.com', 'Calle 18 #3-45', true, 53.93, 39.88, 10);

insert into habitante_entity (id, cedula, nombre , vivienda_id) values (1, 709146542, 'Avie Rideout', 1);
insert into habitante_entity (id, cedula, nombre , vivienda_id) values (2, 288408031, 'Lurline Chastang', 2);
insert into habitante_entity (id, cedula, nombre , vivienda_id) values (3, 131580995, 'Tiffani Merwood', 3);
insert into habitante_entity (id, cedula, nombre , vivienda_id) values (4, 592506303, 'Rasia Danby', 4);
insert into habitante_entity (id, cedula, nombre , vivienda_id) values (5, 479790538, 'Rafaello Blackwell', 5);
insert into habitante_entity (id, cedula, nombre , vivienda_id) values (6, 465310691, 'Giacobo Dripp', 6);
insert into habitante_entity (id, cedula, nombre , vivienda_id) values (7, 785171550, 'Tully Mathivon', 7);
insert into habitante_entity (id, cedula, nombre , vivienda_id) values (8, 027248530, 'Natty Kubera', 8);
insert into habitante_entity (id, cedula, nombre , vivienda_id) values (9, 499702625, 'Heidi January', 9);
insert into habitante_entity (id, cedula, nombre , vivienda_id) values (10, 499807986, 'Sigismond Schmidt', 10);

INSERT INTO lugar_entity (id, nombre, coordenadax, coordenaday, fotos, gratis, precio_max, precio_min, tiempo_llegada, tipo) VALUES
 (1, 'Universidad De Los Andes', 4.602434129812227, -74.0662932590041, 'https://uniandes.edu.co/sites/default/files/campus_ng.jpg', false, 10000, 5000, 5, 0);

INSERT INTO lugar_entity (id, nombre, coordenadax, coordenaday, fotos, gratis, precio_max, precio_min, tiempo_llegada, tipo) VALUES
 (2, 'Pontificia Universidad Javeriana', 4.630117828190012, -74.06450287597498, 'https://www.javeriana.edu.co/recursosdb/659410/668610/deparatamentos-e-institutos.jpg/39709ee8-a370-7ea2-4880-fb9d9f6e2c3d?t=1684251553829', false, 10000, 5000, 5, 0);

INSERT INTO lugar_entity (id, nombre, coordenadax, coordenaday, fotos, gratis, precio_max, precio_min, tiempo_llegada, tipo) VALUES
 (3, 'Universidad del Rosario', 4.601875436064864, -74.07346446161951, 'https://urosario.edu.co/static/getattachment/5556617b-2b6c-4d2d-8b9e-ca836ee99308/portadanoticias.jpg', false, 10000, 5000, 5, 0);

INSERT INTO lugar_entity (id, nombre, coordenadax, coordenaday, fotos, gratis, precio_max, precio_min, tiempo_llegada, tipo) VALUES
 (4, 'Universidad de La Sabana', 4.86910458497227, -74.03288012372631, 'https://www.unisabana.edu.co/fileadmin/_processed_/e/1/csm_foto-universidad-de-la-sabana-nota-informacion-receso-2021_6b46bf5451.jpg', false, 10000, 5000, 5, 0);

INSERT INTO lugar_entity (id, nombre, coordenadax, coordenaday, fotos, gratis, precio_max, precio_min, tiempo_llegada, tipo) VALUES
 (5, 'Universidad Nacional de Colombia', 4.637601843845605, -74.08274817920406, 'https://s3.amazonaws.com/rtvc-assets-senalcolombia.gov.co/s3fs-public/field/image/ppal-sc-unal.jpg', false, 10000, 5000, 5, 0);

INSERT INTO lugar_entity (id, nombre, coordenadax, coordenaday, fotos, gratis, precio_max, precio_min, tiempo_llegada, tipo) VALUES
 (6, 'Carulla', 4.655594271734949, -74.05321534959184, 'https://colombiamaspositiva.com/wp-content/uploads/2021/11/WhatsApp-Image-2021-11-25-at-10.19.29-PM.jpeg', false, 100000, 100, 5, 2);

INSERT INTO lugar_entity (id, nombre, coordenadax, coordenaday, fotos, gratis, precio_max, precio_min, tiempo_llegada, tipo) VALUES
 (7, 'MacDonalds', 4.676441795832425, -74.04849651941522, 'https://www.acis.org.co/portal/sites/default/files/DSC08692.jpg', false, 100000, 100, 5, 1);

INSERT INTO lugar_entity (id, nombre, coordenadax, coordenaday, fotos, gratis, precio_max, precio_min, tiempo_llegada, tipo) VALUES
 (8, 'Parque del chico', 4.674506258610858, -74.0443767074275, 'https://www.museodelchico.com/wp-content/uploads/2023/06/parque-chico-scaled.jpg', true, 100000, 0, 5, 3);

INSERT INTO lugar_entity (id, nombre, coordenadax, coordenaday, fotos, gratis, precio_max, precio_min, tiempo_llegada, tipo) VALUES
 (9, 'Centro Comercial Andino', 4.667617914715053, -74.05248676943387, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.tripadvisor.es%2FAttraction_Review-g294074-d315690-Reviews-Centro_Comercial_Andino-Bogota.html&psig=AOvVaw2JtRWqFeocxQn7ckko2a-D&ust=1716414122929000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCOiRzoLbn4YDFQAAAAAdAAAAABAE', false, 100000, 100, 5, 4);


INSERT INTO lugar_entity (id, nombre, coordenadax, coordenaday, fotos, gratis, precio_max, precio_min, tiempo_llegada, tipo) VALUES
 (9, 'Centro Comercial Andino', 4.667617914715053, -74.05248676943387, 'https://pbs.twimg.com/media/FwQOuIxWcAEGaKh?format=jpg&name=large', false, 100000, 100, 5, 4);


insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (5, 4);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (8, 5); 
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (5, 6); 
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (6, 7); 
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (4, 6); 
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (9, 1);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (2, 9);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (9, 6);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (1, 8); 
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (4, 7); 
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (4, 8);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (10, 6);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (7, 7);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (7, 5);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (4, 4);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (3, 4);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (9, 8); 
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (2, 7); 
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (1, 9); 
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (6, 6);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (9, 8);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (1, 1);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (7, 8); 
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (5, 9);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (9, 4);
insert into VIVIENDA_ENTITY_LUGAR_DE_INTERES_CERCANO (viviendas_cercanas_id, lugar_de_interes_cercano_id) values (7, 7); 

insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (1, 9);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (4, 2);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (7, 9);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (10, 7);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (7, 10);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (1, 10);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (10, 4);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (2, 8);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (9, 5);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (1, 3);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (2, 4);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (1, 4);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (10, 10);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (9, 2);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (4, 8);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (3, 4);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (4, 9);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (1, 1);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (5, 10);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (9, 7);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (7, 5);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (2, 4);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (6, 9);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (10, 4);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (5, 4);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (7, 3);
insert into VIVIENDA_ENTITY_HISTORIAL (viviendas_id, historial_id) values (1, 7);

insert into SERVICIO_ENTITY (id, nombre, costo_adicional, tipo) values (1, 'Mydo', 420317, 0);
insert into SERVICIO_ENTITY (id, nombre, costo_adicional, tipo) values (2, 'Katz', 609437, 2);
insert into SERVICIO_ENTITY (id, nombre, costo_adicional, tipo) values (3, 'Fadeo', 728832, 1);
insert into SERVICIO_ENTITY (id, nombre, costo_adicional, tipo) values (4, 'Brightbean', 963828, 4);
insert into SERVICIO_ENTITY (id, nombre, costo_adicional, tipo) values (5, 'Tagfeed', 261183, 2);
insert into SERVICIO_ENTITY (id, nombre, costo_adicional, tipo) values (6, 'Kwilith', 827956, 2);
insert into SERVICIO_ENTITY (id, nombre, costo_adicional, tipo) values (7, 'Skynoodle', 324760, 1);
insert into SERVICIO_ENTITY (id, nombre, costo_adicional, tipo) values (8, 'Jaxworks', 549510, 3);
insert into SERVICIO_ENTITY (id, nombre, costo_adicional, tipo) values (9, 'Snaptags', 504635, 0);
insert into SERVICIO_ENTITY (id, nombre, costo_adicional, tipo) values (10, 'Kamba', 86993, 2);

insert into comentario_entity (id,titulo, texto, calificacion , nombre) values (1, 'Buen servicio', 'la persona que nos atendió brindo un buen servicio', 5,'Sherlock');
insert into comentario_entity (id,titulo, texto, calificacion , nombre) values (2, 'fea vista', 'La vista no fue de mi agrado ya que un edificio tapa la visión', 3,'Winslow');
insert into comentario_entity (id,titulo, texto, calificacion , nombre) values (3, 'linda vista', 'Me gusto la vista ya que da a la avenida', 4,'Katushka');
insert into comentario_entity (id,titulo, texto, calificacion , nombre) values (4, 'es lo que esperaba', 'El apartamento es justamente como en las publicaciones', 5,'Garvin');
insert into comentario_entity (id,titulo, texto, calificacion , nombre) values (5, 'no es lo que esperaba', 'no es nada parecido al de las publicaciones', 2,'Sayers');
insert into comentario_entity (id,titulo, texto, calificacion , nombre) values (6, 'buena ubicación', 'Esta muy cerca de mi universidad', 5,'Tybie');
insert into comentario_entity (id,titulo, texto, calificacion , nombre) values (7, 'mala ubiacion', 'Es muy lejos de mi universidad', 2,'Quill');
insert into comentario_entity (id,titulo, texto, calificacion , nombre) values (8, 'centralizado', 'Al estar en el centro de la ciudad, tengo acceso a lugares muy rapidamente', 4,'Dorothy');
insert into comentario_entity (id,titulo, texto, calificacion , nombre) values (9, 'no centralizado', ' queda muy lejos de todo al no estar en el centro de la ciudad', 3,'Denny');
insert into comentario_entity (id,titulo,texto, calificacion , nombre) values (10, 'buen piso', 'Me gustaron mucho el diseño de las baldosas', 3,'Zitella');
