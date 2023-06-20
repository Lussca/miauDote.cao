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
import Button from '@mui/material/Button';

function Sidebar(this: any)  {

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

    function handleClickFilters() {

    }

    function handleClickRemoveFilters() {

    }

    return (
        <div className={styles.sidebar}>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Espécie</InputLabel>
                <Select value={especie} onChange={handleChangeEspecie}>
                    <MenuItem value={'Canino'}>Canino</MenuItem>
                    <MenuItem value={'Felino'}>Felino</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Pelagem</InputLabel>
                <Select value={pelagem} onChange={handleChangePelagem}>
                    <MenuItem value={'Longo'}>Longo</MenuItem>
                    <MenuItem value={'Médio'}>Médio</MenuItem>
                    <MenuItem value={'Curto'}>Curto</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Sexo</InputLabel>
                <Select value={sexo} onChange={handleChangeSexo}>
                    <MenuItem value={'Macho'}>Macho</MenuItem>
                    <MenuItem value={'Fêmea'}>Fêmea</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Convivência animal para animal</InputLabel>
                <Select value={caa} onChange={handleChangeCaa}>
                    <MenuItem value={'manso'}>manso</MenuItem>
                    <MenuItem value={'agressivo'}>agressivo</MenuItem>
                    <MenuItem value={'agitado'}>agitado</MenuItem>
                    <MenuItem value={'amigável'}>amigável</MenuItem>
                    <MenuItem value={'amedrontado'}>amedrontado</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Convivência animal e humano</InputLabel>
                <Select value={cah} onChange={handleChangeCah}>
                    <MenuItem value={'manso'}>manso</MenuItem>
                    <MenuItem value={'agressivo'}>agressivo</MenuItem>
                    <MenuItem value={'agitado'}>agitado</MenuItem>
                    <MenuItem value={'amigável'}>amigável</MenuItem>
                    <MenuItem value={'amedrontado'}>amedrontado</MenuItem>
                </Select>
            </FormControl>
            <TextField value={idade} onChange={handleChangeIdade} id="idade" label="Idade" variant="outlined" style={{ width: '100%', marginTop: '5%' }}/>

            <div className={styles.buttons}>
                <Button style={{ marginRight: '1%'}} variant="contained" color="error" onChange={handleClickRemoveFilters}>Remover</Button>
                <Button variant="contained" color="success" onChange={handleClickFilters}>Aplicar</Button>
            </div>
        </div>
    );
  
}

export default Sidebar