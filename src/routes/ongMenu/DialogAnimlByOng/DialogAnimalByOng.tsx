//imports react
import axios from 'axios';
import { getStorage, ref } from '@firebase/storage';
import React, { useEffect, useState, ChangeEvent } from 'react';
import { storageRef, uploadBytes, getDownloadURL } from '../../../Components/Firebase/Firebase';

//imports MUI
import { AlertColor, Button, Dialog, DialogActions, DialogContent, DialogTitle, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material';

interface ModalProps {
  open: boolean;
  onClose: () => void;
}

const AnimalModalAdd = ({ open, onClose }: ModalProps) => {

    const animalsInformations = {
        cah: '',
        caa: '',
        cor: '',
        nome: '',
        sexo: '',
        idade: '',
        porte: '',
        especie: '',
        pelagem: '',
        descricao: '',
    };

    const [animal, setAnimal] = useState(animalsInformations);

    const handleChangeName = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, nome: event.target.value });
    };

    const handleChangeDescription = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, descricao: event.target.value });
    };

    const handleChangeEspecie = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, especie: event.target.value });
    };

    const handleChangePelagem = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, pelagem: event.target.value });
    };

    const handleChangeSexo = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, sexo: event.target.value });
    };

    const handleChangeCaa = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, caa: event.target.value });
    };

    const handleChangeCah = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, cah: event.target.value });
    };

    const handleChangeIdade = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, idade: event.target.value });
    };

    const handleChangePorte = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, porte: event.target.value });
    };

    const handleChangeCor = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, cor: event.target.value });
    };

    const [images, setImages] = useState<File[]>([]);
    const [imageUrls, setImageUrls] = useState<string[]>([]);

    const handleImageChange = (e: ChangeEvent<HTMLInputElement>) => {
        const files = e.target.files;
        if (files) {
          const selectedImages: File[] = Array.from(files);
          setImages(selectedImages);
        }
    };

    async function handleButtonClick() {

        for (const image of images) {
            const storageChildRef = ref(storageRef, `images/${image.name}`);
            try {
              const snapshot = await uploadBytes(storageChildRef, image);
              const url = await getDownloadURL(snapshot.ref);
              setImageUrls((prevImageUrls) => [...prevImageUrls, url]);
            } catch (error) {
              console.error('Error uploading image: ', error);
            }
        }

        const animalData = {
            Animal: {
                race: animal.especie,
                nome: animal.nome,
                porte: animal.porte,
                pelagem: animal.pelagem,
                caa: animal.caa,
                cah: animal.cah,
                sexo: animal.sexo,
                idade: animal.idade,
                idOng: sessionStorage.getItem('userId'),
                cor: animal.cor,
                descricao: animal.descricao,
                Links: {
                    link1: imageUrls[0] || '',
                    link2: imageUrls[1] || '',
                    link3: imageUrls[2] || '',
                    link4: imageUrls[3] || ''
                }
            }
        };
    
        axios
        .post('http://localhost:8080/MiauDoteCao/PetRegistetServlet', animalData)
        .then(response => {
            console.log(response);
        })
        .catch(error => {
            console.error("Erro: " + error);
        });
        
    }

  return (
    <Dialog open={open} onClose={onClose} style={{ overflowY: 'initial' }}>
      <DialogTitle>Adicionar Animal</DialogTitle>
      <DialogContent style={{ overflowY: 'initial' }}>
        <input type="file" accept="image/*" multiple onChange={handleImageChange} />
        <TextField value={animal.nome} onChange={handleChangeName} id="nome" label="Nome" variant="outlined" style={{ width: '50%', marginTop: '5%' }}/>
        <TextField value={animal.idade} onChange={handleChangeIdade} id="idade" label="Idade" variant="outlined" style={{ width: '50%', marginTop: '5%' }}/>
        <TextField value={animal.descricao} onChange={handleChangeDescription} id="descricao" label="Descrição" variant="outlined" style={{ width: '100%', marginTop: '5%' }}/>
        <FormControl style={{ width: '50%', marginTop: '5%' }}>
            <InputLabel>Espécie</InputLabel>
            <Select value={animal.especie} onChange={handleChangeEspecie}>
                <MenuItem value={'1'}>Canino</MenuItem>
                <MenuItem value={'2'}>Felino</MenuItem>
            </Select>
        </FormControl>
        <FormControl style={{ width: '50%', marginTop: '5%' }}>
            <InputLabel>Porte</InputLabel>
            <Select value={animal.porte} onChange={handleChangePorte}>
                <MenuItem value={'1'}>Pequeno</MenuItem>
                <MenuItem value={'2'}>Médio</MenuItem>
                <MenuItem value={'3'}>Grande</MenuItem>
            </Select>
        </FormControl>
        <FormControl style={{ width: '50%', marginTop: '5%' }}>
            <InputLabel>Pelagem</InputLabel>
            <Select value={animal.pelagem} onChange={handleChangePelagem}>
                <MenuItem value={'1'}>Curto</MenuItem>
                <MenuItem value={'2'}>Médio</MenuItem>
                <MenuItem value={'3'}>Longo</MenuItem>
            </Select>
        </FormControl>
        <FormControl style={{ width: '50%', marginTop: '5%' }}>
            <InputLabel>Sexo</InputLabel>
            <Select value={animal.sexo} onChange={handleChangeSexo}>
                <MenuItem value={'1'}>Macho</MenuItem>
                <MenuItem value={'2'}>Fêmea</MenuItem>
            </Select>
        </FormControl>
        <FormControl style={{ width: '50%', marginTop: '5%' }}>
            <InputLabel>Convivência entre animais</InputLabel>
            <Select value={animal.caa} onChange={handleChangeCaa}>
                <MenuItem value={'1'}>manso</MenuItem>
                <MenuItem value={'2'}>agressivo</MenuItem>
                <MenuItem value={'3'}>agitado</MenuItem>
                <MenuItem value={'4'}>amigável</MenuItem>
                <MenuItem value={'5'}>amedrontado</MenuItem>
            </Select>
        </FormControl>
        <FormControl style={{ width: '50%', marginTop: '5%' }}>
            <InputLabel>Convivência com humanos</InputLabel>
            <Select value={animal.cah} onChange={handleChangeCah}>
                <MenuItem value={'1'}>manso</MenuItem>
                <MenuItem value={'2'}>agressivo</MenuItem>
                <MenuItem value={'3'}>agitado</MenuItem>
                <MenuItem value={'4'}>amigável</MenuItem>
                <MenuItem value={'5'}>amedrontado</MenuItem>
            </Select>
        </FormControl>
        <FormControl style={{ width: '100%', marginTop: '5%' }}>
            <InputLabel>Cor do pelo</InputLabel>
            <Select value={animal.cor} onChange={handleChangeCor}>
                <MenuItem value={'1'}>Marrom</MenuItem>
                <MenuItem value={'2'}>Branco</MenuItem>
                <MenuItem value={'3'}>Preto</MenuItem>
                <MenuItem value={'4'}>Dourado</MenuItem>
                <MenuItem value={'5'}>Bege</MenuItem>
                <MenuItem value={'6'}>Laranja</MenuItem>
                <MenuItem value={'7'}>Rajado</MenuItem>
                <MenuItem value={'8'}>Cinza</MenuItem>
                <MenuItem value={'9'}>Bicolor</MenuItem>
                <MenuItem value={'10'}>Tricolor</MenuItem>
            </Select>
        </FormControl>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>CANCELAR</Button>
        <Button onClick={handleButtonClick}>CADASTRAR ANIMAL</Button>
      </DialogActions>
    </Dialog>
  );
};

export default AnimalModalAdd;
