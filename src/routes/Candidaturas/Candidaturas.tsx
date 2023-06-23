//imports padrão react
import styles from'./Candidaturas.module.css';

//components
import { Navbar } from '../adotanteMenu/Navbar/Navbar';
import TableList from './TableListCandidaturas';

function Candidaturas(this: any)  {

    return (
        <div className={styles.menuArea}>
            <Navbar></Navbar>
            <div className={styles.tab}>
                <TableList></TableList>
            </div>
        </div>
    );
  
}

export default Candidaturas