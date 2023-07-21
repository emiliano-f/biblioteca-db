-- Ciudades
insert into `ciudades`(`nombre`)
values ("Buenos Aires"),
        ("Montevideo"),
        ("Londres"),
        ("Medellin"),
        ("Mexico DF"),
        ("Monterrey"),
        ("Caracas"),
        ("Santiago de Chile"),
        ("Madrid"),
        ("Barcelona");

-- Bibliotecas
alter table `biblioteca`.`bibliotecas`
add `dependencia` varchar(30);

insert into `bibliotecas`(`nombre`, `dependencia`)
values ("Juan Bautista Alberdi", "Fac. de Derecho"),
        ("Prof. Consulto Jose Luis Pascal", "Fac. de Odontología"),
        ("Egidio Feruglio", "Fac. de Ingeniería"),
        ("Filosofía y Letras", "Fac. de Filosofía y Letras");
