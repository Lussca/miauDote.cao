//imports padrão react
import styles from'./ongMenu.module.css';

//components
import { Navbar } from './Navbar/Navbar';

//imports MUI
import React, { useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TablePagination, Paper } from '@mui/material';

const data = [
  { id: 1, name: 'Item 1' },
  { id: 2, name: 'Item 2' },
  // ... adicione mais itens aqui
  { id: 30, name: 'Item 30' },
];

const itemsPerPage = 30;

function adotanteMenu(this: any)  {

  const [page, setPage] = useState(0);

  const handleChangePage = (event: any, newPage: React.SetStateAction<number>) => {
    setPage(newPage);
  };

  const rowsPerPage = itemsPerPage;
  const emptyRows = rowsPerPage - Math.min(rowsPerPage, data.length - page * rowsPerPage);

  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>ID</TableCell>
            <TableCell>Nome do animal</TableCell>
            <TableCell>Idade do animal</TableCell>
            <TableCell>Cor do animal</TableCell>
            <TableCell>Editar</TableCell>
            <TableCell>Ecxluir</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {data.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((item) => (
            <TableRow key={item.id}>
              <TableCell>{item.id}</TableCell>
              <TableCell>{item.nameAnimal}</TableCell>
              <TableCell>{item.ageAnimal}</TableCell>
              <TableCell>{item.colorAnimal}</TableCell>
              <TableCell>{item.update}</TableCell>
              <TableCell>{item.delete}</TableCell>
            </TableRow>
          ))}
          {emptyRows > 0 && (
            <TableRow style={{ height: 53 * emptyRows }}>
              <TableCell colSpan={2} />
            </TableRow>
          )}
        </TableBody>
      </Table>
      <TablePagination
        rowsPerPageOptions={[rowsPerPage]}
        component="div"
        count={data.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
      />
    </TableContainer>
  );
  
}

export default adotanteMenu