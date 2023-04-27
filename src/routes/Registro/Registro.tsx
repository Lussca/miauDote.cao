//imports padrão react
import React, { ChangeEvent, useState } from 'react';
import styles from'./Registro.module.css';

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

//TODO: API CEP
//TODO: ABRIR E FECHAR ABA DA ONG
//TODO: CADASTRAR USUÁRIO
//TODO: RESPONSAVEL

function Registro(this: any)  {

  const [login, setlogin] = useState('');
  const [password, setPassword] = useState('');
  const [isOng, setIsOng] = useState(false);
  const [name, setName] = useState('');
  const [cpf, setCpf] = useState('');
  const [birth, setBirth] = useState('');
  const [state, setState] = useState('');
  const [city, setCity] = useState('');
  const [neighborhood, setNeighborhood] = useState('');
  const [cep, setCep] = useState('');
  const [ongName, setOngName] = useState('');

  const handleCheck = (event: { target: { checked: boolean | ((prevState: boolean) => boolean); }; }) => {
    setIsOng(event.target.checked);
  }

  function showOngOptions() {
    let formContainer = document.getElementsByName("formInputsPessoais");

    if (isOng != true) {
      //retira o display none
    }else {
      //coloca o display none
      //limpa os campos
    }
  }

  // function createAccount(event) {

  //     let httpRequest = new XMLHttpRequest();
  //     const URL_SERVLET_REGISTER = "http://localhost:8080/adoteCaoProjeto/RegisterServlet";
  //     httpRequest.onreadystatechange=function(){
  //       if(httpRequest.readyState === XMLHttpRequest.DONE){
  //         if(httpRequest.status === 200){
  //           window.alert("Conta Criada com sucesso!");
  //           window.location.href = "http://127.0.0.1:5500/login.html";
  //         }else if(httpRequest.status === 400 || httpRequest.status === 422 || httpRequest.status === 409 || httpRequest.status === 501){
  //           window.alert(httpRequest.responseText);
  //         }
  //       }
  //     }
  //     httpRequest.open("POST", URL_SERVLET_REGISTER, true);
  //     httpRequest.setRequestHeader('Content-type','application/x-www-form-urlencoded');
      
  //     if(isOng == true){  
  //     let ongName = document.getElementById("ongName").value;
      

  //     console.log(login, password, name, cpf, birth, state, neighborhood, cep, isOng, ongName);
  //     httpRequest.send("login="+login+"&password="+password+"&name="+name+"&cpf="+cpf+"&birth="+birth+"&isOng="+isOng+"&ongName="+ongName+
  //     "&state="+state+"&city="+city+"&neighborhood="+neighborhood+"&cep="+cep);
      
  //     }else if (isOng == false){
  //     console.log(login, password, name, cpf, birth, state, neighborhood, cep, isOng);
  //     httpRequest.send("login="+login+"&password="+password+"&name="+name+"&cpf="+cpf+"&birth="+birth+"&isOng="+isOng+
  //     "&state="+state+"&city="+city+"&neighborhood="+neighborhood+"&cep="+cep);
  //     }      
  // }

  // let data;
  // //PREENCHIMENTO AUTOMATICO DE CAMPOS DE ENDEREÇO
  // cep.addEventListener("input", function(){
  //   let cepValue = cep.value;
  //   let url = "https://api.postmon.com.br/v1/cep/"+cepValue;
  //   let httpRequest = new XMLHttpRequest();
  //   httpRequest.open("GET",url);
  //   httpRequest.onload = () =>{
  //     if(httpRequest.status === 200){
  //         data = JSON.parse(httpRequest.responseText);
  //       let state = document.getElementById("state");
  //       let city = document.getElementById("city");
  //       let neighborhood = document.getElementById("neighborhood");
  //       state.value = data.estado_info.nome;
  //       city.value = data.cidade;
  //       neighborhood.value = data.bairro;
  //     }
  //   }
  //   httpRequest.send();
  // });

  return (
    <div className={styles.cadastroArea}>
      <div className={styles.title}>
        <img src="./imgs/adote_Cao_Logo.png" className={styles.logo}></img>
        <h1>Cadastro MiauDote.Cão</h1>
      </div>
      <div className={styles.subTitle}>
        <p>Dados de Acesso</p>
      </div>
      <div className={styles.forms}>
        
        <div className={styles.campos}>
          <TextField
            required
            id="login"
            name='login' 
            label="Email" 
          />
        </div>
        <div className={styles.campos}>
          <TextField
            required
            id="password"
            name='password'
            label="Password"
            type="password"
          />
        </div>
        <div className={styles.campos}>
          <FormControlLabel
            label="Sou dono de uma ONG"
            labelPlacement="end"
            control={<Checkbox
              checked={isOng}
              onChange={handleCheck}
              onClick={showOngOptions}
            />}
          />
        </div>
      </div>
      <hr />
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
            />
          </div>
          <div className={styles.campos}>
            <TextField 
              required
              id="cpf"
              name='cpf'
              placeholder="CPF*"
              value={cpf}
              onChange={(event) => {
                setCpf(formatCpf(event.target.value));
              }}
            />
          </div>
          <div className={styles.campos}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker/>
            </LocalizationProvider>
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="cep"
              name='cep'
              placeholder="CEP*"
              value={cep}
              onChange={(event) => {
                setCep(formatCep(event.target.value));
              }}
            />
          </div>
        </div>
        <div className={styles.formDiv}>
          <div className={styles.campos}>
            <TextField
              required
              id="Estado"
              name='Estado'
              label="Estado"
            />
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="Cidade"
              name='Cidade'
              label="Cidade"
            />
          </div>
          <div className={styles.campos}>
            <TextField
              required
              id="Bairro"
              name='Bairro'
              label="Bairro"
            />
          </div>
        </div>
      </div>
      <div className={styles.formInputsOng}>
        <div className={styles.subTitle}>
          <p>Dados da ONG</p>
        </div>
        <div className={styles.formInputsPessoais}>
          <div className={styles.formDiv}>
            <div className={styles.campos}>
              <TextField 
                required
                id="id"
                name='ongName'
                label="Nome da ONG"
              />
            </div>
          </div>
        </div>
      </div>
      <div className={styles.btnDiv}>
        <Link to="/" className={styles.link}>
          <Button variant="contained">
            VOLTAR
          </Button>
        </Link>
        <Button variant="contained" color="success">
          CADASTRAR
        </Button>
      </div>
    </div>
  );
}

export default Registro