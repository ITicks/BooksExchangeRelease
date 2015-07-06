INSERT INTO Utente(nome, cognome, username, password, email, indirizzo, comune, provincia, regione, foto, n_cell, confermato) 
VALUES ('Federico', 'Bianchi', 'fede', 'bian', 'fbianchi429@gmail.com', 'via Roma', 'Verona', 'Verona', 'Veneto', 'avatar_anonimo.jpg', '3494241067', 'true');

INSERT INTO Utente(nome, cognome, username, password, email, indirizzo, comune, provincia, regione, foto, n_cell, confermato) 
VALUES ('Matteo', 'Olivato', 'teo', 'ol', 'matteoolivato93@gmail.com', 'corso Cavour', 'Verona', 'Verona', 'Veneto', 'avatar_anonimo.jpg', '3495467098', 'true');

INSERT INTO Utente(nome, cognome, username, password, email, indirizzo, comune, provincia, regione, foto, n_cell, confermato) 
VALUES ('Francesco', 'Furlani', 'fran', 'furl', 'francesco.furlani@studenti.univr.it', 'via Matteotti', 'Verona', 'Verona', 'Veneto', 'avatar_anonimo.jpg', '3492231045', 'true');



INSERT INTO Amministrazione(nome, cognome, username, password, email, n_cell, tipo, last_login, last_logout) 
VALUES ('Gianni', 'Rossi', 'gian', 'amm', 'amm@book-exchange.it', '3405678987', 'amministratore', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO Amministrazione(nome, cognome, username, password, email, n_cell, tipo, last_login, last_logout) 
VALUES ('Mario', 'Verdi', 'mar', 'corr', 'corr@book-exchange.it', '3403238987', 'correttore', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO Libro(ISBN, autore, titolo, genere, descrizione, anno, casa_editrice, lingua, pagine, foto, n_visualizzazioni) 
VALUES ('8838933448', 'Andrea Camilleri', 'La giostra degli scambi', 'ROMANZO', 'Copertina flessibile', '2015-01-01', 'Sellerio Editore Palermo', 'Italiano', '257', 'lagiostradegliscambi.jpg', '0');

INSERT INTO Libro(ISBN, autore, titolo, genere, descrizione, anno, casa_editrice, lingua, pagine, foto, n_visualizzazioni) 
VALUES ('8838933449', 'George R.R. Martina', 'Il trono di Spade, Il Grande Inverno', 'FANTASY', 'Copertina rigida', '2013-01-01', 'Mondadori', 'Italiano', '654', 'iltronodispade.jpg', '0');

INSERT INTO Libro(ISBN, autore, titolo, genere, descrizione, anno, casa_editrice, lingua, pagine, foto, n_visualizzazioni) 
VALUES ('8838933450', 'Stephen King', 'The Dome', 'HORROR', 'Copertina rigida', '2013-01-01', 'Sperling & Kupfer', 'Italiano', '800', 'thedome.jpg', '0');

INSERT INTO Libro(ISBN, autore, titolo, genere, descrizione, anno, casa_editrice, lingua, pagine, foto, n_visualizzazioni) 
VALUES ('8838933451', 'Italo Calvino', 'Il barone rampante', 'ROMANZO', 'Copertina flessibile', '1993-01-01', 'Mondadori', 'Italiano', '319', 'ilbaronerampante.jpg', '0');

INSERT INTO Libro(ISBN, autore, titolo, genere, descrizione, anno, casa_editrice, lingua, pagine, foto, n_visualizzazioni) 
VALUES ('8838933452', 'Shakespeare', 'Romeo and Juliet', 'ROMANZO', 'Copertina flessibile', '2007-01-01', 'Reading and Training', 'Inglese', '112', 'romeoandjuliet.jpg', '0');

INSERT INTO Libro(ISBN, autore, titolo, genere, descrizione, anno, casa_editrice, lingua, pagine, foto, n_visualizzazioni) 
VALUES ('8838933453', 'Dante Alighieri', 'Divina Commedia', 'COMMEDIA', 'Copertina rigida', '2014-01-01', 'Newton Compton', 'Italiano', '664', 'divinacommedia.jpg', '0');


INSERT INTO Inserzione(id_utente, id_amministrazione, codice_libro, disponibilita) 
VALUES (1, 1, 5, 'true');

INSERT INTO Inserzione(id_utente, id_amministrazione, codice_libro, disponibilita)
VALUES (2, 1, 1, 'true');

INSERT INTO Inserzione(id_utente, id_amministrazione, codice_libro, disponibilita)
VALUES (2, 1, 2, 'true');

INSERT INTO Inserzione(id_utente, id_amministrazione, codice_libro, disponibilita)
VALUES (2, NULL, 3, 'true');

INSERT INTO Inserzione(id_utente, id_amministrazione, codice_libro, disponibilita)
VALUES (2, NULL, 4, 'true');
