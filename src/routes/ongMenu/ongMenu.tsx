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
  const [animals, setAnimals] = useState<any[]>([]);

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
          setAnimals(response.data);
        })
        .catch(error => {
          console.error('Erro:', error);
        });
    };

    fetchData();
  }, []);

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
                  <TableCell>{animal.nome}</TableCell>
                  <TableCell>{animal.idade}</TableCell>
                  <TableCell>{animal.raca}</TableCell>
                  <TableCell>{animal.pelagem}</TableCell>
                  <TableCell>
                    <Delete />
                    <Edit />
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