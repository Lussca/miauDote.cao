import styles from'./Content.module.css';

export const ContentThree = () =>{

    return(
        <div className={styles.ContentThree}>
            <h2 className={styles.titleThree}>Quer fazer parte dessa corrente do bem e adotar?  Saiba como funciona</h2>
            <div className={styles.contentItens}>
                <div className={styles.itens}>
                    <img src="../imgs/icons/pet-house.png" className={styles.icons} />
                    <h3>Ache seu pet</h3>
                    <p>Acesse nosso botão Quero adotar, assim conhecera nossos diversos Pets disponíveis para adoção, pertencentes a nossas ONGS/protetores parceiros, para criar a conexão perfeita entre adotante e Pet.</p>                   
                </div>
                <div className={styles.itens}>
                    <img src="../imgs/icons/medical-report.png" className={styles.icons} />
                    <h3>Formulário de Interesse</h3>
                    <p>Preencha o formulário de interesse que disponibilizamos no botão Formulário responsável que a ONG/protetor entrará em contato com você em até 38h.</p>
                </div>
                <div className={styles.itens}>
                    <img src="../imgs/icons/registration.png" className={styles.icons} />
                    <h3>Avaliação da adoção</h3>
                    <p>A ONG/protetor parceiro irá fazer a análise do cadastro e perfil do adotante vs pet escolhido. Preenchendo os requisitos, você recebe a aprovação na hora.</p>
                </div>
                <div className={styles.itens}>
                    <img src="../imgs/icons/dog-training.png" className={styles.icons} />
                    <h3>Adoção Completa</h3>
                    <p>Caso seja aprovado na hora pelo voluntário da ONG/protetor, você já pode levar seu pet para casa! Se enviou o formulário online, espere o contato e a aprovação. Com tudo certo, você busca seu pet em um local e dia combinado com a ONG/protetor.</p>
                </div>
            </div>
            <div className={styles.fundo}>
                <p></p>
            </div>
        </div>
    )
}