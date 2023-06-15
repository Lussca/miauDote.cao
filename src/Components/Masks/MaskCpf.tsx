//Mascar pra Cpf
function formatCpf(value: string) {
    return value
    .replace(/\D/g, '') // Remove tudo que não é dígito
    .replace(/(\d{3})(\d)/, '$1.$2') // Coloca um ponto entre o terceiro e o quarto dígitos
    .replace(/(\d{3})(\d)/, '$1.$2') // Coloca um ponto entre o sexto e o sétimo dígitos
    .replace(/(\d{3})(\d{1,2})$/, '$1-$2') // Coloca um hífen entre o nono e o décimo dígitos
    .substring(0, 14); // Limita o tamanho do CPF para 14 caracteres
}

export default formatCpf;