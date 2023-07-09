import { useEffect, useState } from 'react';
import axios from 'axios';
import styles from '../DialogAnimal/Dialog.module.css';

import Dialog from '@mui/material/Dialog';
import { Alert, AlertColor, Button, CircularProgress, DialogContent, DialogContentText, DialogTitle } from '@mui/material';

import Carousel from 'react-material-ui-carousel';

interface AnimalModalProps {
  open: boolean;
  onClose: () => void;
  animal: { 
    id:string, 
    name: string, 
    age: string, 
    links: Array<any>, 
    race: string, 
    size: string, 
    hairType: string, 
    sex: string, 
    idOng: string, 
    animalDescription: string} | null;
}

const AnimalModal = ({ open, onClose, animal }: AnimalModalProps) => {

  const idUser = sessionStorage.getItem("userId");
  const [severity, setSeverity] = useState('error');
  const [msg, setMsg] = useState('');
  const [showAlert, setShowAlert] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [alertTimer, setAlertTimer] = useState<NodeJS.Timeout | null>(null);

  let alertSeverity: AlertColor | undefined;

  switch (severity) {
    case 'error':
      alertSeverity = 'error';
      break;
    case 'warning':
      alertSeverity = 'warning';
      break;
    case 'info':
      alertSeverity = 'info';
      break;
    case 'success':
      alertSeverity = 'success';
      break;
    default:
      alertSeverity = undefined;
  }

  useEffect(() => {
    return () => {
      if (alertTimer) {
        clearTimeout(alertTimer);
      }
    };
  }, [alertTimer]);

  function handleButtonClick(idAnimal: string) {

    setIsLoading(true);

    const token = sessionStorage.getItem('jwt')
    const config = {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    };
  
    axios
      .post('http://localhost:8080/MiauDoteCao/AdoptionApplicationServlet', {
        idUser: idUser,
        idAnimal: idAnimal
      }, config)
      .then(response => {
        setIsLoading(false);
        setShowAlert(true);
        setSeverity('success');
        setMsg('Candidatura realizada com sucesso!');
        setAlertTimer(
          setTimeout(() => {
            setShowAlert(false);
          }, 500)
        );
      })
      .catch(error => {
        setIsLoading(false);
        setShowAlert(true);
        setSeverity('error');
        setMsg('Você já se candidatou a adoção deste animal.');
        setAlertTimer(
          setTimeout(() => {
            setShowAlert(false);
          }, 500)
        );
      });
  }

  return (
    <Dialog open={open} onClose={onClose} aria-labelledby="responsive-dialog-title">
      {/* Loading de carregamento */}
      {isLoading && <div className={styles.progress}>
         <CircularProgress />
      </div>}
      {showAlert && <Alert variant="filled" severity={alertSeverity}  onClose={() => {setShowAlert(false)}}>
        {msg}
      </Alert> }
      {animal && (
        <>
        <div style={{ width: '50%' }}></div>
        <Carousel animation="slide">
          {animal.links.join(',').split(',').map((link, index) => (
            <img 
              key={index} 
              src={link.trim()} 
              alt={animal.name} 
              style={{ width: '100%', display: 'flex', justifyContent: 'center' }} />
          ))}
        </Carousel>
        <DialogTitle id="responsive-dialog-title">
            {animal.name} ({animal.sex})
        </DialogTitle>
        <DialogContent dividers>
            <DialogContentText>
                Raça: {animal.race}
            </DialogContentText>
            <DialogContentText>
                Porte: {animal.size}
            </DialogContentText>
            <DialogContentText>
                Pelagem: {animal.hairType}
            </DialogContentText>
        </DialogContent>
        <DialogContent dividers>
        <DialogTitle style={{ padding: 0 }}>
            Sobre o animal 
        </DialogTitle>
            <DialogContentText>
                {animal.animalDescription}
            </DialogContentText>
        </DialogContent>
        <Button style={{ color: "#fff", backgroundColor: "#1bff00" }} onClick={() => handleButtonClick(animal?.id)}>candidatar-se</Button>
        </>
      )}
    </Dialog>
  );
};

export default AnimalModal;
