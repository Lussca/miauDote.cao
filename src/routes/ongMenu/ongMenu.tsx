//imports padrão react
import styles from'./ongMenu.module.css';
import axios, { AxiosRequestConfig } from 'axios';
import { useEffect, useState } from 'react';

//components
import { Navbar } from './Navbar/Navbar';
import dogAnimation from '../adotanteMenu/dog/dogAnimation';

//imports MUI
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TablePagination, Paper, IconButton } from '@mui/material';
import { Delete, Delete as DeleteIcon, Edit, Edit as EditIcon } from '@mui/icons-material';

interface Animal {
  id: number;
  nome: string;
  idade: number;
  raca: string;
  pelagem: string;
}

function adotanteMenu(this: any)  {

  const idUser = sessionStorage.getItem("userId");
  const [animals, setAnimals] = useState<{
    age:string, 
    animalDescription: string, 
    animalToAnimal: string, 
    animalToPerson: string, 
    color: string, 
    hairType: string, 
    id: string, 
    idOng: string, 
    insertionDate: string, 
    name: string, 
    race: string,
    sex: string,
    size: string,}[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      const config: AxiosRequestConfig = {
        params: {
          idUser: idUser,
        },
      };

      axios
        .get('http://localhost:8080/MiauDoteCao/GetAnimalsByOngId', config)
        .then(response => {
          setAnimals(response.data.animals);
        })
        .catch(error => {
          console.error('Erro:', error);
        });
    };

    fetchData();
  }, []);

  const handleDelete = (animalId: string) => {

    const idUser = sessionStorage.getItem("userId");
    const idAnimal = animalId;

    const Animal: AxiosRequestConfig = {
      data: {
        idAnimal: idAnimal,
        idUser: idUser,
      },
      headers: {
        'Content-Type': 'application/json',
      },
    };

    axios
        .delete('http://localhost:8080/MiauDoteCao/PetUpdateServlet', Animal)
        .then(response => {
          console.log("Deu certo")
        })
        .catch(error => {
          console.error('Erro:', error);
        });
  };

  const handleEdit = (animalId: string) => {
    // Lógica para editar o animal com o ID fornecido
    console.log(`Editar animal com ID ${animalId}`);
  };

  return (
    <div className={styles.menuArea}>
      <Navbar></Navbar>
      {animals.length === 0 ? (
        <div className={styles.header}>
          <p>Não encontramos nenhum animal!</p>
        </div>
      ) : (
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell>Nome do Animal</TableCell>
                <TableCell>Idade</TableCell>
                <TableCell>Raça</TableCell>
                <TableCell>Pelagem</TableCell>
                <TableCell>Ações</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {animals.map(animal => (
                <TableRow key={animal.id}>
                  <TableCell>{animal.id}</TableCell>
                  <TableCell>{animal.name}</TableCell>
                  <TableCell>{animal.age}</TableCell>
                  <TableCell>{animal.race}</TableCell>
                  <TableCell>{animal.hairType}</TableCell>
                  <TableCell>
                  <IconButton onClick={() => handleDelete(animal.id)}>
                      <Delete />
                    </IconButton>
                    <IconButton onClick={() => handleEdit(animal.id)}>
                      <Edit />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </div>
  );
  
}

export default adotanteMenu