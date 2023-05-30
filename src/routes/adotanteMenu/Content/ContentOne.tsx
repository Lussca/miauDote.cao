import styles from'./Content.module.css';

export const ContentOne = () =>{

    return(
        <div className={styles.ContentOne}>
            <h1>Conheça o MiauDote.Cão</h1>
            <p className={styles.describe}>O Adote.Cão faz a conexão entre quem deseja adotar um pet com as ONGS de sua região, facilitando assim esse contato.</p>
            <p className={styles.describe}>ONGs/protetores parceiros ficam responsáveis pelo processo e entrevista com os potenciais adotantes.
            Disponibilizamos também diversos filtros para facilitar a busca do seu pet ideal.</p>
        </div>
    )
}