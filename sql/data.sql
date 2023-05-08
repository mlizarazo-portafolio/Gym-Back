insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (1, 'Messi', 'https://www.armandorodriguez.com.ar/img.php?img=caricaturas-lio-messi.jpg&mode=cm&w=350&h=400', 'newells, fc barcelona, psg');
insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (2, 'El bicho', 'https://im.rediff.com/sports/2014/mar/06ronaldo-portugal-record.jpg', 'sporting, manchester united, real madrid, juventus, machester united, al nassr :()');
insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (3, 'Marco Mbapperez', 'https://www.elmundo.com/assets/uploads/files/36586-vladimir-hernandez.jpg', 'Junior');


insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (1, 'Central', 'Calle 31 Norte #7-21', '7788918');
insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (2, 'Occidental', 'Calle 15 Oeste #3-81', '9988112');
insert into SERVICIO_ENTITY (id, servicio, disponible, sede_id) values (1, 'Pesas', True, 1);
insert into SERVICIO_ENTITY (id, servicio, disponible, sede_id) values (2, 'Masajes', True, 2);
insert into PLAN_ENTRENAMIENTO_ENTITY(id, nombre, objetivo_Basico, descripcion, direccion, duracion, costo) values(3, 'Fuerza I', 'Aumento de fuerza', 'Ejercicio basado en fuerza','Cra7',3,3000);
insert into ACTIVIDAD_ENTITY(id, nombre, max_Participantes, horario, tipo, entrenador_id) values (1, 'Yoga', 30, 'Lunes - 12:30 a 2:30', 'Grupal', 1);
insert into RESTRICCION_ENTITY (id, edad, cond_fisica, actividad_id) values (1, 45, 'buena resistencia', 1);
insert into CONVENIO_ENTITY(id, nombre,descuento) values(1,'Compensar', 10);

insert into RESENIA_ENTITY(id, usuario, puntuacion, comentario, sede_id) values (1, 'dgomezrey', 4, 'Es el mejor gimnasio al que he ido en toda mi vida, excelente servicio. Por favor saquenme de Colombia.', 1);
insert into RESENIA_ENTITY(id, usuario, puntuacion, comentario, sede_id) values (2, 'jp.hernandez', 1, 'Pésimo gimnasio, Daniel tiene problemas. Pero efectivamente saquenme de Colombia.', 1);
insert into RESENIA_ENTITY(id, usuario, puntuacion, comentario, sede_id) values (3, 'jorge1', 5, 'Messi debería venir es muy bueno.', 2);
insert into RESENIA_ENTITY(id, usuario, puntuacion, comentario, sede_id) values (4, 'alberto69', 0, 'Mu malo no me lleven otra vez!', 2);
