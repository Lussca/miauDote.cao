//imports padrão react
import styles from'./Candidaturas.module.css';

//components
import { Navbar } from '../adotanteMenu/Navbar/Navbar';
import TableList from './TableListCandidaturas';

function Candidaturas(this: any)  {

    return (
        <div className={styles.menuArea}>
            <Navbar></Navbar>
            <TableList></TableList>
        </div>
    );
  
}

export default Candidaturas