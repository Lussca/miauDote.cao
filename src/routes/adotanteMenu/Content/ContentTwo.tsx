import styles from'./Content.module.css';

export const ContentTwo = () =>{

    return(
        <div className={styles.ContentTwo}>
            <div className={styles.areaContent}>
                <p className={styles.titleContenteTwo}>Apoie e faça parte da maior rede de adoção e bem estar de cães e gatos do Brasil.</p>
                <p className={styles.subTitleContenteTwo}>Toda ajuda conta e faz muita diferença.</p>
                <div className={styles.centralize}>
                    <div className={styles.layout}>
                        <p className={styles.TitleFont}><span className={styles.laranja}>+</span>10 mil</p>
                        <hr />
                        <p className={styles.subTitleFont}>animais adotados em 2023</p>
                    </div>
                    <div className={styles.layout} style={{marginLeft: '10'}}>
                        <p className={styles.TitleFont}><span className={styles.laranja}>+</span>100</p>
                        <hr />
                        <p className={styles.subTitleFont}>ONGs e protetores cadastrados</p>
                    </div>
                </div>
            </div>
        </div>
    )
}