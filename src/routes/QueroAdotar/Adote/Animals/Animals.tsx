import axios from "axios";
import { useEffect, useState } from "react";

import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';

const Animals = () => {
  const [animalData, setAnimalData] = useState<{ id:String, name: string, age: string, imageUrl: string, race: string, size: string, hairType: string, sex: string, idOng: string}[]>([]);

  useEffect(() => {
    sendRequest();
  }, []);

  function sendRequest() {
    axios
      .get('http://localhost:8080/MiauDoteCao/GetAllAnimals')
      .then(response => {
        setAnimalData(response.data.animals);
      })
      .catch(error => {
        console.error('Erro:', error);
      });
  }

  return (
    <>
      {animalData.map((animal, index) => (
        <Card key={index} sx={{ width: 200, height: 250, marginBottom: 3 }}>
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
              <Typography variant="body2" color="text.secondary">
                {animal.race}
                {animal.hairType}
                {animal.sex}
              </Typography>
            </CardContent>
          </CardActionArea>
        </Card>
      ))}
    </>
  );
};

export default Animals;
