//imports padrão react
import React, { useRef } from 'react';
import { useEffect, useState } from 'react';
import styles from'./adotanteMenu.module.css';

//import rotas
import { Link } from 'react-router-dom';

//imports mui
import Alert, { AlertColor } from '@mui/material/Alert';

//componentes
import DogAnimation from '../../dog/dogAnimation';
import { Navbar } from '../../Navbar/Navbar';

// validação para verificar se o usuário está logado
sessionStorage.setItem("isLoggedIn", "true");

var isLoggedIn = sessionStorage.getItem("isLoggedIn");

if (isLoggedIn === "true") {
} else {
    window.location.href = "/error";
}

function adotanteMenu(this: any)  {

    return (
        <div className={styles.menuArea}>
            <Navbar></Navbar>
        </div>
    );
  
}

export default adotanteMenu