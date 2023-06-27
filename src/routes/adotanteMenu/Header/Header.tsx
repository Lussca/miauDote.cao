import styles from'./Header.module.css';

import DogAnimation from '../dog/dogAnimation';
import Welcome from '../Welcome/welcome';

export const Header = () =>{

    return(
        <div className={styles.Header}>
            <DogAnimation/>
            <Welcome/>
        </div>
    )
}