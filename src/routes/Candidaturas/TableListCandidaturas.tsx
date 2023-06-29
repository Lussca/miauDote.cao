//imports react 
import axios from 'axios';
import { useEffect, useState } from 'react';

// imports MUI
import { Button } from '@mui/material';
import Table from '@mui/material/Table';
import Paper from '@mui/material/Paper';
import TableRow from '@mui/material/TableRow';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import CancelIcon from '@mui/icons-material/Cancel';
import TableContainer from '@mui/material/TableContainer';

const TableList = () =>  {

    const [candidaturas, setCandidaturas] = useState<{ 
        age: string; 
        animalDescription: string; 
        animalToAnimal: string; 
        animalToPerson: string; 
        color: string; 
        hairType: string; 
        id: string; 
        idOng: string; 
        name: string; 
        ongName: string; 
        race: string; 
        sex: string; 
        size: string 
    }[]>([]);

    const [deletionOccurred, setDeletionOccurred] = useState(false);
    const [error, setError] = useState(false);

    const params = {
        idUser: sessionStorage.getItem('userId'),
    };

    useEffect(() => {
        const fetchData = async () => {

            axios
            .get('http://localhost:8080/MiauDoteCao/AdoptionApplicationServlet', { params })
            .then(response => {
                setCandidaturas(response.data.animals);
                setError(false);
            })
            .catch(error => {
                console.error('Erro:', error);
                setError(true);
            });
        };
      
        fetchData();
    }, [deletionOccurred]);

    const tableHeadStyle = {
        backgroundColor: '#ff9900',
    };

    const handleButtonClick = (id: string) => {

        const idUser = sessionStorage.getItem('userId');

        axios
            .delete('http://localhost:8080/MiauDoteCao/AdoptionApplicationServlet',{
                data: { id: idUser, idAnimal: id },
            })
            .then(response => {
                console.log( response.data)
                setDeletionOccurred(prev => !prev);
            })
            .catch(error => {
                console.error('Erro:', error);
            });

    };

    return (
        <TableContainer component={Paper} style={{ width: '50%' }}>
          <Table>
            <TableHead>
              <TableRow style={tableHeadStyle}>
                <TableCell>ID</TableCell>
                <TableCell>Nome do Animal</TableCell>
                <TableCell>Raça do Animal</TableCell>
                <TableCell>ONG</TableCell>
                <TableCell style={{ width: '10%' }}>Ações</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
            {candidaturas.length === 0 ? (
                <TableRow>
                    <TableCell colSpan={5} align="center">
                    Nenhuma informação encontrada.
                    </TableCell>
                </TableRow>
                ) : (
                candidaturas.map((candidatura, index) => (
                    <TableRow key={index}>
                    <TableCell>{candidatura.id}</TableCell>
                    <TableCell>{candidatura.name}</TableCell>
                    <TableCell>{candidatura.race}</TableCell>
                    <TableCell>{candidatura.ongName}</TableCell>
                    <TableCell>
                        <Button onClick={() => handleButtonClick(candidatura.id)}>
                            <CancelIcon style={{ color: 'red' }}></CancelIcon>
                        </Button>
                    </TableCell>
                </TableRow>
                ))  
            )}
            </TableBody>
          </Table>
        </TableContainer>
      );
  
}

export default TableList