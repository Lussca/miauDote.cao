//imports padrão react
import styles from'./Adote.module.css';

//import Componentes
import Sidebar from './Sidebar/Sidebar';

//imports MUI
import Grid from '@mui/material/Grid';


function Adote(this: any)  {

    return (
        <div className={styles.adoteArea}>
            <Grid container spacing={2} margin={0}>
                <Grid xs={2} style={{ backgroundColor: "#fff" }}>
                    <Sidebar></Sidebar>
                </Grid>
                <Grid xs={10}>

                </Grid>
            </Grid>
        </div>
    );
  
}

export default Adote