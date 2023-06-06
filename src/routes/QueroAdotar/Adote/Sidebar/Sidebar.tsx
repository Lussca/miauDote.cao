//imports padrão react
import React, { useEffect, useState } from 'react';
import styles from'./Sidebar.module.css';
import axios from 'axios';

//imports MUI
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import TextField from '@mui/material/TextField';

interface Estado {
    id: string;
    nome: string;
}

interface Cidade {
    id: string;
    nome: string;
}

interface ONGs {
    nome: string;
}

function Sidebar(this: any)  {

    const [ongs, setOngs] = useState<ONGs[]>([]);

    //Requisicao com fetch para a servlet responsável
    function sendRequest() {
        fetch('http://localhost:8080/MiauDoteCao/GetOngNameServlet')
          .then(response => response.json())
          .then(data => {
            setOngs(data); // Armazena a lista de menuItem no estado
          })
          .catch(error => {
            console.error("Error:", error);
          });
      }

    return (
        <div className={styles.sidebar}>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Ongs</InputLabel>
                <Select value={ongs}>
                    <MenuItem value="">
                        <em>Selecione uma ONG</em>
                    </MenuItem>
                    {ongs.map((ongs) => (
                        <MenuItem key={ongs.nome} value={ongs.nome}>{ongs.nome}</MenuItem>
                    ))}
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Espécie</InputLabel>
                <Select>
                    <MenuItem value={1}>Canino</MenuItem>
                    <MenuItem value={2}>Felino</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Pelagem</InputLabel>
                <Select>
                    <MenuItem value={1}>Longo</MenuItem>
                    <MenuItem value={2}>Médio</MenuItem>
                    <MenuItem value={2}>Curto</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Sexo</InputLabel>
                <Select>
                    <MenuItem value={1}>Macho</MenuItem>
                    <MenuItem value={2}>Fêmea</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Convivência animal para animal</InputLabel>
                <Select>
                    <MenuItem value={1}>manso</MenuItem>
                    <MenuItem value={2}>agressivo</MenuItem>
                    <MenuItem value={2}>agitado</MenuItem>
                    <MenuItem value={2}>amigável</MenuItem>
                    <MenuItem value={2}>amedrontado</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Convivência animal e humano</InputLabel>
                <Select>
                    <MenuItem value={1}>manso</MenuItem>
                    <MenuItem value={2}>agressivo</MenuItem>
                    <MenuItem value={2}>agitado</MenuItem>
                    <MenuItem value={2}>amigável</MenuItem>
                    <MenuItem value={2}>amedrontado</MenuItem>
                </Select>
            </FormControl>
            <TextField id="outlined-basic" label="Idade" variant="outlined" style={{ width: '100%', marginTop: '5%' }}/>
        </div>
    );
  
}

export default Sidebar