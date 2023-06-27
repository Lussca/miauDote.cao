import { useEffect, useState } from 'react';
import axios from 'axios';
import styles from '../DialogAnimal/Dialog.module.css';

import Dialog from '@mui/material/Dialog';
import { Alert, AlertColor, Button, DialogContent, DialogContentText, DialogTitle } from '@mui/material';

interface AnimalModalProps {
  open: boolean;
  onClose: () => void;
  animal: { 
    id:string, 
    name: string, 
    age: string, 
    imageUrl: string, 
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
  
    axios
      .post('http://localhost:8080/MiauDoteCao/AdoptionApplicationServlet', {
        idUser: idUser,
        idAnimal: idAnimal
      })
      .then(response => {
        console.log(response)
          setShowAlert(true);
          setSeverity('success');
          setMsg('Candidatura realizada com sucesso!');
          setAlertTimer(
          setTimeout(() => {
            setShowAlert(false);
          }, 1500)
        );
      })
      .catch(error => {
        setShowAlert(true);
        setSeverity('error');
        setMsg('Você já se candidatou a adoção deste animal.');
        setAlertTimer(
          setTimeout(() => {
            setShowAlert(false);
          }, 1500)
        );
      });
  }

  return (
    <Dialog open={open} onClose={onClose} aria-labelledby="responsive-dialog-title">
       <div className={styles.alertArea}>
        {showAlert && <Alert variant="filled" severity={alertSeverity}  onClose={() => {setShowAlert(false)}}>
          {msg}
        </Alert> }
      </div>
      {animal && (
        <>
        <div style={{ display: 'flex', justifyContent: 'center', marginTop: '5%' }}>
            <img src={animal.imageUrl} alt={animal.name} style={{ width: '15em' }}/>
        </div>
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
