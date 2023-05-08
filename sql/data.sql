insert into ENTRENADOR_ENTITY (id, nombre, foto, trayectoria) values (1, 'Messi', 'http://dummyimage.com/241x100.png/5fa2dd/ffffff', 'newells, fc barcelona, psg');
insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (1, 'Central', 'Calle 31 Norte #7-21', '7788918');
insert into SEDE_ENTITY (id, nombre, direccion, telefono) values (2, 'Occidental', 'Calle 15 Oeste #3-81', '9988112');
insert into SERVICIO_ENTITY (id, servicio, disponible, sede_id) values (1, 'Pesas', True, 1);
insert into SERVICIO_ENTITY (id, servicio, disponible, sede_id) values (2, 'Masajes', True, 2);
insert into PLAN_ENTRENAMIENTO_ENTITY(id, nombre, objetivo_Basico, descripcion, direccion, duracion, costo) values(3, 'Fuerza I', 'Aumento de fuerza', 'Ejercicio basado en fuerza','Cra7',3,3000);
insert into ACTIVIDAD_ENTITY(id, nombre, max_Participantes, horario, tipo, entrenador_id) values (1, 'Yoga', 30, 'Lunes - 12:30 a 2:30', 'Grupal', 1);
insert into RESTRICCION_ENTITY (id, edad, cond_fisica, actividad_id) values (1, 45, 'buena resistencia', 1);
insert into CONVENIO_ENTITY(id, nombre,descuento) values(1,'Compensar', 10);
