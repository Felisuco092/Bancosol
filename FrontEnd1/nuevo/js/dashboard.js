import { fetch_data } from "./utils/fetch.js";
import { getActiveCampana, getNextCampana, getDaysRemaining } from "./utils/date_utils.js";

const metricTotalTiendas = document.getElementById('metric-total-tiendas');
const metricTotalVoluntarios = document.getElementById('metric-total-voluntarios');
const metricTopCadenas = document.getElementById('metric-top-cadenas-tbody');
const metricDaysRemaining = document.getElementById('metric-days-remaining');
const metricDaysLabel = document.getElementById('metric-days-label');

function getTotalVoluntarios(voluntarioEntidad, voluntarioFisico) {
    const entidadCount = voluntarioEntidad.reduce((sum, v) => sum + v.n_voluntarios, 0);
    const fisicoCount = voluntarioFisico.length;
    return entidadCount + fisicoCount;
}

function getTopCadenas(cadenas, tiendas) {
    const cadenaCount = {};

    tiendas.forEach(t => {
        const id = t.id_cadena;
        cadenaCount[id] = (cadenaCount[id] || 0) + 1;
    });

    const sorted = Object.entries(cadenaCount)
        .sort((a, b) => b[1] - a[1])
        .slice(0, 4);

    return sorted.map(([id, count]) => {
        const cadena = cadenas.find(c => String(c.id) === String(id));
        return {
            nombre: cadena.nombre,
            tiendas: count
        };
    });
}

function renderTopCadenas(topCadenas) {
    metricTopCadenas.innerHTML = '';

    if (topCadenas.length === 0) {
        metricTopCadenas.innerHTML = '<tr><td colspan="2">No hay datos disponibles</td></tr>';
        return;
    }

    topCadenas.forEach(c => {
        const row = `<tr><td>${c.nombre}</td><td><strong>${c.tiendas}</strong></td></tr>`;
        metricTopCadenas.insertAdjacentHTML('beforeend', row);
    });
}

function renderDaysMetric(campanas) {
    const active = getActiveCampana(campanas);

    if (active) {
        const days = getDaysRemaining(active.dia_final);
        metricDaysRemaining.textContent = days > 0 ? days : '0';
        metricDaysLabel.textContent = `Días restantes de "${active.nombre}"`;
    } else {
        const next = getNextCampana(campanas);

        if (next) {
            const daysUntil = getDaysRemaining(next.dia_comienzo);
            metricDaysRemaining.textContent = '---';
            metricDaysLabel.textContent = `No hay campañas activas. Próxima: "${next.nombre}" en ${daysUntil} días`;
        } else {
            metricDaysRemaining.textContent = '---';
            metricDaysLabel.textContent = 'No hay campañas activas';
        }
    }
}

function renderDashboard(data) {
    const tiendas = data[0];
    const cadenas = data[1];
    const campanas = data[2];
    const voluntarioEntidad = data[3];
    const voluntarioFisico = data[4];

    metricTotalTiendas.textContent = tiendas.length;
    metricTotalVoluntarios.textContent = getTotalVoluntarios(voluntarioEntidad, voluntarioFisico);

    const topCadenas = getTopCadenas(cadenas, tiendas);
    renderTopCadenas(topCadenas);

    renderDaysMetric(campanas);
}

Promise.all([
    fetch_data('tiendas'),
    fetch_data('cadenas'),
    fetch_data('campanas'),
    fetch_data('voluntario_entidad'),
    fetch_data('voluntario_fisico')
]).then(renderDashboard).catch(err => console.error(err));

let user = sessionStorage.getItem("user") 

if(user) {
    user = JSON.parse(user)
    const bienvenido = document.querySelector("#bienvenido")
    bienvenido.textContent = `Bienvenido, ${user.nombre}`
}
