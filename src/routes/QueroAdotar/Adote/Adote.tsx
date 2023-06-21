//imports padrão react
import styles from'./Adote.module.css';

//import Componentes
import Animals from './Animals';

//imports MUI
import Grid from '@mui/material/Grid';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { useState } from 'react';


function Adote(this: any)  {

    const [filters, setFilters] = useState({
        especie: '',
        pelagem: '',
        sexo: '',
        caa: '',
        cah: '',
        idade: ''
      });

    const handleChangeEspecie = (event: { target: { value: string; }; }) => {
        setFilters({ ...filters, especie: event.target.value });
        setFilterApplied(true);

    };

    const handleChangePelagem = (event: { target: { value: string; }; }) => {
        setFilters({ ...filters, pelagem: event.target.value });
        setFilterApplied(true);

    };

    const handleChangeSexo = (event: { target: { value: string; }; }) => {
        setFilters({ ...filters, sexo: event.target.value });
        setFilterApplied(true);

    };

    const handleChangeCaa = (event: { target: { value: string; }; }) => {
        setFilters({ ...filters, caa: event.target.value });
        setFilterApplied(true);

    };

    const handleChangeCah = (event: { target: { value: string; }; }) => {
        setFilters({ ...filters, cah: event.target.value });
        setFilterApplied(true);

    };

    const handleChangeIdade = (event: { target: { value: string; }; }) => {
        setFilters({ ...filters, idade: event.target.value });
        setFilterApplied(true);

    };

    const [filterApplied, setFilterApplied] = useState(false);

    const removeFilter = () => {
        setFilterApplied(false);
    };

    return (
        <div className={styles.adoteArea}>
            <Grid container spacing={2} margin={0}>
                <Grid item xs={2} style={{ backgroundColor: "#fff" }}>
                    <div className={styles.sidebar}>
                        <FormControl style={{ width: '100%', marginTop: '5%' }}>
                            <InputLabel>Espécie</InputLabel>
                            <Select value={filters.especie} onChange={handleChangeEspecie}>
                                <MenuItem value={'Canino'}>Canino</MenuItem>
                                <MenuItem value={'Felino'}>Felino</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl style={{ width: '100%', marginTop: '5%' }}>
                            <InputLabel>Pelagem</InputLabel>
                            <Select value={filters.pelagem} onChange={handleChangePelagem}>
                                <MenuItem value={'Longo'}>Longo</MenuItem>
                                <MenuItem value={'Médio'}>Médio</MenuItem>
                                <MenuItem value={'Curto'}>Curto</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl style={{ width: '100%', marginTop: '5%' }}>
                            <InputLabel>Sexo</InputLabel>
                            <Select value={filters.sexo} onChange={handleChangeSexo}>
                                <MenuItem value={'Macho'}>Macho</MenuItem>
                                <MenuItem value={'Fêmea'}>Fêmea</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl style={{ width: '100%', marginTop: '5%' }}>
                            <InputLabel>Convivência entre animais</InputLabel>
                            <Select value={filters.caa} onChange={handleChangeCaa}>
                                <MenuItem value={'manso'}>manso</MenuItem>
                                <MenuItem value={'agressivo'}>agressivo</MenuItem>
                                <MenuItem value={'agitado'}>agitado</MenuItem>
                                <MenuItem value={'amigável'}>amigável</MenuItem>
                                <MenuItem value={'amedrontado'}>amedrontado</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl style={{ width: '100%', marginTop: '5%' }}>
                            <InputLabel>Convivência com humanos</InputLabel>
                            <Select value={filters.cah} onChange={handleChangeCah}>
                                <MenuItem value={'manso'}>manso</MenuItem>
                                <MenuItem value={'agressivo'}>agressivo</MenuItem>
                                <MenuItem value={'agitado'}>agitado</MenuItem>
                                <MenuItem value={'amigável'}>amigável</MenuItem>
                                <MenuItem value={'amedrontado'}>amedrontado</MenuItem>
                            </Select>
                        </FormControl>
                        <TextField value={filters.idade} onChange={handleChangeIdade} id="idade" label="Idade" variant="outlined" style={{ width: '100%', marginTop: '5%' }}/>

                        <div className={styles.buttons}>
                            <Button variant="contained" color="error" onChange={removeFilter}>Remover</Button>
                        </div>
                    </div>
                </Grid>
                <Grid item xs={10} style={{ display: 'flex', flexWrap: 'wrap', columnGap: '21px' }}>
                    <Animals
                    filterApplied={filterApplied}
                    // applyFilter={applyFilter}
                    removeFilter={removeFilter}
                    filters={filters}
                    ></Animals>
                </Grid>
            </Grid>
        </div>
    );
  
}

export default Adote