-- MySQL Script generated by MySQL Workbench
-- Wed Jun 21 23:09:45 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema adotecao
-- -----------------------------------------------------
-- drop database adotecao;
-- -----------------------------------------------------
-- Schema adotecao
-- -----------------------------------------------------
CREATE DATABASE IF NOT EXISTS `adotecao` DEFAULT CHARACTER SET utf8 ;
USE `adotecao` ;

-- -----------------------------------------------------
-- Table `adotecao`.`adress`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`adress` (
  `idAdress` INT NOT NULL AUTO_INCREMENT,
  `state` VARCHAR(75) NOT NULL,
  `city` VARCHAR(75) NOT NULL,
  `neighbor` VARCHAR(75) NOT NULL,
  `cep` VARCHAR(10) NOT NULL,
  `street` VARCHAR(150) NOT NULL,
  `number` VARCHAR(10),
  PRIMARY KEY (`idAdress`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adotecao`.`userOng`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`userOng` (
  `idOng` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(75) NOT NULL,
  `pw` VARCHAR(64) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `cpf` VARCHAR(14) NOT NULL,
  `birth` DATE NOT NULL,
  `ongName` VARCHAR(100) NOT NULL,
  `publicKey` VARCHAR(1000) NOT NULL,
  `privateKey` VARCHAR(1000) NOT NULL,
  `jwt` VARCHAR(750) NULL,
  `idAdress` INT NOT NULL,
  `validationCode` VARCHAR(64),
  PRIMARY KEY (`idOng`),
  INDEX `fk_userOng_adress_idx` (`idAdress` ASC) VISIBLE,
  CONSTRAINT `fk_userOng_adress`
    FOREIGN KEY (`idAdress`)
    REFERENCES `adotecao`.`adress` (`idAdress`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adotecao`.`userAdopter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`userAdopter` (
  `idAdopter` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(75) NOT NULL,
  `pw` VARCHAR(64) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `cpf` VARCHAR(14) NOT NULL,
  `birth` DATE NOT NULL,
  `publicKey` VARCHAR(1000) NOT NULL,
  `privateKey` VARCHAR(1000) NOT NULL,
  `jwt` VARCHAR(750) NULL,
  `idAdress` INT NOT NULL,
  `validationCode` VARCHAR(64),
  PRIMARY KEY (`idAdopter`),
  INDEX `fk_userOng_adress_idx` (`idAdress` ASC) VISIBLE,
  CONSTRAINT `fk_userOng_adress0`
    FOREIGN KEY (`idAdress`)
    REFERENCES `adotecao`.`adress` (`idAdress`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adotecao`.`animalSize`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`animalSize` (
  `idAnimalSize` INT NOT NULL AUTO_INCREMENT,
  `size` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idAnimalSize`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adotecao`.`animalFurType`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`animalFurType` (
  `idAnimalFurType` INT NOT NULL AUTO_INCREMENT,
  `furType` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idAnimalFurType`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adotecao`.`animalToAnimal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`animalToAnimal` (
  `idAnimalToAnimal` INT NOT NULL AUTO_INCREMENT,
  `behavior` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idAnimalToAnimal`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adotecao`.`animalToPerson`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`animalToPerson` (
  `idAnimalToPerson` INT NOT NULL AUTO_INCREMENT,
  `behavior` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idAnimalToPerson`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adotecao`.`color`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`color` (
  `idColor` INT NOT NULL AUTO_INCREMENT,
  `color` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idColor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adotecao`.`raca`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`race` (
  `idRace` INT NOT NULL AUTO_INCREMENT,
  `race` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`idRace`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adotecao`.`animal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`animal` (
  `idAnimal` INT NOT NULL AUTO_INCREMENT,
  `animalName` VARCHAR(45) NOT NULL,
  `age` VARCHAR(2) NOT NULL,
  `sex` VARCHAR(20) NOT NULL,
  `insertionDate` DATE NOT NULL,
  `descricao` VARCHAR(300) NULL,
  `idOng` INT NOT NULL,
  `idAnimalSize` INT NOT NULL,
  `idAnimalFurType` INT  NOT NULL,
  `idAnimalToAnimal` INT NOT NULL,
  `idAnimalToPerson` INT NOT NULL,
  `idColor` INT NOT NULL,
  `idRace` INT NOT NULL,
  PRIMARY KEY (`idAnimal`),
  INDEX `fk_animal_userOng1_idx` (`idOng` ASC) VISIBLE,
  INDEX `fk_animal_animalSize1_idx` (`idAnimalSize` ASC) VISIBLE,
  INDEX `fk_animal_animalFurType1_idx` (`idAnimalFurType` ASC) VISIBLE,
  INDEX `fk_animal_animalToAnimal1_idx` (`idAnimalToAnimal` ASC) VISIBLE,
  INDEX `fk_animal_animalToPerson1_idx` (`idAnimalToPerson` ASC) VISIBLE,
  INDEX `fk_animal_color1_idx` (`idColor` ASC) VISIBLE,
  INDEX `fk_animal_race1_idx` (`idRace` ASC) VISIBLE,
  CONSTRAINT `fk_animal_userOng1`
    FOREIGN KEY (`idOng`)
    REFERENCES `adotecao`.`userOng` (`idOng`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_animal_animalSize1`
    FOREIGN KEY (`idAnimalSize`)
    REFERENCES `adotecao`.`animalSize` (`idAnimalSize`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_animal_animalFurType1`
    FOREIGN KEY (`idAnimalFurType`)
    REFERENCES `adotecao`.`animalFurType` (`idAnimalFurType`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_animal_animalToAnimal1`
    FOREIGN KEY (`idAnimalToAnimal`)
    REFERENCES `adotecao`.`animalToAnimal` (`idAnimalToAnimal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_animal_animalToPerson1`
    FOREIGN KEY (`idAnimalToPerson`)
    REFERENCES `adotecao`.`animalToPerson` (`idAnimalToPerson`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_animal_color1`
    FOREIGN KEY (`idColor`)
    REFERENCES `adotecao`.`color` (`idColor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_animal_race1`
    FOREIGN KEY (`idRace`)
    REFERENCES `adotecao`.`race` (`idRace`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adotecao`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`image` (
  `idImage` INT NOT NULL AUTO_INCREMENT,
  `imageUrl` VARCHAR(255) NOT NULL,
  `idAnimal` INT NOT NULL,
  PRIMARY KEY (`idImage`),
  INDEX `fk_image_animal1_idx` (`idAnimal` ASC) VISIBLE,
  CONSTRAINT `fk_image_animal1`
    FOREIGN KEY (`idAnimal`)
    REFERENCES `adotecao`.`animal` (`idAnimal`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
) ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `adotecao`.`application`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adotecao`.`application` (
  `idAdopter` INT NOT NULL,
  `idAnimal` INT NOT NULL,
  PRIMARY KEY (`idAdopter`, `idAnimal`),
  INDEX `fk_userAdopter_has_animal_animal1_idx` (`idAnimal` ASC) VISIBLE,
  INDEX `fk_userAdopter_has_animal_userAdopter1_idx` (`idAdopter` ASC) VISIBLE,
  CONSTRAINT `fk_userAdopter_has_animal_userAdopter1`
    FOREIGN KEY (`idAdopter`)
    REFERENCES `adotecao`.`userAdopter` (`idAdopter`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_userAdopter_has_animal_animal1`
    FOREIGN KEY (`idAnimal`)
    REFERENCES `adotecao`.`animal` (`idAnimal`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
) ENGINE = InnoDB;




SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;