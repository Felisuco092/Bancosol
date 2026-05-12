export function getActiveCampana(campanas) {
  const today = new Date();
  return campanas.find(c => {
    const start = new Date(c.dia_comienzo);
    const end = new Date(c.dia_final);
    return today >= start && today <= end;
  }) || null;
}

export function getNextCampana(campanas) {
  const today = new Date();
  const future = campanas
    .filter(c => new Date(c.dia_comienzo) > today)
    .sort((a, b) => new Date(a.dia_comienzo) - new Date(b.dia_comienzo));
  return future[0] || null;
}

export function getDaysRemaining(endDate) {
  const today = new Date();
  const end = new Date(endDate);
  const diff = end - today;
  return Math.max(0, Math.ceil(diff / (1000 * 60 * 60 * 24)));
}
