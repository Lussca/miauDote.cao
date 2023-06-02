//imports padrão react
import styles from'./Header.module.css';

function Header(this: any)  {

    return (
        <div className={styles.headerArea}>
            <div className={styles.text}>
                <h1>Campanha de Adoção</h1>
                <p>Uma seleção especial de peludinhos que buscam um lar para chamar de seu. Não encontrou seu pet aqui ainda? 
                <span className={styles.textStyles}> Preencha o formulário </span> 
                de intenção que uma ONG parceria entrará em contato com você.</p>
            </div>
        </div>
    );
  
}

export default Header