create database if not exists biblioteca;

create table `biblioteca`.`bibliotecas` (
    `id` int not null auto_increment primary key,
    `nombre` varchar(100) not null
);

create table `biblioteca`.`bibliotecarios` (
    `id` int not null auto_increment primary key,
    `nombre` varchar(50) not null,
    `apellido` varchar(30) not null,
    `legajo` int not null,
    `login` int not null, -- no se exactamente para que es esto (ni logout) ni si debe estar en not null
    `logout` int not null,
    `from` date not null,
    `to` date null,
    `id_biblioteca` int not null,
    foreign key (`id_biblioteca`) 
    references `biblioteca`.`bibliotecas`(`id`)
);


create table `biblioteca`.`afiliados` (
    `nombre` varchar(50) not null,
    `apellido` varchar(30) not null,
    `legajo` int not null,
    `id_biblioteca_afiliacion` int not null,
    `login` int not null,
    `logout` int not null,
    foreign key (`id_biblioteca_afiliacion`)
    references `biblioteca`.`bibliotecas`(`id`),
    primary key (`legajo`,`id_biblioteca_afiliacion`)
);

create table `biblioteca`.`ubicaciones` (
    `id` varchar(5) not null primary key,
    `id_biblioteca` int not null,
    foreign key (`id_biblioteca`)
    references `biblioteca`.`bibliotecas`(`id`)
);

create table `biblioteca`.`ejemplares` (
    `id` int not null auto_increment primary key,
    `ISBN` char(13) not null,
    `numero` int not null,
    `id_biblioteca` int not null,
    `id_ubicacion` varchar(5) not null,
    foreign key (`id_biblioteca`)
    references `biblioteca`.`bibliotecas`(`id`),
    foreign key (`id_ubicacion`)
    references `biblioteca`.`ubicaciones`(`id`)
);

create table `biblioteca`.`ciudades` (
    `id` int not null auto_increment primary key,
    `nombre` varchar(50) not null
);

create table `biblioteca`.`editoriales` (
    `id` int not null auto_increment primary key,
    `nombre` varchar(50) not null,
    `id_ciudad` int not null,
    foreign key (`id_ciudad`)
    references `biblioteca`.`ciudades`(`id`)
);

create table `biblioteca`.`autores` (
    `id` int not null auto_increment primary key,
    `nombre` varchar(30) not null,
    `apellido` varchar(30) not null,
    `nacionalidad` varchar(30) null,
    `fecha_nacimiento` date null
);

create table `biblioteca`.`libros` (
    `ISBN` varchar(13) not null primary key,
    `nombre` varchar(100) not null,
    `idioma` varchar(10),
    `genero` varchar(10),
    `id_editorial` int not null,
    `edicion` int not null,
    `paginas` int,
    `year_publicacion` date,
    foreign key (`id_editorial`)
    references `biblioteca`.`editoriales`(`id`)
);

create table `biblioteca`.`libros_autores` (
    `id_autor` int not null,
    `id_libro` varchar(13) not null,
    primary key (`id_autor` ,`id_libro`),
    foreign key (`id_autor`)
    references `biblioteca`.`autores`(`id`),
    foreign key (`id_libro`)
    references `biblioteca`.`libros`(`ISBN`)
);

create table `biblioteca`.`prestamos` (
    `legajo` int not null,
    `id_biblioteca_afiliado` int not null,
    `id_ejemplar` int not null,
    `from` date not null,
    `to` date not null,
    foreign key (`legajo`, `id_biblioteca_afiliado`)
    references `biblioteca`.`afiliados`(`legajo`,`id_biblioteca_afiliacion`),
    foreign key (`id_ejemplar`)
    references `biblioteca`.`ejemplares`(`id`)
);

create table `biblioteca`.`sanciones` (
    `legajo` int not null,
    `id_biblioteca_afiliado` int not null,
    `from` date not null,
    `to` date not null,
    foreign key (`legajo`, `id_biblioteca_afiliado`)
    references `biblioteca`.`afiliados`(`legajo`,`id_biblioteca_afiliacion`)
);

alter table `biblioteca`.`editoriales`
add column ciudad varchar(30),

alter table `biblioteca`.`editoriales`
drop FOREIGN KEY `editoriales_ibfk_1`;

alter table `biblioteca`.`editoriales`
drop column id_ciudad;

drop table `biblioteca`.`ciudades`;

alter table `biblioteca`.`afiliados`
drop column `login`,
drop column `logout`;

alter table `biblioteca`.`afiliados`
add column `fecha_registro` date;
