USE adotecao;
INSERT INTO `adotecao`.`animal` (`animalName`, `age`, `sex`, `insertionDate`, `descricao`, `idOng`, `idAnimalSize`, `idAnimalFurType`, `idAnimalToAnimal`, `idAnimalToPerson`, `idColor`, `idRace`)
VALUES
  ('Rex', '2', '1', '2023-06-21', 'Rex é um adorável cachorro de porte médio, com pelos macios e pretos. Ele é muito brincalhão e adora correr.', 1, 1, 1, 1, 1, 1, 1),
  ('Luna', '3', '2', '2023-06-21', 'Luna é uma gatinha de pelagem cinza e olhos verdes. Ela é calma e adora receber carinho.', 2, 2, 2, 2, 2, 2, 2),
  ('Max', '1', '1', '2023-06-21', 'Max é um cãozinho pequeno e energético. Ele adora brincar com bolinhas e é muito sociável.', 3, 3, 3, 3, 3, 3, 1),
  ('Nina', '4', '2', '2023-06-21', 'Nina é uma gata preta com olhos amarelos. Ela é independente e gosta de explorar seu ambiente.', 1, 1, 1, 4, 4, 4, 2),
  ('Thor', '5', '1', '2023-06-21', 'Thor é um cão de grande porte e muito dócil. Ele é um excelente companheiro para atividades ao ar livre.', 2, 2, 2, 5, 5, 5, 1),
  ('Bella', '2', '2', '2023-06-21', 'Bella é uma gata de pelo branco e olhos azuis. Ela é meiga e adora receber atenção.', 3, 3, 3, 1, 1, 6, 2),
  ('Toby', '3', '1', '2023-06-21', 'Toby é um cão de médio porte, com pelos marrom e branco. Ele é muito obediente e adora aprender truques novos.', 1, 1, 1, 2, 2, 7, 1),
  ('Mia', '1', '2', '2023-06-21', 'Mia é uma gatinha de pelagem laranja e branca. Ela é curiosa e adora explorar todos os cantos da casa.', 2, 2, 2, 3, 3, 8, 2),
  ('Rocky', '4', '1', '2023-06-21', 'Rocky é um cão de porte grande e pelagem marrom. Ele é muito brincalhão e se dá bem com crianças.', 3, 3, 3, 4, 4, 9, 1),
  ('Sophie', '5', '2', '2023-06-21', 'Sophie é uma gata de pelos pretos e olhos verdes. Ela é tranquila e adora tirar sonecas.', 1, 1, 1, 5, 5, 10, 2),
  ('Charlie', '2', '1', '2023-06-21', 'Charlie é um cão de raça mista, com pelos curtos e manchas pretas. Ele é muito amoroso e adora brincar com outros cães.', 2, 2, 2, 1, 1, 1, 1),
  ('Lola', '3', '2', '2023-06-21', 'Lola é uma gatinha de pelagem cinza e branca. Ela é bastante curiosa e adora se esconder em caixas.', 3, 3, 3, 2, 2, 2, 2),
  ('Zeus', '1', '1', '2023-06-21', 'Zeus é um cão de grande porte e muita energia. Ele adora correr e brincar ao ar livre.', 1, 1, 1, 3, 3, 3, 1),
  ('Mimi', '4', '2', '2023-06-21', 'Mimi é uma gata de pelagem preta e olhos amarelos. Ela é bastante independente e adora explorar seu território.', 2, 2, 2, 4, 4, 4, 2),
  ('Oliver', '5', '1', '2023-06-21', 'Oliver é um cão de médio porte, com pelos brancos e manchas marrons. Ele é muito leal e adora acompanhar seus donos.', 3, 3, 3, 5, 5, 5, 1),
  ('Luna', '2', '2', '2023-06-21', 'Luna é uma gatinha de pelagem tigrada e olhos verdes. Ela é brincalhona e adora se divertir com bolinhas.', 1, 1, 1, 1, 1, 6, 2);
  
  -- Cachorros
INSERT INTO image (idAnimal, imageUrl)
SELECT idAnimal, 'https://i0.wp.com/www.portaldodog.com.br/cachorros/wp-content/uploads/2019/01/boo.jpg?resize=600%2C954&ssl=1'
FROM animal
WHERE idRace = 1;

-- Gatos
INSERT INTO image (idAnimal, imageUrl)
SELECT idAnimal, 'https://i.pinimg.com/236x/f5/dd/c6/f5ddc690ab0783246299f9045bb2dec4.jpg'
FROM animal
WHERE idRace = 2;

