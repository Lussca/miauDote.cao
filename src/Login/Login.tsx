import './Login.css';
import { useState } from 'react';
import React, { useRef } from 'react';

// imports MUI
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Link from '@mui/material/Link';

function Login(this: any)  {

  const email = useRef(null);
  const password = useRef(null);

  const handleClick = () => {

    let httpRequest = new XMLHttpRequest();
    const URL_SERVLET_LOGIN = "http://localhost:8080/adoteCaoProjeto/LoginServlet";
    httpRequest.onreadystatechange=function(){
        if(httpRequest.readyState === XMLHttpRequest.DONE){
            if(httpRequest.status === 200){
                console.log("Request realizado");
                let sessionData = JSON.parse(httpRequest.responseText);
                window.sessionStorage.setItem("isOng", sessionData.isOng);
                window.sessionStorage.setItem("isLogged", sessionData.isLogged);
                window.sessionStorage.setItem("jwt", sessionData.jwt);
                console.log(sessionData);

                if(sessionData.isOng){
                    window.location.href = "http://127.0.0.1:5500/ongMenu.html";
                }else{
                    window.location.href = "http://127.0.0.1:5500/adotanteMenu.html";
                }
            }
        }
    }
    httpRequest.open("POST", URL_SERVLET_LOGIN, true);
    httpRequest.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    httpRequest.send("login="+email+"&password="+password);
  }

  return (
    <div className="loginArea">
      <div className='intro'>
        <img src="./imgs/adote_Cao_Logo.png" className='logo'></img>
        <h1 className='login'>Login MiauDote.Cão</h1>
      </div>
      
      <div className='loginForm'>

        <TextField
          required
          id="filled-required"
          label="Email"
          className='id'
          variant="filled"
          margin="normal"
          ref={email}
        />
        <TextField
        id="filled-password-input"
        label="Password"
        type="password"
        className='senha'
        autoComplete="current-password"
        margin="normal"
        variant="filled"
        ref={password}
        />

        <div className='Register'>
          <p className='Link'>Ainda não tem uma conta? 
            <Link href="http:127.0.0.1:5500/cadastro.html" underline="hover">
              {'Clique aqui!'}
            </Link>
          </p>
        </div>
        
        <Button id="signIn" variant="contained" color="success" onClick={handleClick}> Entrar </Button>
      </div>
    </div>
  );
}

export default Login