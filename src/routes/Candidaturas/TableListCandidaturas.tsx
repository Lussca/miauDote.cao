import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Button } from '@mui/material';
import CancelIcon from '@mui/icons-material/Cancel';

const TableList = () =>  {

    const animals = [
        { id: 1, name: 'John', age: 25 },
        { id: 2, name: 'Jane', age: 30 },
        { id: 3, name: 'Bob', age: 35 },
    ];

    const tableHeadStyle = {
        backgroundColor: '#ff9900',
    };

    const handleButtonClick = (id: number) => {
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
                {animals.map((row) => (
                    <TableRow key={row.id}>
                        <TableCell>{row.id}</TableCell>
                        <TableCell>{row.name}</TableCell>
                        <TableCell>{row.age}</TableCell>
                        <TableCell>
                            <Button onClick={() => handleButtonClick(row.id)}>
                                <CancelIcon style={{ color: 'red' }}></CancelIcon>
                            </Button>
                        </TableCell>
                    </TableRow>
                ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
  
}

export default TableList