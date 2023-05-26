//imports padrão react
import styles from'./Welcome.module.css';

function Welcome(this: any)  {

  return (
    <div className={styles.frase}>
      <div className={styles.animationLogin}>
            <span className={styles.firstText}>Olá, </span>
            <span className={styles.slide}>
                <span className={styles.secondText}>Você acaba de entrar no sistema!</span>
            </span>
        </div>
    </div>
  );
}

export default Welcome