import axios from "axios";
import { useEffect, useState } from "react";

import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';

import AnimalModal from "../../../Components/DialogAnimal/Dialog";

interface AnimalsProps {
  filterApplied: boolean;
  removeFilter: () => void;
  filters: {
    especie: string;
    pelagem: string;
    sexo: string;
    caa: string;
    cah: string;
    idade: string;
  };
}

const Animals = ({ filterApplied, removeFilter, filters }: AnimalsProps) => {
  const [openModal, setOpenModal] = useState(false);
  const [selectedAnimal, setSelectedAnimal] = useState<{ id:String, name: string, age: string, imageUrl: string, race: string, size: string, hairType: string, sex: string, idOng: string, animalDescription: string} | null>(null);
  const [animalData, setAnimalData] = useState<{ id:String, name: string, age: string, imageUrl: string, race: string, size: string, hairType: string, sex: string, idOng: string, animalDescription: string}[]>([]);

  useEffect(() => {
    if(filterApplied){
      console.log("consulta 1")
    // com filtros
    axios
    .post('http://localhost:8080/MiauDoteCao/GetAnimalByFilter', filters)
    .then(response => {
      setAnimalData(response.data.animals);
    })
    .catch(error => {
      console.error('Erro:', error);
    });

    } else{
      console.log("consulta 2")
      // todos os animais
      axios
      .get('http://localhost:8080/MiauDoteCao/GetAllAnimals')
      .then(response => {
        console.log(response.data)
        setAnimalData(response.data.animals);
      })
      .catch(error => {
        console.error('Erro:', error);
      });
    }
  }, [filterApplied, filters]);

  const openAnimalModal = (animal: { id:String, name: string, age: string, imageUrl: string, race: string, size: string, hairType: string, sex: string, idOng: string, animalDescription: string}) => {
    setSelectedAnimal(animal);
    setOpenModal(true);
  };
  
  return (
    <>
      {animalData.map((animal, index) => (
        <Card key={index} sx={{ width: 200, height: 'auto', marginBottom: 3 }} onClick={() => openAnimalModal(animal)}>
          <CardActionArea>
            <CardMedia
              component="img"
              height="140"
              image={animal.imageUrl}
              alt={animal.name}
            />
            <CardContent>
              <Typography gutterBottom variant="h5" component="div">
                {animal.name}
              </Typography>
              <Typography variant="body2" color={animal.sex === 'Fêmea' ? 'pink' : 'blue'}>
                {animal.sex} || {animal.age} ano
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {animal.animalDescription}
              </Typography>
            </CardContent>
          </CardActionArea>
        </Card>
      ))}
      <AnimalModal
        open={openModal}
        onClose={() => setOpenModal(false)}
        animal={selectedAnimal}
      />
    </>
  );
};

export default Animals;
