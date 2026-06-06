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

export function isInvalidDateRange(startDate, endDate) {
  return new Date(startDate) > new Date(endDate);
}

export function hasDateOverlap(startDate, endDate, allCampaigns, currentCampaignId = null) {
  const start = new Date(startDate);
  const end = new Date(endDate);

  return allCampaigns.some(camp => {
    // Si estamos editando, ignoramos la campaña actual
    if (currentCampaignId && String(camp.id) === String(currentCampaignId)) {
      return false;
    }

    const campInicio = new Date(camp.dia_comienzo);
    const campFin = new Date(camp.dia_final);

    // Lógica de solapamiento: [A, B] se solapa con [C, D] si A <= D y B >= C
    return start <= campFin && end >= campInicio;
  });
}
