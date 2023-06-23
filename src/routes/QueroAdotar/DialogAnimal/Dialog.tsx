import { useState } from 'react';
import axios from 'axios';

import Dialog from '@mui/material/Dialog';
import { Button, DialogContent, DialogContentText, DialogTitle } from '@mui/material';

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

function handleButtonClick(idAnimal: string) {

  const idUser = sessionStorage.getItem("userId");

  axios
    .post('http://localhost:8080/MiauDoteCao/AdoptionApplicationServlet', {
      idUser: idUser,
      idAnimal: idAnimal
    })
    .then(response => {
      console.log("deu certo!")
    })
    .catch(error => {
      console.error('Erro:', error);
    });
}

const AnimalModal = ({ open, onClose, animal }: AnimalModalProps) => {
  return (
    <Dialog open={open} onClose={onClose} aria-labelledby="responsive-dialog-title">
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
