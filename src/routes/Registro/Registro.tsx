//imports padrão react
import React, { ChangeEvent, useState, useEffect } from 'react';
import styles from'./Registro.module.css';

//imports masks
import formatCpf from '../../Masks/MaskCpf';
import formatCep from '../../Masks/MaskCep';

//import de rotas
import { Link } from 'react-router-dom';

//imports mui
import Button from '@mui/material/Button';
import { TextField } from '@mui/material';
import Alert, { AlertColor } from '@mui/material/Alert';
import Checkbox from '@mui/material/Checkbox';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import FormControlLabel from '@mui/material/FormControlLabel';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import 'dayjs/locale/pt-br';

// import Api cep (axios)
import axios from 'axios';

//Api CEP consulta
async function fetchAddress(cep: any) {
  const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);
  return response.data;
}

// interface com as variaveis da localidade
interface Endereco {
  uf: string;
  localidade: string;
  bairro: string;
  logradouro: string;
}

function Registro(this: any)  {

  //======================================= Dados =====================================//

  //acesso usuário
  const [login, setlogin] = useState('');
  const [password, setPassword] = useState('');

  //dados da ONG
  const [isOng, setIsOng] = useState(false);
  const [ongName, setOngName] = useState('');

  //dados do usuário
  const [name, setName] = useState('');
  const [cpf, setCpf] = useState('');
  const [birth, setBirth] = useState(null);
  const [birthFormated, setSelectedDateString] = useState('');

  //variavel da api do enderço
  const [address, setAddress] = useState<Endereco | null>(null);
  
  //dados do local do usuário
  const [cep, setCep] = useState('');
  const [state, setState] = useState('');
  const [city, setCity] = useState('');
  const [neighborhood, setNeighborhood] = useState('');
  const [street, setStreet] = useState('');
  const [number, setNumber] = useState('');

  //====================================================================================//
  
  //======================================= handleChangeValues =====================================//
  
  const handleChangeLogin = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setlogin(event.target.value);
  };

  const handleChangePassword = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setPassword(event.target.value);
  };

  const handleChangeName = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setName(event.target.value);
  };

  const handleChangeCpf = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setCpf(event.target.value);
  };
  
  const handleChangeOngName = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setOngName(event.target.value);
  };
  
  const handleChangeNumber = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setNumber(event.target.value);
  };
  
  //================================================================================================//

  //formata e monta a data
  const handleDateChange = (date:any) => {
    setBirth(date);
    if (date !== null) {
      const formattedDate = new Date(date).toLocaleDateString('pt-BR');
      setSelectedDateString(formattedDate);
    } else {
      setSelectedDateString('');
    }
  };

  //add/remove display none e limpa os campos dos dados da ONG 
  const toggleDisplay = () => {
    const element = document.querySelector('#formInputsOng') as HTMLElement;
    if(element){
      element.style.display = isOng ? 'none' : 'flex';
      const inputElement = document.querySelector('#idOng') as HTMLInputElement;
      inputElement.value = '';
      setIsOng(!isOng);
    }
  };

  //retira mascara do campo CEP
  const cepValue = cep.replace(/\D/g, '');

  //Faz o resuqest da consulta e preenche os campos
  useEffect(() => {
    if (cepValue.length === 8) {
      const fetchData = async () => {
        const addressData = await fetchAddress(cepValue);
        setAddress(addressData);

        if(addressData){
          setState(addressData.uf);
          setCity(addressData.localidade);
          setNeighborhood(addressData.bairro);
          setStreet(addressData.logradouro);
        }
      };
      fetchData();
    } else{
      setAddress(null);
    }

  }, [cep]);

  // alertas config
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

  // criacao de contas
  function createAccount() {

    let httpRequest = new XMLHttpRequest();
    const URL_SERVLET_REGISTER = "http://localhost:8080/adoteCaoProjeto/RegisterServlet";

    httpRequest.onreadystatechange=function(){
      if(httpRequest.readyState === XMLHttpRequest.DONE){

        let sessionData = JSON.parse(httpRequest.responseText);
        console.log(sessionData)
        
        // alertas de cadastro
        if(sessionData == 13){
          setShowAlert(true);
          setSeverity('warning');
          setMsg('Atenção! preencha todos os campos.');
        } else if(sessionData == 1){
          setShowAlert(true);
          setSeverity('error');
          setMsg('Erro! CIDADE inválida.');
        } else if(sessionData == 3){
          setShowAlert(true);
          setSeverity('error');
          setMsg('Erro! CEP inválido.');
        } else if(sessionData == 4){
          setShowAlert(true);
          setSeverity('error');
          setMsg('Erro! LOGIN inválido.');
        } else if(sessionData == 5){
          setShowAlert(true);
          setSeverity('error');
          setMsg('Erro! SENHA inválida.');
        } else if(sessionData == 7){
          setShowAlert(true);
          setSeverity('error');
          setMsg('Erro! CPF inválido.');
        } else if(sessionData == 8){
          setShowAlert(true);
          setSeverity('error');
          setMsg('Erro! DATA inválido.');
        } else if(sessionData == 9){
          setShowAlert(true);
          setSeverity('error');
          setMsg('Erro! NOME DA ONG inválido.');
        } else if(sessionData == 10){
          setShowAlert(true);
          setSeverity('warning');
          setMsg('Atenção! este LOGIN já existe.');
        } else if(sessionData == 11){
          setShowAlert(true);
          setSeverity('warning');
          setMsg('Atenção! este CPF já está sendo usado.');
        } else if(sessionData == 12){
          setShowAlert(true);
          setSeverity('warning');
          setMsg('Atenção! este NOME DE ONG já está sendo usado.');
        } else if(sessionData == 14 || sessionData == 16){
          setShowAlert(true);
          setSeverity('error');
          setMsg('Erro! ocorreu um erro durante a criação da ong, tente novamente mais tarde.');
        }

        if(httpRequest.status === 200){

          console.log('chegou aqui');
          setShowAlert(true);
          setSeverity('success');
          setMsg('Sucesso! Conta Criada com sucesso!');
          window.location.href = "/";

        } else if(httpRequest.status === 400 || httpRequest.status === 422 || httpRequest.status === 409 || httpRequest.status === 501){

          setShowAlert(true);
          setSeverity('error');
          setMsg('Erro! '+ httpRequest.responseText);

        }
      }
    }

    httpRequest.open("POST", URL_SERVLET_REGISTER, true);
    httpRequest.setRequestHeader('Content-type','application/x-www-form-urlencoded');
      
    if(isOng == true){

      httpRequest.send(
        "login="+login+
        "&password="+password+
        "&name="+name+
        "&cpf="+cpf+
        "&birth="+birthFormated+
        "&isOng="+isOng+
        "&ongName="+ongName+
        "&state="+state+
        "&city="+city+
        "&neighborhood="+neighborhood+
        "&cep="+cep+
        "&street="+street+
        "&number="+number
      );
    
    } else if (isOng == false){

      httpRequest.send(
        "login="+login+
        "&password="+password+
        "&name="+name+
        "&cpf="+cpf+
        "&birth="+birthFormated+
        "&isOng="+isOng+
        "&state="+state+
        "&city="+city+
        "&neighborhood="+neighborhood+
        "&cep="+cep+
        "&street="+street+
        "&number="+number
      );

    }      
  }

  return (
    <div className={styles.cadastroArea}>
      {/* alert */}
      <div className={styles.alertArea}>
        {showAlert && <Alert variant="filled" severity={alertSeverity}  onClose={() => {setShowAlert(false)}}>
          {msg}
        </Alert> }
      </div>

      {/* titel */}
      <div className={styles.title}>
        <img src="./imgs/LogoSemFonte_Azul.svg" className={styles.logo}></img>
        <h1>Cadastro MiauDote.Cão</h1>
      </div>

      {/* dados de acesso de dados da Ong */}
      <div className={styles.rowOne}>
        <div className={styles.forms}>
          <div className={styles.subTitle}>
            <p>Dados de Acesso</p>
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="login"
              name='login' 
              label="Email"
              value={login} 
              onChange={handleChangeLogin}
            />
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="password"
              name='password'
              label="Password"
              type="password"
              value={password} 
              onChange={handleChangePassword}
            />
          </div>
          <div className={styles.campos}>
            <FormControlLabel
              label="Sou dono de uma ONG"
              labelPlacement="end"
              control={<Checkbox
                checked={isOng}
                onChange={toggleDisplay}
              />}
            />
          </div>
        </div>
        <div className={styles.formInputsOng} id='formInputsOng'>
          <div className={styles.subTitle}>
            <p>Dados da ONG</p>
          </div>
          <div className={styles.campos}>
            <TextField 
              required
              id="idOng"
              name='ongName'
              label="Nome da ONG"
              value={ongName}
              onChange={handleChangeOngName}
            />
          </div>
        </div>
      </div>
      
      {/* Dados pessoais do usuário */}
      <div className={styles.subTitle}>
        <p>Dados Pessoais</p>
      </div>
      <div className={styles.formInputsPessoais}>
        <div className={styles.formDiv}>
          <div className={styles.campos}>
            <TextField 
              required
              id="name"
              name='name'
              label="Nome Completo"
              value={name}
              onChange={handleChangeName}
            />
          </div>
          <div className={styles.campos}>
            <TextField 
              required
              id="cpf"
              name='cpf'
              placeholder="CPF*"
              value={cpf}
              onChange={
                (event) => {
                  setCpf(formatCpf(event.target.value));
                }
              }
            />
          </div>
          <div className={styles.campos}>
            <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="pt-br">
              <DatePicker  onChange={handleDateChange}/>
            </LocalizationProvider>
          </div>

          <div className={styles.campos}>
            <TextField
              required
              id="cep"
              name='cep'
              placeholder="CEP*"
              value={cep}
              onChange={
                (event) => {
                  setCep(formatCep(event.target.value));
                }
              }
            />
          </div>
        </div>

        {address !== null && (
        <div className={styles.formDiv}>
          <div className={styles.campos}>
            <TextField
              id="Estado"
              name='Estado'
              label="Estado"
              value={address.uf}
              disabled
            />
          </div>
          <div className={styles.campos}>
            <TextField
              id="Cidade"
              name='Cidade'
              label="Cidade"
              value={address.localidade}
              disabled
            />
          </div>
          <div className={styles.campos}>
            <TextField
              id="Bairro"
              name='Bairro'
              label="Bairro"
              value={address.bairro}
              disabled
            />
          </div>
          <div className={styles.campos}>
            <TextField
              id="Rua"
              name='Rua'
              label="Rua"
              value={address.logradouro}
              disabled
            />
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="Numero"
              name='Numero'
              label="Numero"
              value={number}
              onChange={handleChangeNumber}
            />
          </div>
        </div>
        )}
      </div>

      {/* botoes */}
      <div className={styles.btnDiv}>
        <Link to="/" className={styles.link}>
          <Button variant="contained">
            VOLTAR
          </Button>
        </Link>
        <Button variant="contained" color="success" onClick={createAccount}>
          CADASTRAR
        </Button>
      </div>

    </div>
  );
}

export default Registro