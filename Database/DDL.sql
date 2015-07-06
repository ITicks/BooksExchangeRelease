-- Creazione tabelle

CREATE TABLE Utente
(

id SERIAL PRIMARY KEY,
nome VARCHAR(50) NOT NULL,
cognome VARCHAR(50) NOT NULL,
username VARCHAR(50) UNIQUE,
password VARCHAR(50) NOT NULL,
email VARCHAR(100) NOT NULL,
indirizzo VARCHAR(100) NOT NULL,
comune VARCHAR(50) NOT NULL,
provincia VARCHAR(50) NOT NULL,
regione VARCHAR(50) NOT NULL,
foto VARCHAR(100),
n_cell VARCHAR(50),
confermato BOOLEAN

);

CREATE TABLE Amministrazione
(

id SERIAL PRIMARY KEY,
nome VARCHAR(50) NOT NULL,
cognome VARCHAR(50) NOT NULL,
username VARCHAR(50) UNIQUE,
password VARCHAR(50) NOT NULL,
email VARCHAR(100) NOT NULL,
n_cell VARCHAR(50) NOT NULL,
tipo VARCHAR(50) NOT NULL,
last_login TIMESTAMP,
last_logout TIMESTAMP

);

CREATE TABLE Libro
(

codice SERIAL PRIMARY KEY,
ISBN VARCHAR(50) NOT NULL,
autore VARCHAR(100) NOT NULL,
titolo VARCHAR(50) NOT NULL,
genere VARCHAR(50) NOT NULL,
descrizione VARCHAR(200),
anno DATE,
casa_editrice VARCHAR(100) NOT NULL,
lingua VARCHAR(50) NOT NULL,
pagine INTEGER,
foto VARCHAR(100) NOT NULL,
n_visualizzazioni INTEGER

);

CREATE TABLE Inserzione
(

id_utente INTEGER,
id_amministrazione INTEGER,
codice_libro INTEGER,
disponibilita BOOLEAN,

PRIMARY KEY(id_utente, codice_libro),
FOREIGN KEY(id_utente) REFERENCES Utente(id),
FOREIGN KEY(codice_libro) REFERENCES Libro(codice),
FOREIGN KEY(id_amministrazione) REFERENCES Amministrazione(id)

);

