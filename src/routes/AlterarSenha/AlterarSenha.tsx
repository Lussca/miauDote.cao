//imports padrão react
import styles from'./AlterarSenha.module.css';
import { useEffect, useState } from 'react';
import React, { useRef } from 'react';
import axios from 'axios';

import { Alert, AlertColor, Button, CircularProgress, TextField } from '@mui/material';

function AlterarSenha(this: any)  {

    const [email, setEmail] = useState('');
    const [codigo, setCodigo] = useState('');
    const [senha, setSenha] = useState('');

    const [showEmailFields, setShowEmailFields] = useState(true);
    const [showVerificationCodeField, setShowVerificationCodeField] = useState(false);

    const [msg, setMsg] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [severity, setSeverity] = useState('error');
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

    const handleChangeCodigo = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setCodigo(event.target.value);
    };

    const handleChangeSenha = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setSenha(event.target.value);
    };

    const handleButtonClick = () => {

        setIsLoading(true);

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

                setIsLoading(false);

                setShowEmailFields(false);
                setShowVerificationCodeField(true);

                window.sessionStorage.setItem("email", email);
            })
            .catch(error => {
                console.error("Erro: " + error);

                setIsLoading(false);

                setShowAlert(true);
                setSeverity('error');
                setMsg('Erro ao enviar o email!');
            });
    };

    const handleButtonUpdateSenha = () => {

        setIsLoading(true);

        const emailSession = sessionStorage.getItem('email')

        const data = {
            validationCode: codigo,
            newPassword: senha,
            email: emailSession
        };
    
        const Data = JSON.stringify(data);
    
        axios
            .post('http://localhost:8080/MiauDoteCao/ValidateChangePasswordCode', Data)
            .then(response => {
                setShowAlert(true);
                setSeverity('success');
                setMsg('Senha alterada com sucesso!');

                setIsLoading(false);

                setShowEmailFields(false);
                setShowVerificationCodeField(true);

                sessionStorage.removeItem("email");
                window.location.href = "/login";
            })
            .catch(error => {
                console.error("Erro: " + error);

                setIsLoading(false);

                setShowAlert(true);
                setSeverity('error');
                setMsg('Erro ao alterar a senha!');
            });
    };

    return (
        <div className={styles.Area}>
            <div className={styles.alertArea}>
                {showAlert && <Alert variant="filled" severity={alertSeverity}  onClose={() => {setShowAlert(false)}}>
                {msg}
                </Alert> }
            </div>

            {isLoading && <div className={styles.progress}>
                <CircularProgress />
            </div>}

            {showEmailFields && (
                <>
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
                </>
            )}
            {showVerificationCodeField && (
                <>
                    <h2>Inserir código de verificação</h2>
                    <p>Insira o código de verificação recebido por email e uma senha de acesso nova.</p>
                    <TextField
                        id="codigo"
                        label="Código de Verificação"
                        margin="normal"
                        variant="filled"
                        value={codigo}
                        onChange={handleChangeCodigo}
                    />
                    <TextField
                        id="senhaNova"
                        label="Senha nova"
                        margin="normal"
                        variant="filled"
                        value={senha}
                        onChange={handleChangeSenha}
                    />
                    <Button variant="contained" color="success" onClick={handleButtonUpdateSenha} style={{marginBottom: '5%'}}>
                        VERIFICAR
                    </Button>
                </>
            )}
        </div>
    );
}

export default AlterarSenha