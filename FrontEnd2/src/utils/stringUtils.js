export function quitarTildes(texto) {
  const mapaAcentos = {
    'á': 'a', 'é': 'e', 'í': 'i', 'ó': 'o', 'ú': 'u',
    'Á': 'A', 'É': 'E', 'Í': 'I', 'Ó': 'O', 'Ú': 'U',
    'ü': 'u', 'Ü': 'U'
  };

  return texto.replace(/[áéíóúüÁÉÍÓÚÜ]/g, (letra) => mapaAcentos[letra]);
}
