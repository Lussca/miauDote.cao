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

function Sidebar(this: any)  {

    const [ongs, setOngs] = useState([]); 
    const [selectedValue, setSelectedValue] = useState('');
    const [especie, setEspecie] = useState('');
    const [pelagem, setPelagem] = useState('');
    const [sexo, setSexo] = useState('');
    const [caa, setCaa] = useState('');
    const [cah, setCah] = useState('');
    const [idade, setIdade] = useState('');

    const handleChangeEspecie = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setEspecie(event.target.value);
    };

    const handleChangePelagem = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setPelagem(event.target.value);
    };

    const handleChangeSexo = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setSexo(event.target.value);
    };

    const handleChangeCaa = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setCaa(event.target.value);
    };

    const handleChangeCah = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setCah(event.target.value);
    };

    const handleChangeIdade = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setIdade(event.target.value);
    };

    const handleSelectChange = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setSelectedValue(event.target.value);
      };

    useEffect(() => {
        sendRequest();
    }, []);

    function sendRequest() {
        axios
          .get('http://localhost:8080/MiauDoteCao/GetOngNameServlet')
          .then(response => {
            setOngs(response.data.names);
          })
          .catch(error => {
            console.error('Erro:', error);
          });
    }

    return (
        <div className={styles.sidebar}>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Ongs</InputLabel>
                <Select value={selectedValue} onChange={handleSelectChange}>
                    {(ongs ?? []).length > 0 ? (
                        ongs.map(ong => (
                        <MenuItem key={ong} value={ong}>
                            {ong}
                        </MenuItem>
                        ))
                    ) : (
                        <MenuItem value="">Nenhuma ONG encontrada</MenuItem>
                    )}
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Espécie</InputLabel>
                <Select value={especie} onChange={handleChangeEspecie}>
                    <MenuItem value={1}>Canino</MenuItem>
                    <MenuItem value={2}>Felino</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Pelagem</InputLabel>
                <Select value={pelagem} onChange={handleChangePelagem}>
                    <MenuItem value={1}>Longo</MenuItem>
                    <MenuItem value={2}>Médio</MenuItem>
                    <MenuItem value={3}>Curto</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Sexo</InputLabel>
                <Select value={sexo} onChange={handleChangeSexo}>
                    <MenuItem value={1}>Macho</MenuItem>
                    <MenuItem value={2}>Fêmea</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Convivência animal para animal</InputLabel>
                <Select value={caa} onChange={handleChangeCaa}>
                    <MenuItem value={1}>manso</MenuItem>
                    <MenuItem value={2}>agressivo</MenuItem>
                    <MenuItem value={3}>agitado</MenuItem>
                    <MenuItem value={4}>amigável</MenuItem>
                    <MenuItem value={5}>amedrontado</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Convivência animal e humano</InputLabel>
                <Select value={cah} onChange={handleChangeCah}>
                    <MenuItem value={1}>manso</MenuItem>
                    <MenuItem value={2}>agressivo</MenuItem>
                    <MenuItem value={3}>agitado</MenuItem>
                    <MenuItem value={4}>amigável</MenuItem>
                    <MenuItem value={5}>amedrontado</MenuItem>
                </Select>
            </FormControl>
            <TextField value={idade} onChange={handleChangeIdade} id="idade" label="Idade" variant="outlined" style={{ width: '100%', marginTop: '5%' }}/>
        </div>
    );
  
}

export default Sidebar