 drop database if exists adotecao;
 CREATE DATABASE adotecao;

USE adotecao;

CREATE TABLE adress (
  idAdress INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  state VARCHAR(50) NOT NULL,
  city VARCHAR(50) NOT NULL,
  neighbor VARCHAR(50) NOT NULL,
  cep VARCHAR(10) NOT NULL,
  rua VARCHAR(150) NOT NULL,
  numero VARCHAR(10)
);

CREATE TABLE userOng (
  idOng INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  login VARCHAR(50) NOT NULL,
  pw VARCHAR(64) NOT NULL,
  username VARCHAR(50) NOT NULL,
  cpf VARCHAR(14) NOT NULL,
  birth DATE NOT NULL,
  ongName VARCHAR(100) NOT NULL,
  publicKey VARCHAR(1000) NOT NULL,
  privateKey VARCHAR(1000) NOT NULL,
  idAdress INT NOT NULL,
  jwt VARCHAR(750),
  FOREIGN KEY (idAdress) REFERENCES adress(idAdress)
);
CREATE TABLE animal (
  idAnimal INT PRIMARY KEY auto_increment,
  race CHAR(1),
  animalName VARCHAR(50),
  size CHAR(1),
  hairType CHAR(1),
  animalToAnimal CHAR(1),
  animalToPerson CHAR(1),
  sex CHAR(1),
  age INT,
  idOng INT,
  insertionDate DATE,
  FOREIGN KEY (idOng) REFERENCES userOng(idOng) ON DELETE CASCADE
);

CREATE TABLE image (
  idImage INT PRIMARY KEY auto_increment,
  idAnimal INT,
  imageUrl VARCHAR(255),
  FOREIGN KEY (idAnimal) REFERENCES animal(idAnimal) ON DELETE CASCADE
);


CREATE TABLE userAdopter (
  idAdopter INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  login VARCHAR(100) NOT NULL,
  pw VARCHAR(64) NOT NULL,
  cpf VARCHAR(14) NOT NULL,
  birth DATE NOT NULL,
  publicKey VARCHAR(1000) NOT NULL,
  privateKey VARCHAR(1000) NOT NULL,
  idAdress INT NOT NULL,
  jwt VARCHAR(750),
  FOREIGN KEY (idAdress) REFERENCES adress(idAdress)
);
CREATE TABLE animalAdoption (
  idAnimal INT,
  idAdopter INT,
  FOREIGN KEY (idAnimal) REFERENCES animal(idAnimal),
  FOREIGN KEY (idAdopter) REFERENCES userAdopter(idAdopter) ON DELETE CASCADE,
  PRIMARY KEY (idAnimal, idAdopter)
);

-- Se não tiver no minimo 3 ONGs criadas a query para inserção de animais não irá funcionar
INSERT INTO animal (race, animalName, size, hairType, animalToAnimal, animalToPerson, sex, color, animalDescription, age, idOng, insertionDate)
VALUES 
  ('Cachorro', 'Rex', 'Grande', 'Curto', 'Amigável', 'Amigável', 'Macho', 'Marrom', 'Rex é um cachorro brincalhão e adora crianças.', 3, 1, '2022-01-01'),
  ('Gato', 'Mia', 'Médio', 'Curto', 'Amigável', 'Amigável', 'Fêmea', 'Cinza', 'Mia é uma gatinha muito carinhosa e adora colo.', 2, 1, '2022-02-02'),
  ('Cachorro', 'Max', 'Grande', 'Longo', 'Manso', 'Amigável', 'Macho', 'Preto', 'Max é um cão protetor e se dá bem com pessoas.', 4, 2, '2022-03-03'),
  ('Gato', 'Luna', 'Pequeno', 'Curto', 'Amigável', 'Amedrontado', 'Fêmea', 'Branco', 'Luna é uma gatinha brincalhona e prefere ser filha única.', 1, 2, '2022-04-04'),
  ('Cachorro', 'Bolinha', 'Pequeno', 'Curto', 'Amigável', 'Amigável', 'Fêmea', 'Marrom', 'Bolinha é uma cachorrinha muito dócil e se adapta bem a outros animais.', 2, 2, '2022-05-05'),
  ('Gato', 'Thor', 'Médio', 'Longo', 'Amigável', 'Amigável', 'Macho', 'Preto e branco', 'Thor é um gatinho brincalhão e se dá bem com crianças.', 3, 2, '2022-06-06'),
  ('Cachorro', 'Nina', 'Pequeno', 'Curto', 'Amigável', 'Amigável', 'Fêmea', 'Marrom', 'Nina é uma cachorrinha ativa e adora brincar ao ar livre.', 2, 3, '2022-07-07'),
  ('Gato', 'Oliver', 'Médio', 'Longo', 'Amigável', 'Agressivo', 'Macho', 'Amarelo', 'Oliver é um gatinho independente e prefere um ambiente tranquilo.', 1, 3, '2022-08-08'),
  ('Cachorro', 'Bela', 'Grande', 'Curto', 'Manso', 'Amigável', 'Fêmea', 'Preto e branco', 'Bela é uma cadela protetora e se dá bem com pessoas.', 3, 3, '2022-09-09'),
  ('Gato', 'Milo', 'Pequeno', 'Curto', 'Manso', 'Manso', 'Macho', 'Cinza', 'Milo é um gatinho arisco e prefere viver sozinho.', 2, 3, '2022-10-10'),
  ('Cachorro', 'Bob', 'Médio', 'Curto', 'Amigável', 'Amigável', 'Macho', 'Marrom', 'Bob é um cão brincalhão e se adapta bem a outros animais.', 4, 1, '2022-11-11'),
  ('Gato', 'Lola', 'Pequeno', 'Curto', 'Amigável', 'Amigável', 'Fêmea', 'Branco', 'Lola é uma gatinha carinhosa e se dá bem com crianças.', 1, 1, '2022-12-12'),
  ('Cachorro', 'Buddy', 'Grande', 'Longo', 'Amigável', 'Amigável', 'Macho', 'Preto', 'Buddy é um cachorro leal e adora brincar ao ar livre.', 3, 2, '2023-01-01'),
  ('Gato', 'Minnie', 'Médio', 'Curto', 'Amigável', 'Agressivo', 'Fêmea', 'Cinza', 'Minnie é uma gatinha calma e prefere um ambiente tranquilo.', 2, 2, '2023-02-02'),
  ('Cachorro', 'Toby', 'Pequeno', 'Curto', 'Amigável', 'Amigável', 'Macho', 'Preto e marrom', 'Toby é um cachorrinho amoroso e se adapta bem a outros animais.', 1, 3, '2023-03-03'),
  ('Gato', 'Simba', 'Médio', 'Longo', 'Manso', 'Amigável', 'Macho', 'Laranja', 'Simba é um gato territorial e se dá bem com pessoas.', 4, 3, '2023-04-04');