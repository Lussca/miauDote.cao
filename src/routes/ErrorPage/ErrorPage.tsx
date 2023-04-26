import React from "react";
import styles from './ErrorPage.module.css';

const ErrorPage = () => {
    return(
        <div className={styles.errorScreen}>
            <iframe src="https://embed.lottiefiles.com/animation/76699" className={styles.iframe}></iframe>
            <h2>Oops, algo deu errado!</h2>
            <p>tente novamente mais tarde.</p>
        </div>
    );
}

export default ErrorPage;