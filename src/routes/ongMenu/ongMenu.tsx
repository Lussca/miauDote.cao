//imports padrão react
import { useEffect, useState } from 'react';
import React, { useRef } from 'react';
import styles from'./ongMenu.module.css';

//import rotas
import { Link } from 'react-router-dom';

//imports mui
import Alert, { AlertColor } from '@mui/material/Alert';

//componentes
import DogAnimation from '../../Components/dog/dogAnimation';
import { Button } from '@mui/material';
  

function adotanteMenu(this: any)  {

  return (
    <div className={styles.menuArea}>
        <DogAnimation/>
        <div className={styles.animationLogin}>
            <span className={styles.firstText}>Olá, </span>
            <span className={styles.slide}>
                <span className={styles.secondText}>ONG acaba de entrar no sistema!</span>
            </span>
        </div>
        <Button id="signIn" variant="contained" color="error"> SAIR </Button>
    </div>
  );
  
}

export default adotanteMenu