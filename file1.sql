create database proiect3;
use  proiect3;

create table Clienti (
ClientID int not null auto_increment primary key,
Nume varchar(45),
Prenume varchar(45),
Adresa varchar(45) ) ;

create table IntermediarCP (
IntermediarCP int not null auto_increment primary key,
ClientID int not null,
ProdusID int not null );

create table ProdusAlimentar (
ProdusID int not null auto_increment primary key,
Denumire varchar(45),
DataProducere datetime,
DataExpirare datetime );

create table IntermediarPP (
IntermediarPP int not null auto_increment primary key,
ProdusID int not null,
ProducatorID int not null );

create table Producatori (
ProducatorID int not null auto_increment primary key,
Denumire varchar(45),
TaraOrigine varchar(45),
Adresa varchar(45) );

create table Users(
	username varchar(50) not null primary key,
	pass binary(32) not null,
    priviledged bool default false );
    
insert into Users(username, pass) values('user', sha2('user', 256));
insert into Users(username, pass, priviledged) values('admin', sha2('admin', 256), true);
select pass from users where username = 'user';

alter table users modify pass char(64);

alter table IntermediarCP
add constraint `fk1` foreign key (ClientID)
references Clienti(ClientID);

alter table IntermediarCP
add constraint `fk2` foreign key (ProdusID)
references ProdusAlimentar(ProdusID);

alter table IntermediarPP
add constraint `fk3` foreign key (ProdusID)
references ProdusAlimentar(ProdusID);

alter table IntermediarPP
add constraint `fk4` foreign key (ProducatorID)
references Producatori(ProducatorID);