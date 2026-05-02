function getActiveCampana(campanas) {
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    return campanas.find(c => {
        const start = new Date(c.dia_comienzo);
        start.setHours(0, 0, 0, 0);
        const end = new Date(c.dia_final);
        end.setHours(0, 0, 0, 0);
        return today >= start && today <= end;
    });
}

function getNextCampana(campanas) {
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const upcoming = campanas.filter(c => {
        const start = new Date(c.dia_comienzo);
        start.setHours(0, 0, 0, 0);
        return start > today;
    });

    upcoming.sort((a, b) => new Date(a.dia_comienzo) - new Date(b.dia_comienzo));

    return upcoming.length > 0 ? upcoming[0] : null;
}

function getDaysRemaining(endDate) {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const end = new Date(endDate);
    end.setHours(0, 0, 0, 0);
    const diffTime = end - today;
    return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
}



export { getActiveCampana, getNextCampana, getDaysRemaining };
