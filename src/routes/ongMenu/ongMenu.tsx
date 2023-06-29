//imports padrão react
import styles from'./ongMenu.module.css';

//components
import { Navbar } from './Navbar/Navbar';

//imports MUI
import Grid from '@mui/material/Grid';

function adotanteMenu(this: any)  {

  return (
    <div className={styles.menuArea}>
      <Navbar></Navbar>
      
    </div>
  );
  
}

export default adotanteMenu