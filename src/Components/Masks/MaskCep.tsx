//Mascara para Cep
function formatCep(value: string) {
    return value
    .replace(/\D/g, '') // Remove tudo que não é dígito
    .replace(/^(\d{5})(\d{1,3})?/, '$1-$2') // Formata os cinco primeiros dígitos
    .substring(0, 9); // Limita o tamanho do CEP para 9 caracteres
}

export default formatCep;