//imports padrão react
import React, { ChangeEvent, useState, useEffect } from 'react';
import styles from'./Registro.module.css';
import * as moment from "moment";

//imports masks
import formatCpf from '../../Masks/MaskCpf';
import formatCep from '../../Masks/MaskCep';

//import de rotas
import { Link } from 'react-router-dom';

//imports mui
import Button from '@mui/material/Button';
import { TextField } from '@mui/material';
import Checkbox from '@mui/material/Checkbox';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import FormControlLabel from '@mui/material/FormControlLabel';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';

import dayjs from 'dayjs';
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

  const [login, setlogin] = useState('');
  const [password, setPassword] = useState('');
  const [isOng, setIsOng] = useState(false);
  const [name, setName] = useState('');
  const [cpf, setCpf] = useState('');
  const [birth, setBirth] = useState('');
  const [cep, setCep] = useState('');
  const [ongName, setOngName] = useState('');

  const [state, setState] = useState('');
  const [city, setCity] = useState('');
  const [neighborhood, setNeighborhood] = useState('');
  const [street, setstreet] = useState('');
  const [number, setNumber] = useState('');

  //variavel da api do enderço
  const [address, setAddress] = useState<Endereco | null>(null);
  
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

  const handleChangeBirth = (event:any) => {
    let data = event.$d;
    setBirth(data);
  };

  const handleChangeCep = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setCep(event.target.value);
  };

  const handleChangeState = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setState(event.target.value);
  };

  const handleChangeCity = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setCity(event.target.value);
  };

  const handleChangeNeighborhood = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setNeighborhood(event.target.value);
  };

  const handleChangeOngName = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setOngName(event.target.value);
  };
  
  const handleChangeStreet = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setstreet(event.target.value);
  };
  
  const handleChangeNumber = (event: { target: { value: React.SetStateAction<string>; }; }) => {
    setNumber(event.target.value);
  };

  const handleCheck = (event: { target: { checked: boolean | ((prevState: boolean) => boolean); }; }) => {
    setIsOng(event.target.checked);
  }

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
      };
      fetchData();
    } else {
      setAddress(null);
    }
  }, [cep]);

  function createAccount() {

      let httpRequest = new XMLHttpRequest();
      const URL_SERVLET_REGISTER = "http://localhost:8080/adoteCaoProjeto/RegisterServlet";
      httpRequest.onreadystatechange=function(){
        if(httpRequest.readyState === XMLHttpRequest.DONE){
          if(httpRequest.status === 200){
            window.alert("Conta Criada com sucesso!");
            window.location.href = "http://127.0.0.1:5500/login.html";
          }else if(httpRequest.status === 400 || httpRequest.status === 422 || httpRequest.status === 409 || httpRequest.status === 501){
            window.alert(httpRequest.responseText);
          }
        }
      }
      httpRequest.open("POST", URL_SERVLET_REGISTER, true);
      httpRequest.setRequestHeader('Content-type','application/x-www-form-urlencoded');
      
      if(isOng == true){

      console.log(login, password, name, cpf, birth, state, neighborhood, cep, isOng, ongName);
      httpRequest.send("login="+login+"&password="+password+"&name="+name+"&cpf="+cpf+"&birth="+birth+"&isOng="+isOng+"&ongName="+ongName+
      "&state="+state+"&city="+city+"&neighborhood="+neighborhood+"&cep="+cep+"&street="+street+"&number="+number);
      
      }else if (isOng == false){
      console.log(login, password, name, cpf, birth, state, neighborhood, cep, isOng);
      httpRequest.send("login="+login+"&password="+password+"&name="+name+"&cpf="+cpf+"&birth="+birth+"&isOng="+isOng+
      "&state="+state+"&city="+city+"&neighborhood="+neighborhood+"&cep="+cep+"&street="+street+"&number="+number);
      }      
  }

  return (
    <div className={styles.cadastroArea}>
      {/* titel */}
      <div className={styles.title}>
        <img src="./imgs/adote_Cao_Logo.png" className={styles.logo}></img>
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
              <DatePicker  onChange={handleChangeBirth}/>
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
              required
              id="Estado"
              name='Estado'
              label="Estado"
              value={address.uf}
              onChange={handleChangeState}
            />
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="Cidade"
              name='Cidade'
              label="Cidade"
              value={address.localidade}
              onChange={handleChangeCity}
            />
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="Bairro"
              name='Bairro'
              label="Bairro"
              value={address.bairro}
              onChange={handleChangeNeighborhood}
            />
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="Rua"
              name='Rua'
              label="Rua"
              value={address.logradouro}
              onChange={handleChangeStreet}
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