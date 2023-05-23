insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (1, 'Messi', 'https://www.armandorodriguez.com.ar/img.php?img=caricaturas-lio-messi.jpg&mode=cm&w=350&h=400', 'newells, fc barcelona, psg');
insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (2, 'El bicho', 'https://im.rediff.com/sports/2014/mar/06ronaldo-portugal-record.jpg', 'sporting, manchester united, real madrid, juventus, machester united, al nassr :()');
insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (3, 'Marco Mbapperez', 'https://www.elmundo.com/assets/uploads/files/36586-vladimir-hernandez.jpg', 'Junior');
insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (4, 'Jimmy Butler', 'https://media.gettyimages.com/id/1491625432/es/foto/jimmy-butler-of-the-miami-heat-reacts-during-the-fourth-quarter-against-the-boston-celtics-in.jpg?s=1024x1024&w=gi&k=20&c=ho6N5Vfs_nUcD8B1ZRlwOs8Bsi5z8zm9EcMJgNL5ozc=', 'Miami');
insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (5, 'Kyle Lowry', 'https://media.gettyimages.com/id/1489592484/es/foto/kyle-lowry-of-the-miami-heat-drives-on-isaiah-hartenstein-of-the-new-york-knicks-during-game.jpg?s=612x612&w=gi&k=20&c=Us3nUjE6nYeGUbdtnKXLifFBcZy_R19m2-T_82kPOlE=', 'Toronto');
insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (6, 'DiAngello Russel', 'https://media.gettyimages.com/id/1255987883/es/foto/dangelo-russell-of-the-los-angeles-lakers-looks-on-prior-to-game-2-of-the-western-conference.jpg?s=612x612&w=gi&k=20&c=U4CihBN_ejG379T_ydeXF_zispABhsfc82JgR_Grky0=', 'Los Angeles');
insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (7, 'Jaylen Brown', 'https://media.gettyimages.com/id/1255640642/es/foto/jaylen-brown-of-the-boston-celtics-looks-on-during-game-one-of-the-eastern-conference-finals.jpg?s=612x612&w=gi&k=20&c=sJr9p_TfKCIQCIUW8ol-pzvMfl0k45pF0wMoilP0el0=', 'Boston');
insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (8, 'Jamal Murray', 'https://media.gettyimages.com/id/1256648448/es/foto/jamal-murray-of-the-denver-nuggets-dribbles-the-ball-during-game-three-of-the-western.jpg?s=612x612&w=gi&k=20&c=IjML8J8KprwrLcWlgbkNIC-vdVuqjGp-clAqtZ9szCE=', 'Denver');
insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (9, 'Lamelo Ball', 'https://media.gettyimages.com/id/1247543953/es/foto/lamelo-ball-of-the-charlotte-hornets-looks-on-during-the-game-on-febuary-27-2023-at-spectrum.jpg?s=612x612&w=gi&k=20&c=EV8hlm5MXGwv6s9SvZnFmSg9mqKx_z48zfUfbJUurrY=', 'Charlotte');

insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (1, 'Central', 'Calle 31 Norte #7-21', '7788918');
insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (2, 'Occidental', 'Calle 15 Oeste #3-49', '9988112');
insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (3, 'Pasoancho', 'Calle 14 Sur #1-23', '7208698');
insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (4, 'Pance', 'Calle 16 Este #9-92', '3105288');
insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (5, 'Guajira', 'Calle 29 Norte #6-42', '8152054');
insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (6, 'Amazonas', 'Calle 1 Oeste #2-81', '9904768');
insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (7, 'Arauca', 'Calle 3 Sur #1-33', '7406985');
insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (8, 'Guaviare', 'Calle 7 Este #32-47', '2898560');

INSERT INTO SERVICIO_ENTITY (id, servicio, disponible, sede_id) VALUES (1, 'Pesas', True, 1);
INSERT INTO SERVICIO_ENTITY (id, servicio, disponible, sede_id) VALUES (2, 'Masajes', True, 2);
INSERT INTO SERVICIO_ENTITY (id, servicio, disponible, sede_id) VALUES (3, 'Duchas', True, 3);
INSERT INTO SERVICIO_ENTITY (id, servicio, disponible, sede_id) VALUES (4, 'Clases', True, 4);
INSERT INTO SERVICIO_ENTITY (id, servicio, disponible, sede_id) VALUES (5, 'Lavandería', True, 5);
INSERT INTO SERVICIO_ENTITY (id, servicio, disponible, sede_id) VALUES (6, 'Transporte', True, 6);
INSERT INTO SERVICIO_ENTITY (id, servicio, disponible, sede_id) VALUES (7, 'Eventos en Vivo', True, 7);
INSERT INTO SERVICIO_ENTITY (id, servicio, disponible, sede_id) VALUES (8, 'Especialistas Invitados', True, 8);

insert into PLAN_ENTRENAMIENTO_ENTITY(id, nombre, objetivo_Basico, descripcion, direccion, duracion, costo) values(3, 'Fuerza I', 'Aumento de fuerza', 'Ejercicio basado en fuerza','Cra7',3,3000);

