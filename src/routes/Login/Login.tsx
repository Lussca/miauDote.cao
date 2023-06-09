//imports padrão react
import { useEffect, useState } from 'react';
import React, { useRef } from 'react';
import styles from'./Login.module.css';

//import rotas
import { Link, useNavigate } from 'react-router-dom';
import { userService } from '../../service/userService';

//imports mui
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Alert, { AlertColor } from '@mui/material/Alert';

function Login(this: any)  {

  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');
  const [severity, setSeverity] = useState('error');
  const [msg, setMsg] = useState('');
  const [showAlert, setShowAlert] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    userService.verificationUserLogged(false, navigate);
  },[])

  let alertSeverity: AlertColor | undefined;

  switch (severity) {
    case 'error':
      alertSeverity = 'error';
      break;
    case 'warning':
      alertSeverity = 'warning';
      break;
    case 'info':
      alertSeverity = 'info';
      break;
    case 'success':
      alertSeverity = 'success';
      break;
    default:
      alertSeverity = undefined;
  }

  const handleChangeUser = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setUser(event.target.value);
  };
  
  const handleChangePassword = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setPassword(event.target.value);
  };

  const handleClick = () => {

    let httpRequest = new XMLHttpRequest();
    const URL_SERVLET_LOGIN = "http://localhost:8080/MiauDoteCao/LoginServlet";
    httpRequest.onreadystatechange=function(){
        if(httpRequest.readyState === XMLHttpRequest.DONE){
          let sessionData = JSON.parse(httpRequest.responseText);

          if(sessionData == 15){
            setShowAlert(true);
            setSeverity('warning');
            setMsg('Atenção! email ou senha inválidos.');
          }

          if(httpRequest.status === 200){
            window.sessionStorage.setItem("userId", sessionData.userId);
            window.sessionStorage.setItem("isOng", sessionData.isOng);
            window.sessionStorage.setItem("isLogged", sessionData.isLogged);
            window.sessionStorage.setItem("jwt", sessionData.jwt);

            if(sessionData.isOng){
                window.location.href = "/ongMenu";
            }else{
                window.location.href = "/adotanteMenu";
            }
          }
        }
    }
    httpRequest.open("POST", URL_SERVLET_LOGIN, true);
    httpRequest.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    httpRequest.send("login="+user+"&password="+password);
  }

  return (
    <div className={styles.loginArea}>
      <div className={styles.alertArea}>
        {showAlert && <Alert variant="filled" severity={alertSeverity}  onClose={() => {setShowAlert(false)}}>
          {msg}
        </Alert> }
      </div>
      
      <div className={styles.intro}>
        <img src="./imgs/logos/LogoSemFonte_Azul.svg" className={styles.logo}></img>
        <h1 className={styles.login}>Login MiauDote.Cão</h1>
      </div>
      
      <div className={styles.loginForm}>

        <TextField
          id="user"
          label="Email"
          className={styles.id}
          variant="filled"
          margin="normal"
          value={user} 
          onChange={handleChangeUser}
        />
        <TextField
          id="Password"
          label="Senha"
          type="password"
          className={styles.senha}
          autoComplete="current-password"
          margin="normal"
          variant="filled"
          value={password} 
          onChange={handleChangePassword}
        />

        <div className={styles.Register}>
          <p className={styles.Link}>Ainda não tem uma conta? 
            <Link to="/registrar" className={styles.link}>
              {'Clique aqui!'}
            </Link>
          </p>
          <p className={styles.Link}>Esqueceu a senha? 
            <Link to="/alterarSenha" className={styles.link}>
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