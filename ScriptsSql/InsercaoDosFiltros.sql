USE adotecao;
-- Inserção na tabela animalSize
INSERT INTO `adotecao`.`animalSize` (`size`) VALUES
('pequeno'),
('medio'),
('grande');

-- Inserção na tabela animalFurType
INSERT INTO `adotecao`.`animalFurType` (`furType`) VALUES
('pequeno'),
('medio'),
('longo');

-- Inserção na tabela animalToAnimal
INSERT INTO `adotecao`.`animalToAnimal` (`behavior`) VALUES
('manso'),
('agressivo'),
('agitado'),
('amigavel'),
('amedrontado');

-- Inserção na tabela animalToPerson
INSERT INTO `adotecao`.`animalToPerson` (`behavior`) VALUES
('manso'),
('agressivo'),
('agitado'),
('amigavel'),
('amedrontado');

-- Inserção na tabela color
INSERT INTO `adotecao`.`color` (`color`) VALUES
('marrom'),
('branco'),
('preto'),
('dourado'),
('bege'),
('laranja'),
('rajado'),
('cinza'),
('bicolor'),
('tricolor');

-- Inserção na tabela race
INSERT INTO `adotecao`.`race` (`race`) VALUES
('cachorro'),
('gato');

UPDATE `adotecao`.`race` SET `race` = 'gato' WHERE idRace = 2;