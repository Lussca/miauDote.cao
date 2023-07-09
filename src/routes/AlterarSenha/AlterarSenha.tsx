//imports padrão react
import styles from'./AlterarSenha.module.css';
import { useEffect, useState } from 'react';
import React, { useRef } from 'react';
import axios from 'axios';

import { Alert, AlertColor, Button, TextField } from '@mui/material';

function AlterarSenha(this: any)  {

    const [email, setEmail] = useState('');
    const [severity, setSeverity] = useState('error');
    const [msg, setMsg] = useState('');
    const [showAlert, setShowAlert] = useState(false);

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

    const handleChangeEmail = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setEmail(event.target.value);
    };

    const handleButtonClick = () => {

        const data = {
            email: email
        };
    
        const jsonData = JSON.stringify(data);
    
        axios
            .post('http://localhost:8080/MiauDoteCao/ChangePasswordServlet', jsonData)
            .then(response => {
                setShowAlert(true);
                setSeverity('success');
                setMsg('Email enviado com sucesso!');
            })
            .catch(error => {
                console.error("Erro: " + error);
                setShowAlert(true);
                setSeverity('error');
                setMsg('Erro ao enciar o email!');
            });
    };

    return (
        <div className={styles.Area}>
            <div className={styles.alertArea}>
                {showAlert && <Alert variant="filled" severity={alertSeverity}  onClose={() => {setShowAlert(false)}}>
                {msg}
                </Alert> }
            </div>
            <h2>Alterar senha</h2>
            <p>Preencha e você receberá um email com o código de verificação.</p>
            <TextField
            id="email"
            label="Email"
            margin="normal"
            variant="filled"
            value={email}
            onChange={handleChangeEmail}
            />
            <Button variant="contained" color="success" onClick={handleButtonClick} style={{marginBottom: '5%'}}>
                ENVIAR
            </Button>
        </div>
    );
}

export default AlterarSenha