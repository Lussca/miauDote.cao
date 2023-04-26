import { useState } from 'react';
import React, { useRef } from 'react';
import styles from'./Registro.module.css';

import Button from '@mui/material/Button';
import { TextField } from '@mui/material';
import Checkbox from '@mui/material/Checkbox';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import FormControlLabel from '@mui/material/FormControlLabel';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';


function Registro(this: any)  {

  return (
    <div className={styles.cadastroArea}>
      <div className={styles.title}>
        <img src="./imgs/adote_Cao_Logo.png" className={styles.logo}></img>
        <h1>Cadastro MiauDote.Cão</h1>
      </div>

      <div className={styles.forms}>
        <div className={styles.subTitle}>
          <h3>Dados de Acesso</h3>
        </div>
        <div className={styles.campos}>
          <TextField
            required
            id="login"
            name='login' 
            label="Email" 
          />
        </div>
        <div className={styles.campos}>
          <TextField
            required
            id="password"
            name='password'
            label="Password"
            type="password"
          />
        </div>
        <div className={styles.campos}>
          <FormControlLabel
            value="isOng"
            control={<Checkbox />}
            label="Sou dono de uma ONG"
            labelPlacement="end"
          />
        </div>
      </div>

      <div className={styles.subTitle}>
        <h3>Dados Pessoais</h3>
      </div>
      <div className={styles.formInputsPessoais}>
        <div className={styles.formDiv}>
          <div className={styles.campos}>
            <TextField 
              required
              id="name"
              name='name'
              label="Nome Completo"
            />
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="cpf"
              name='cpf'
              label="CPF"
            />
          </div>
          <div className={styles.campos}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker/>
            </LocalizationProvider>
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="CEP"
              name='CEP'
              label="CEP"
            />
          </div>
        </div>
        <div className={styles.formDiv}>
          <div className={styles.campos}>
            <TextField
              required
              id="Estado"
              name='Estado'
              label="Estado"
            />
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="Cidade"
              name='Cidade'
              label="Cidade"
            />
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="Bairro"
              name='Bairro'
              label="Bairro"
            />
          </div>
        </div>
      </div>
      <div className={styles.btnDiv}>
        <Button variant="contained" color="success">
          CADASTRAR
        </Button>
      </div>
    </div>
  );
}

export default Registro