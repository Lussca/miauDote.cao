import React from 'react';
import Dialog from '@mui/material/Dialog';
import Typography from '@mui/material/Typography';
import { Button, DialogContent, DialogContentText, DialogTitle } from '@mui/material';

interface AnimalModalProps {
  open: boolean;
  onClose: () => void;
  animal: { 
    id:String, 
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

function handleButtonClick(){

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
            <DialogContentText style={{ display: 'flex' }}>
                Raça: {animal.race}
            </DialogContentText>
            <DialogContentText style={{ display: 'flex' }}>
                Porte: {animal.size}
            </DialogContentText>
            <DialogContentText style={{ display: 'flex' }}>
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
        <Button style={{ color: "#fff", backgroundColor: "#1bff00" }} onClick={handleButtonClick}>candidatar-se</Button>
        </>
      )}
    </Dialog>
  );
};

export default AnimalModal;
