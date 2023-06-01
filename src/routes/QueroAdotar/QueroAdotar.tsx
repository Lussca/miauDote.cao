//imports padrão react
import styles from'./QueroAdotar.module.css';

//imports mui
import Alert, { AlertColor } from '@mui/material/Alert';

//componentes
import { Navbar } from './Navbar/Navbar';

// validação para verificar se o usuário está logado
sessionStorage.setItem("isLoggedIn", "true");

var isLoggedIn = sessionStorage.getItem("isLoggedIn");

if (isLoggedIn === "true") {
} else {
    window.location.href = "/error";
}

function QueroAdotar(this: any)  {

    return (
        <div className={styles.menuArea}>
            <Navbar></Navbar>
            {/* <Header></Header> */}
        </div>
    );
  
}

export default QueroAdotar