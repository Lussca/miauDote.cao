//imports padrão react
import React, { useEffect, useState } from 'react';
import styles from'./Sidebar.module.css';
import axios from 'axios';

//imports MUI
import MenuItem from '@mui/material/MenuItem';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Autocomplete from '@mui/material/Autocomplete';
import Select, { SelectChangeEvent } from '@mui/material/Select';

interface Estado {
    id: string;
    nome: string;
}

interface Cidade {
    id: string;
    nome: string;
}

const ONGs = [
    { label: 'The Shawshank Redemption', year: 1994 },
];

function Sidebar(this: any)  {

    const [estados, setEstados] = useState<Estado[]>([]);
    const [cidades, setCidades] = useState<Cidade[]>([]);
    const [selectedEstado, setSelectedEstado] = useState<string>('');
    const [selectedCidade, setSelectedCidade] = useState<string>('');
  
    useEffect(() => {
      const fetchEstados = async () => {
        const response = await axios.get(
          'https://servicodados.ibge.gov.br/api/v1/localidades/estados'
        );
        setEstados(response.data);
      };
  
      fetchEstados();
    }, []);
  
    const handleEstadoChange = async (event: { target: { value: any; }; }) => {
      const estadoId = event.target.value;
      setSelectedEstado(estadoId);
  
      if (estadoId) {
        const response = await axios.get(
          `https://servicodados.ibge.gov.br/api/v1/localidades/estados/${estadoId}/municipios`
        );
        setCidades(response.data);
      } else {
        setCidades([]);
      }
  
      setSelectedCidade('');
    };
  
    const handleCidadeChange = (event: { target: { value: any; }; }) => {
      const cidadeId = event.target.value;
      setSelectedCidade(cidadeId);
    };

    return (
        <div className={styles.sidebar}>
            <FormControl style={{ width: '100%' }}>
                <InputLabel>Estado</InputLabel>
                <Select value={selectedEstado} onChange={handleEstadoChange}>
                    <MenuItem value="">
                        <em>Selecione um estado</em>
                    </MenuItem>
                    {estados.map((estado) => (
                        <MenuItem key={estado.id} value={estado.id}>
                        {estado.nome}
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Cidade</InputLabel>
                <Select value={selectedCidade} onChange={handleCidadeChange}>
                    <MenuItem value="">
                        <em>Selecione uma cidade</em>
                    </MenuItem>
                    {cidades.map((cidade) => (
                        <MenuItem key={cidade.id} value={cidade.id}>
                        {cidade.nome}
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '5%' }}>
                <InputLabel>Ongs</InputLabel>
                {/* <Select value={selectedCidade} onChange={handleCidadeChange}>
                    <MenuItem value="">
                        <em>Selecione uma cidade</em>
                    </MenuItem>
                    {cidades.map((cidade) => (
                        <MenuItem key={cidade.id} value={cidade.id}>
                        {cidade.nome}
                        </MenuItem>
                    ))}
                </Select> */}
            </FormControl>
        </div>
    );
  
}

export default Sidebar