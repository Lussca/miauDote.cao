//imports padrão react
import styles from'./QueroAdotar.module.css';

//imports mui
import Alert, { AlertColor } from '@mui/material/Alert';

//componentes
import { Navbar } from '../adotanteMenu/Navbar/Navbar';
import Header from './Header/Header';
import Adote from './Adote/Adote';

function QueroAdotar(this: any)  {

    return (
        <div className={styles.menuArea}>
            <Navbar></Navbar>
            <Header></Header>
            <Adote></Adote>
        </div>
    );
  
}

export default QueroAdotar