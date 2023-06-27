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

    const [candidaturas, setCandidaturas] = useState<{ id: string; name: string; idOng: string }[]>([]);

    const params = {
        idUser: sessionStorage.getItem('userId'),
    };

    useEffect(() => {
        const fetchData = async () => {

        axios
            .get('http://localhost:8080/MiauDoteCao/AdoptionApplicationServlet', { params })
            .then(response => {
                console.log( response.data)
                setCandidaturas(response.data);
            })
            .catch(error => {
                console.error('Erro:', error);
            });
        };
      
        fetchData();
    }, []);

    const tableHeadStyle = {
        backgroundColor: '#ff9900',
    };

    const handleButtonClick = (id: string) => {
        console.log(`Botão clicado para o ID ${id}`);
    };

    return (
        <TableContainer component={Paper} style={{ width: '50%' }}>
          <Table>
            <TableHead>
              <TableRow style={tableHeadStyle}>
                <TableCell>ID</TableCell>
                <TableCell>Nome do Animal</TableCell>
                <TableCell>ONG</TableCell>
                <TableCell style={{ width: '10%' }}>Ações</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
            {candidaturas.map((candidatura, index) => (
                <TableRow key={index}>
                    <TableCell>{candidatura}</TableCell>
                    <TableCell>{candidatura}</TableCell>
                    <TableCell>{candidatura}</TableCell>
                    <TableCell>
                    {/* <Button onClick={() => handleButtonClick(row.id)}>
                        <CancelIcon style={{ color: 'red' }}></CancelIcon>
                    </Button> */}
                    </TableCell>
                </TableRow>
            ))}
            </TableBody>
          </Table>
        </TableContainer>
      );
  
}

export default TableList