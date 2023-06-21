//imports padrão react
import styles from'./adotanteMenu.module.css';

//componentes
import { Navbar } from './Navbar/Navbar';
import { Header } from './Header/Header';
import { ContentOne } from './Content/ContentOne';
import { ContentTwo } from './Content/ContentTwo';
import { ContentThree } from './Content/ContentThree';

function adotanteMenu(this: any)  {

    return (
        <div className={styles.menuArea}>
            <Navbar></Navbar>
            <Header></Header>
            <ContentOne></ContentOne>
            <ContentTwo></ContentTwo>
            <ContentThree></ContentThree>
        </div>
    );
  
}

export default adotanteMenu