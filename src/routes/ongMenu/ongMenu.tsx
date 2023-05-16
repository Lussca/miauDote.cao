//imports padrão react
import { useEffect, useState } from 'react';
import React, { useRef } from 'react';
import styles from'./ongMenu.module.css';

//import rotas
import { Link } from 'react-router-dom';

//imports mui
import Alert, { AlertColor } from '@mui/material/Alert';

//componentes
import DogAnimation from '../../dog/dogAnimation';
import { Button } from '@mui/material';

// validação para verificar se o usuário está logado
sessionStorage.setItem("isLoggedIn", "true");
// sessionStorage.setItem("isLoggedIn", "true");

var isLoggedIn = sessionStorage.getItem("isLoggedIn");

if (isLoggedIn === "true") {
} else {
    window.location.href = "/error";
}

// função para deslogar do sistema e remover as informações armazenadas no session storage
const handleClick = () => {
    // Remover as informações do session storage
    sessionStorage.removeItem("isLoggedIn");

    // Redirecionar para a página de login ou para outra página adequada após o logout
    window.location.href = "pagina_de_login.html";
};
  

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
        <Button id="signIn" variant="contained" color="error" onClick={handleClick}> SAIR </Button>
    </div>
  );
  
}

export default adotanteMenu