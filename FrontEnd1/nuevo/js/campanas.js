import { fetch_data } from "./utils/fetch.js";

//Cargar los json y meterlo a la tabla

function modelo_Fila(campana) {
    return `<tr class="campaign-row" data-start="${campana.dia_comienzo}" data-end="${campana.dia_final}">
                        <td>${campana.nombre}</td>
                        <td>${campana.ano}</td>
                        <td>${campana.dia_comienzo}</td>
                        <td>${campana.dia_final}</td>
                        <td class="status-cell"></td>
                        <td>
                            <button class="btn btn-primary btn-sm">Editar</button><br>
                            <button class="btn btn-action btn-info">Tiendas</button><br>
                            <button class="btn btn-action btn-warning">Colaboradores</button><br>
                            <button class="btn btn-action btn-turnos" onclick="location.href='turnos.html'">Turnos</button><br>
                            <button class="btn btn-action btn-delete-campana">Borrar</button><br>
                        </td>
                    </tr>`
}

const tbody = document.getElementById("campaign-table-body");
const campanas = fetch_data("campanas").then(render_Rows);

function render_Rows(data) {
    console.log(data);
    data.forEach(element => {
        tbody.insertAdjacentHTML('beforeend', modelo_Fila(element))
    });

    // Inicializar estados
        updateStatus(); 
}

//Estado de la campaña

function updateStatus() {
            const now = new Date();
            document.querySelectorAll('.campaign-row').forEach(row => {
                const start = new Date(row.getAttribute('data-start'));
                const end = new Date(row.getAttribute('data-end'));
                const statusCell = row.querySelector('.status-cell');
                let status = '';
                let label = '';
                let style = '';

                console.log(row)

                if (now < start) {
                    status = 'proximamente';
                    label = 'Próximamente';
                    style = 'background: #fff3cd; color: #856404;';
                } else if (now > end) {
                    status = 'terminada';
                    label = 'Terminada';
                    style = 'background: #f8d7da; color: #721c24;';
                } else {
                    status = 'activa';
                    label = 'Activa';
                    style = 'background: #d4edda; color: #155724;';
                }

                row.setAttribute('data-status', status);
                statusCell.innerHTML = `<span style="${style} padding: 4px 8px; border-radius: 4px; font-size: 0.85rem;">${label}</span>`;
            });
        }

    // Lógica de filtrado
        document.querySelectorAll('.filter-btn').forEach(button => {
            button.addEventListener('click', () => {
                const filter = button.getAttribute('data-filter');
                document.querySelectorAll('.filter-btn').forEach(btn => btn.classList.remove('active'));
                button.classList.add('active');

                document.querySelectorAll('.campaign-row').forEach(row => {
                    const status = row.getAttribute('data-status');
                    row.style.display = (filter === 'all' || status === filter) ? '' : 'none';
                });
            });
        });

        



//console.log(modelo_Fila(campanas[0]));