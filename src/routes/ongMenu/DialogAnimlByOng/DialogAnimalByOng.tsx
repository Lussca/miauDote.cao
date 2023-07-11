//imports react
import axios from 'axios';
import { ref } from '@firebase/storage';
import { useEffect, useState, ChangeEvent } from 'react';
import { storageRef, uploadBytes, getDownloadURL } from '../../../Components/Firebase/Firebase';
import styles from '../DialogAnimlByOng/DialogAnimalByOng.module.css';

//imports MUI
import { Alert, AlertColor, Button, CircularProgress, Dialog, DialogActions, DialogContent, DialogTitle, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material';

interface Animal {
    id: string
}

interface AnimalModalProps  {
    open: boolean;
    onClose: () => void;
    animalData?: Animal | null;
}

const AnimalModalAddEdit = ({ open, onClose, animalData }: AnimalModalProps ) => {

    const animalsInformations = {
        age: '',
        animalDescription: '',
        animalToAnimal: '',
        animalToPerson: '',
        color: '',
        hairType: '',
        id: '',
        idOng: '',
        insertionDate: '',
        name: '',
        race: '',
        sex: '',
        size: '',
    };

    useEffect(() => {

        if (animalData) {
            setTitel("Editar")
            setNomeBotao("ATUALIZAR")

            const params = {
                idAnimal: animalData.id,
            };
    
            axios
                .get('http://localhost:8080/MiauDoteCao/GetAnimalDataServlet', {params})
                .then(response => {
                    setAnimal({...response.data});
                })
                .catch(error => {
                  console.error('Erro:', error);
                });
        } else {
            setAnimal({...animalsInformations});
            setTitel("Cadastrar")
            setNomeBotao("CADASTRAR")
        }

    }, [animalData]);

    const [animal, setAnimal] = useState(animalsInformations);
    const [images, setImages] = useState<File[]>([]);
    const [imageUrls, setImageUrls] = useState<string[]>([]);

    const [titel, setTitel] = useState('');
    const [nomeBotao, setNomeBotao] = useState('');

    const [msg, setMsg] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [severity, setSeverity] = useState('error');
    const [showAlert, setShowAlert] = useState(false);

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

    const handleChangeName = (event: ChangeEvent<HTMLInputElement>) => {
        setAnimal({ ...animal, name: event.target.value });
    };

    const handleChangeDescription = (event: ChangeEvent<HTMLInputElement>) => {
        setAnimal({ ...animal, animalDescription: event.target.value });
    };

    const handleChangeEspecie = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, race: event.target.value });
    };

    const handleChangePelagem = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, hairType: event.target.value });
    };

    const handleChangeSexo = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, sex: event.target.value });
    };

    const handleChangeCaa = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, animalToAnimal: event.target.value });
    };

    const handleChangeCah = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, animalToPerson: event.target.value });
    };

    const handleChangeIdade = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, age: event.target.value });
    };

    const handleChangePorte = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, size: event.target.value });
    };

    const handleChangeCor = (event: { target: { value: string; }; }) => {
        setAnimal({ ...animal, color: event.target.value });
    };

    const handleImageChange = (e: ChangeEvent<HTMLInputElement>) => {
        const files = e.target.files;
        if (files != undefined && files != null) {
          const selectedImages: File[] = Array.from(files);
          setImages(selectedImages);
        }
    };

    async function handleButtonClick() {

        setIsLoading(true);

        const idAnimal = animalData?.id;

        if (images.length === 0) {
            setShowAlert(true);
            setSeverity('error');
            setMsg('Atenção! Nenhuma imagem selecionada.');

            return;
        }

        const imgsTemp:{ [key:number]:string } = {};

        for (let i = 0; i < images.length; i++) {
            const storageChildRef = ref(storageRef, `images/${images[i].name}`);
            try {
              const snapshot = await uploadBytes(storageChildRef, images[i]);
              const url = await getDownloadURL(snapshot.ref);
              imgsTemp[i] = url;
            } catch (error) {
              console.error('Error uploading image: ', error);
            }
        }

        // setImageUrls(imgsTemp);

        if(idAnimal != null){

            const data = {
                Animal: {
                    idAnimal: idAnimal,
                    race: animal.race,
                    nome: animal.name,
                    porte: animal.size,
                    pelagem: animal.hairType,
                    caa: animal.animalToAnimal,
                    cah: animal.animalToPerson,
                    sexo: animal.sex,
                    idade: animal.age,
                    idOng: sessionStorage.getItem('userId'),
                    cor: animal.color,
                    descricao: animal.animalDescription,
                    Links: imgsTemp
                }
            };
    
            const jsonData = JSON.stringify(data);

            const token = sessionStorage.getItem('jwt');
    
            const config = {
                headers: {
                'Content-Type': 'multipart/form-data; charset=utf-8',
                'Authorization': `Bearer ${token}`
                },
            };
            
            axios
            .put('http://localhost:8080/MiauDoteCao/PetUpdateServlet', jsonData, config)
            .then(response => {
                setShowAlert(true);
                setSeverity('success');
                setMsg('Animal editado com sucesso!');

                setIsLoading(false);

                window.location.reload()
            })
            .catch(error => {
                console.error("Erro: " + error);
            });

        } else{

            const data = {
                Animal: {
                    race: animal.race,
                    nome: animal.name,
                    porte: animal.size,
                    pelagem: animal.hairType,
                    caa: animal.animalToAnimal,
                    cah: animal.animalToPerson,
                    sexo: animal.sex,
                    idade: animal.age,
                    idOng: sessionStorage.getItem('userId'),
                    cor: animal.color,
                    descricao: animal.animalDescription,
                    Links: imgsTemp
                }
            };
    
            const jsonData = JSON.stringify(data);

            const token = sessionStorage.getItem('jwt')
    
            const config = {
                headers: {
                'Content-Type': 'multipart/form-data; charset=utf-8',
                'Authorization': `Bearer ${token}`
                },
            };
        
            axios
            .post('http://localhost:8080/MiauDoteCao/PetRegisterServlet', jsonData, config)
            .then(response => {
                setShowAlert(true);
                setSeverity('success');
                setMsg('Animal cadastrado com sucesso!');

                setIsLoading(false);

                // window.location.reload()
            })
            .catch(error => {
                console.error("Erro: " + error);
            });
            
        }
    }

  return (
    <Dialog open={open} onClose={onClose}>

        {showAlert && <Alert variant="filled" severity={alertSeverity}  onClose={() => {setShowAlert(false)}}>
            {msg}
        </Alert> }

        {isLoading && <div className={styles.progress}>
            <CircularProgress />
        </div>}

      <DialogTitle>{titel} Animal</DialogTitle>
      <DialogContent className={styles.ajustesAnimalAdd}>
        <input type="file" accept="image/*" multiple onChange={handleImageChange} className={styles.input}/>
        <div className={styles.componentsText}>
            <TextField 
                value={animal.name} 
                onChange={handleChangeName} 
                id="nome" 
                label="Nome" 
                variant="outlined"
                style={{ width: '50%' }} 
            />
            <TextField 
                value={animal.age} 
                onChange={handleChangeIdade} 
                id="idade" 
                label="Idade" 
                variant="outlined" 
                style={{ width: '50%' }}
            />
            <TextField 
                value={animal.animalDescription} 
                onChange={handleChangeDescription} 
                id="descricao" 
                label="Descrição" 
                variant="outlined" 
                style={{ width: '100%', marginTop: '5%' }}
            />
        </div>
        <div className={styles.componentsSelect}>
            <FormControl style={{ width: '50%' }}>
                <InputLabel>Espécie</InputLabel>
                <Select value={animal.race} onChange={handleChangeEspecie}>
                    <MenuItem value={'1'}>Canino</MenuItem>
                    <MenuItem value={'2'}>Felino</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '50%' }}>
                <InputLabel>Porte</InputLabel>
                <Select value={animal.size} onChange={handleChangePorte}>
                    <MenuItem value={'1'}>Pequeno</MenuItem>
                    <MenuItem value={'2'}>Médio</MenuItem>
                    <MenuItem value={'3'}>Grande</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '50%', marginTop: '2%' }}>
                <InputLabel>Pelagem</InputLabel>
                <Select value={animal.hairType} onChange={handleChangePelagem}>
                    <MenuItem value={'1'}>Curto</MenuItem>
                    <MenuItem value={'2'}>Médio</MenuItem>
                    <MenuItem value={'3'}>Longo</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '50%', marginTop: '2%' }}>
                <InputLabel>Sexo</InputLabel>
                <Select value={animal.sex} onChange={handleChangeSexo}>
                    <MenuItem value={'1'}>Macho</MenuItem>
                    <MenuItem value={'2'}>Fêmea</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '50%', marginTop: '2%' }}>
                <InputLabel>Convivência entre animais</InputLabel>
                <Select value={animal.animalToAnimal} onChange={handleChangeCaa}>
                    <MenuItem value={'1'}>manso</MenuItem>
                    <MenuItem value={'2'}>agressivo</MenuItem>
                    <MenuItem value={'3'}>agitado</MenuItem>
                    <MenuItem value={'4'}>amigável</MenuItem>
                    <MenuItem value={'5'}>amedrontado</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '50%', marginTop: '2%' }}>
                <InputLabel>Convivência com humanos</InputLabel>
                <Select value={animal.animalToPerson} onChange={handleChangeCah}>
                    <MenuItem value={'1'}>manso</MenuItem>
                    <MenuItem value={'2'}>agressivo</MenuItem>
                    <MenuItem value={'3'}>agitado</MenuItem>
                    <MenuItem value={'4'}>amigável</MenuItem>
                    <MenuItem value={'5'}>amedrontado</MenuItem>
                </Select>
            </FormControl>
            <FormControl style={{ width: '100%', marginTop: '2%' }}>
                <InputLabel>Cor do pelo</InputLabel>
                <Select value={animal.color} onChange={handleChangeCor}>
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
        </div>
      </DialogContent>
      <DialogActions>
        <Button variant="contained" color="error" onClick={onClose}>CANCELAR</Button>
        <Button variant="contained" color="success" onClick={handleButtonClick}>{nomeBotao} ANIMAL</Button>
      </DialogActions>
    </Dialog>
  );
};

export default AnimalModalAddEdit;
