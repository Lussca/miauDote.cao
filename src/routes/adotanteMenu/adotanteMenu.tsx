//imports padrão react
import styles from'./adotanteMenu.module.css';

//imports mui
import Alert, { AlertColor } from '@mui/material/Alert';

//componentes
import { Navbar } from './Navbar/Navbar';
import { Header } from './Header/Header';
import { ContentOne } from './Content/ContentOne';
import { ContentTwo } from './Content/ContentTwo';
// import { ContentThree } from './Content/ContentThree';

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
            <Header></Header>
            <ContentOne></ContentOne>
            <ContentTwo></ContentTwo>
        </div>
    );
  
}

export default adotanteMenu