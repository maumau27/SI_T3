create table usuarios (
	id int not null auto_increment,
    login_name varchar(255) unique not null,
    nome varchar(255) not null,
	certificado_digital varchar(255),
    -- senha varchar(100) not null,
    senha int(8) not null,
    id_grupo int not null,
    data_bloqueio datetime not null DEFAULT CURRENT_TIMESTAMP(),
    total_acessos int default 0,
    
    primary key usuario_PK(id),
    foreign key usuarios_grupo_FK(id_grupo) REFERENCES grupos(GID)
);

create table grupos(
	GID int not null auto_increment,
    nome varchar(100),
    
    primary key grupos_PK(GID)
);

create table mensagens(
    mensagem varchar(255),
    codigo int not null unique,
    
    primary key mensagens_PK(codigo)
);

create table registros(
	id int not null auto_increment,
    data_ocorrencia datetime not null DEFAULT CURRENT_TIMESTAMP(),
    codigo int,
    id_usuario int,
    
    primary key registros_PK(id),
    foreign key registro_mensagens_FK(codigo) REFERENCES mensagens(codigo)
);