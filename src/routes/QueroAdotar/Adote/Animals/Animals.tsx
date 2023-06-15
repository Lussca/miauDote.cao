const Animals = () =>{
    const teste = [1,2,3]
    return (<>{
        teste.map((item)=>(
            <h1>{item}</h1>
        ))
    }</>)
} 

export default Animals;