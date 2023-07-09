import axios from "axios";
import { useEffect, useState } from "react";

import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';

import AnimalModal from "../DialogAnimal/Dialog";

interface AnimalsProps {
  filterApplied: boolean;
  filters: {
    especie: string;
    pelagem: string;
    sexo: string;
    caa: string;
    cah: string;
    idade: string;
  };
}

interface animals {
  id: string;
  name: string;
  age: string;
  links: any[]; // Você pode substituir "any" pelo tipo adequado dos elementos do array "links"
  race: string;
  size: string;
  hairType: string;
  sex: string;
  idOng: string;
  animalDescription: string;
}

const Animals = ({ filterApplied, filters }: AnimalsProps) => {
  const [openModal, setOpenModal] = useState(false);
  const [selectedAnimal, setSelectedAnimal] = useState<{
    id: string;
    name: string;
    age: string;
    links: Array<any>; // Ou substitua "any" pelo tipo adequado para os elementos do array "links"
    race: string;
    size: string;
    hairType: string;
    sex: string;
    idOng: string;
    animalDescription: string;
  } | null>(null);
  const [animalData, setAnimalData] = useState<animals[]>([]);

  const idUser = sessionStorage.getItem("userId");

  const config = idUser ? { ...filters, idUser } : { ...filters };

  useEffect(() => {
    if(filterApplied){

      axios
      .post('http://localhost:8080/MiauDoteCao/AnimalFilterServlet', config)
      .then(response => {
        setAnimalData(response.data.animals);
      })
      .catch(error => {
        console.error('Erro:', error);
      });

    } else{

      const params = {
        idUser: sessionStorage.getItem("userId"),
      };
      
      axios
      .get('http://localhost:8080/MiauDoteCao/GetAllAnimals',{params})
      .then(response => {
        setAnimalData(response.data.animals);
      })
      .catch(error => {
        console.error('Erro:', error);
      });
      
    }
  }, [filterApplied, filters]);

  const openAnimalModal = (animal: animals) => {
    setSelectedAnimal(animal);
    setOpenModal(true);
  };
  
  return (
    <>
      {animalData.length === 0 || animalData.length === null ? (
        <Typography variant="body1">nenhum animal encontrado no seu Estado.</Typography>
      ) : (
      animalData.map((animal, index) => (
        <Card key={index} sx={{ width: 200, height: 'auto', maxHeight: '21em' , marginBottom: 3 }} onClick={() => openAnimalModal(animal)}>
          <CardActionArea>
            <CardMedia
              component="img"
              height="140"
              image={animal.links[0]}
              alt={animal.name}
            />
            <CardContent>
              <Typography gutterBottom variant="h5" component="div">
                {animal.name}
              </Typography>
              <Typography variant="body2" color={animal.sex === 'Fêmea' ? 'pink' : 'blue'}>
                {animal.sex} || {animal.age} anos
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {animal.animalDescription}
              </Typography>
            </CardContent>
          </CardActionArea>
        </Card>
        ))
      )}
      <AnimalModal
        open={openModal}
        onClose={() => setOpenModal(false)}
        animal={selectedAnimal}
      />
    </>
  );
};

export default Animals;
