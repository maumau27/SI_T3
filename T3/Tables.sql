create table usuarios (
	id int not null auto_increment,
    email varchar(255) unique not null,
    senha int(8) not null,
    primary key usuario_PK(id)
)