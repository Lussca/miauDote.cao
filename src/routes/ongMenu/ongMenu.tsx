//imports padrão react
import styles from'./ongMenu.module.css';
import axios, { AxiosRequestConfig } from 'axios';
import { useEffect, useState } from 'react';

//components
import { Navbar } from './Navbar/Navbar';
import AnimalModalAddEdit from './DialogAnimlByOng/DialogAnimalByOng';

//imports MUI
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TablePagination, Paper, IconButton } from '@mui/material';
import { Delete, Delete as DeleteIcon, Edit, Edit as EditIcon } from '@mui/icons-material';

interface Animal {
  age: string,
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
  size: string,
}

function ongMenu(this: any)  {

  const idUser = sessionStorage.getItem("userId");
  const [animals, setAnimals] = useState<{
    age: string,
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
    size: string,
  }[]>([]);
  
  const [animalData, setAnimalData] = useState<Animal | null>(null);

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

  useEffect(() => {
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
          fetchData();
        })
        .catch(error => {
          console.error('Erro:', error);
          fetchData();
        });
  };

  const handleEdit = (animalId: string) => {
    const animal = animals.find(animal => animal.id === animalId);
    console.log(animal)
    if (animal) {
      openModalRegister(animal);
    }
  };

  const [openModal, setOpenModal] = useState(false);

  const openModalRegister = (animalData: Animal | null = null) => {
      setOpenModal(true);
      setAnimalData(animalData);
  }

  return (
    <div className={styles.menuArea}>
      <Navbar></Navbar>
      {animals.length === 0 ? (
        <div className={styles.header}>
          <p>Não encontramos nenhum animal!</p>
        </div>
      ) : (
        <div className={styles.tableAnimal}>
          <TableContainer component={Paper} style={{ width: '60%' }}>
            <Table>
              <TableHead style={{ backgroundColor: 'rgb(255, 115, 8)' }}>
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
                    <TableCell style={{ width: '10%' }}>
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
          <AnimalModalAddEdit
            open={openModal}
            onClose={() => setOpenModal(false)}
            animalData={animalData as Animal}
          />
        </div>
      )}
    </div>
  );
  
}

export default ongMenu