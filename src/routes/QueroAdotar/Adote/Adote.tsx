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

    const initialFilters = {
        especie: '',
        pelagem: '',
        sexo: '',
        caa: '',
        cah: '',
        idade: '',
        porte: ''
      };

    const [filters, setFilters] = useState(initialFilters);

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

    const handleChangePorte = (event: { target: { value: string; }; }) => {
        setFilters({ ...filters, porte: event.target.value });
        setFilterApplied(true);
    };

    const [filterApplied, setFilterApplied] = useState(false);

    const handleClearFilters = () => {
        setFilters(initialFilters);
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
                                <MenuItem value={'1'}>Canino</MenuItem>
                                <MenuItem value={'2'}>Felino</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl style={{ width: '100%', marginTop: '5%' }}>
                            <InputLabel>Porte</InputLabel>
                            <Select value={filters.porte} onChange={handleChangePorte}>
                                <MenuItem value={'1'}>Pequeno</MenuItem>
                                <MenuItem value={'2'}>Médio</MenuItem>
                                <MenuItem value={'3'}>Grande</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl style={{ width: '100%', marginTop: '5%' }}>
                            <InputLabel>Pelagem</InputLabel>
                            <Select value={filters.pelagem} onChange={handleChangePelagem}>
                                <MenuItem value={'3'}>Longo</MenuItem>
                                <MenuItem value={'2'}>Médio</MenuItem>
                                <MenuItem value={'1'}>Curto</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl style={{ width: '100%', marginTop: '5%' }}>
                            <InputLabel>Sexo</InputLabel>
                            <Select value={filters.sexo} onChange={handleChangeSexo}>
                                <MenuItem value={'1'}>Macho</MenuItem>
                                <MenuItem value={'2'}>Fêmea</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl style={{ width: '100%', marginTop: '5%' }}>
                            <InputLabel>Convivência entre animais</InputLabel>
                            <Select value={filters.caa} onChange={handleChangeCaa}>
                                <MenuItem value={'1'}>manso</MenuItem>
                                <MenuItem value={'2'}>agressivo</MenuItem>
                                <MenuItem value={'3'}>agitado</MenuItem>
                                <MenuItem value={'4'}>amigável</MenuItem>
                                <MenuItem value={'5'}>amedrontado</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl style={{ width: '100%', marginTop: '5%' }}>
                            <InputLabel>Convivência com humanos</InputLabel>
                            <Select value={filters.cah} onChange={handleChangeCah}>
                                <MenuItem value={'1'}>manso</MenuItem>
                                <MenuItem value={'2'}>agressivo</MenuItem>
                                <MenuItem value={'3'}>agitado</MenuItem>
                                <MenuItem value={'4'}>amigável</MenuItem>
                                <MenuItem value={'5'}>amedrontado</MenuItem>
                            </Select>
                        </FormControl>
                        <TextField value={filters.idade} onChange={handleChangeIdade} id="idade" label="Idade" variant="outlined" style={{ width: '100%', marginTop: '5%' }}/>

                        <div className={styles.buttons}>
                            <Button variant="contained" color="error" onClick={handleClearFilters}>Remover</Button>
                        </div>
                    </div>
                </Grid>
                <Grid item xs={10} style={{ display: 'flex', flexWrap: 'wrap', columnGap: '21px' }}>
                    <Animals
                    filterApplied={filterApplied}
                    filters={filters}
                    ></Animals>
                </Grid>
            </Grid>
        </div>
    );
  
}

export default Adote