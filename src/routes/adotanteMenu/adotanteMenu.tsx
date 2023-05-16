//imports padrão react
import { useEffect, useState } from 'react';
import React, { useRef } from 'react';
import styles from'./adotanteMenu.module.css';

//import rotas
import { Link } from 'react-router-dom';

//imports mui
import DogAnimation from '../../dog/dogAnimation';

sessionStorage.setItem("isLoggedIn", "true");

var isLoggedIn = sessionStorage.getItem("isLoggedIn");
console.log(isLoggedIn)

if (isLoggedIn === "true") {
  // O usuário está logado, permita o acesso à página
  console.log("caiu aqui")
} else {
  // O usuário não está logado, redirecione para a página de login ou exiba uma mensagem de erro
  console.log("caiu aqui 2 ")
}

function adotanteMenu(this: any)  {

    return (
        <div className={styles.menuArea}>
            <DogAnimation/>
            <div className={styles.animationLogin}>
                <span className={styles.firstText}>Olá, </span>
                <span className={styles.slide}>
                    <span className={styles.secondText}>Você acaba de entrar no sistema!</span>
                </span>
            </div>
        </div>
    );
  
}

export default adotanteMenu