insert into ACTIVIDAD_ENTITY(id, nombre, max_Participantes, horario, tipo, entrenador_id) values (1, 'Yoga', 30, 'Lunes - 12:30 a 2:30', 'Grupal', 1);
insert into ACTIVIDAD_ENTITY(id, nombre, max_Participantes, horario, tipo, entrenador_id) values (2, 'Pilates', 20, 'Martes - 10:00 a 11:00', 'Grupal', 2);
insert into ACTIVIDAD_ENTITY(id, nombre, max_Participantes, horario, tipo, entrenador_id) values (3, 'Zumba', 25, 'Miércoles - 17:00 a 18:00', 'Grupal', 3);
insert into ACTIVIDAD_ENTITY(id, nombre, max_Participantes, horario, tipo, entrenador_id) values (4, 'CrossFit', 15, 'Jueves - 19:30 a 21:00', 'Grupal', 4);
insert into ACTIVIDAD_ENTITY(id, nombre, max_Participantes, horario, tipo, entrenador_id) values (5, 'Spinning', 25, 'Viernes - 15:00 a 16:00', 'Grupal', 5);
insert into ACTIVIDAD_ENTITY(id, nombre, max_Participantes, horario, tipo, entrenador_id) values (6, 'Kickboxing', 20, 'Sábado - 9:00 a 10:30', 'Grupal', 6);
insert into ACTIVIDAD_ENTITY(id, nombre, max_Participantes, horario, tipo, entrenador_id) values (7, 'Pilates', 15, 'Domingo - 16:00 a 17:00', 'Grupal', 7);
insert into ACTIVIDAD_ENTITY(id, nombre, max_Participantes, horario, tipo, entrenador_id) values (8, 'Aeróbicos', 30, 'Lunes - 18:30 a 19:30', 'Grupal', 8);
insert into ACTIVIDAD_ENTITY(id, nombre, max_Participantes, horario, tipo, entrenador_id) values (9, 'Baile', 25, 'Miércoles - 10:30 a 12:00', 'Grupal', 9);

insert into RESTRICCION_ENTITY (id, edad, cond_fisica, actividad_id) values (1, 45, 'buena resistencia', 1);
insert into RESTRICCION_ENTITY (id, edad, cond_fisica, actividad_id) values (2, 30, 'ninguna restricción', 2);
insert into RESTRICCION_ENTITY (id, edad, cond_fisica, actividad_id) values (3, 28, 'lesión en la rodilla', 3);
insert into RESTRICCION_ENTITY (id, edad, cond_fisica, actividad_id) values (4, 50, 'hipertensión', 4);
insert into RESTRICCION_ENTITY (id, edad, cond_fisica, actividad_id) values (5, 40, 'baja flexibilidad', 5);
insert into RESTRICCION_ENTITY (id, edad, cond_fisica, actividad_id) values (6, 35, 'ninguna restricción', 6);
insert into RESTRICCION_ENTITY (id, edad, cond_fisica, actividad_id) values (7, 55, 'problemas de espalda', 7);
insert into RESTRICCION_ENTITY (id, edad, cond_fisica, actividad_id) values (8, 30, 'buena condición física', 8);
insert into RESTRICCION_ENTITY (id, edad, cond_fisica, actividad_id) values (9, 42, 'ninguna restricción', 9);

insert into CONVENIO_ENTITY(id, nombre, descuento) values(1, 'Compensar', 10);

insert into ATLETA_ENTITY(id, nombre, login ,tipoSangre, direccion, altura, peso) values(1,'Sandr','s.dante', 'A+', 'Calle 1', 1.70, 70);
insert into DEPORTOLOGO_ENTITY(id, nombre, login, experiencia, foto) values(1,'Juan','j.lopez', 'Fisioterapeuta', 'http://dummyimage.com/241x100.png/5fa2dd/ffffff');

insert into RESENIA_ENTITY(id, usuario, puntuacion, comentario, sede_id) values (1, 'dgomezrey', 4, 'Es el mejor gimnasio al que he ido en toda mi vida, excelente servicio. Por favor saquenme de Colombia.', 1);
insert into RESENIA_ENTITY(id, usuario, puntuacion, comentario, sede_id) values (2, 'jp.hernandez', 1, 'Pésimo gimnasio, Daniel tiene problemas. Pero efectivamente saquenme de Colombia.', 1);
insert into RESENIA_ENTITY(id, usuario, puntuacion, comentario, sede_id) values (3, 'jorge1', 5, 'Messi debería venir es muy bueno.', 2);
insert into RESENIA_ENTITY(id, usuario, puntuacion, comentario, sede_id) values (4, 'alberto69', 0, 'Mu malo no me lleven otra vez!', 2);

insert into ACTIVIDAD_ENTITY_SEDES(actividades_id, sedes_id) values (1, 1);
insert into ACTIVIDAD_ENTITY_SEDES(actividades_id, sedes_id) values (2, 2);
insert into ACTIVIDAD_ENTITY_SEDES(actividades_id, sedes_id) values (3, 3);
insert into ACTIVIDAD_ENTITY_SEDES(actividades_id, sedes_id) values (4, 4);
insert into ACTIVIDAD_ENTITY_SEDES(actividades_id, sedes_id) values (5, 5);
insert into ACTIVIDAD_ENTITY_SEDES(actividades_id, sedes_id) values (6, 6);
insert into ACTIVIDAD_ENTITY_SEDES(actividades_id, sedes_id) values (7, 7);
insert into ACTIVIDAD_ENTITY_SEDES(actividades_id, sedes_id) values (8, 8);
