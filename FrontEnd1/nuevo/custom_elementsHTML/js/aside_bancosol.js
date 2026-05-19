



const Permisos = {
    1: {
        campanas: true,
        cadena: true,
        tiendas: true,
        colaboradores: true,
        usuarios: true,
        turnos: true,
        bandeja: true
    },
    2: {
        campanas: false,
        cadena: false,
        tiendas: false,
        colaboradores: false,
        usuarios: false,
        turnos: true,
        bandeja: true
    },
    3: {
        campanas: false,
        cadena: false,
        tiendas: true,
        colaboradores: true,
        usuarios: false,
        turnos: true,
        bandeja: true
    }
}

function displayBotones() {
    const usuario = JSON.parse(sessionStorage.getItem('user'));
    console.log(usuario);
    const permisos = Permisos[usuario.id_rol];
    console.log(permisos);

    dashboard.style.display = permisos.dashboard ? 'block' : 'none';
    campanas.style.display = permisos.campanas ? 'block' : 'none';
    cadena.style.display = permisos.cadena ? 'block' : 'none';
    tiendas.style.display = permisos.tiendas ? 'block' : 'none';
    colaboradores.style.display = permisos.colaboradores ? 'block' : 'none';
    usuarios.style.display = permisos.usuarios ? 'block' : 'none';
    turnos.style.display = permisos.turnos ? 'block' : 'none';
    bandeja.style.display = permisos.bandeja ? 'block' : 'none';
}

document.addEventListener("include-html-loaded", () => {
    const dashboard = document.getElementById('dashboard');
    const campanas = document.getElementById('campanas');
    const cadena = document.getElementById('cadena');
    const tiendas = document.getElementById('tiendas');
    const colaboradores = document.getElementById('colaboradores');
    const usuarios = document.getElementById('usuarios');
    const turnos = document.getElementById('turnos');
    const bandeja = document.getElementById('bandeja');
    const logout = document.getElementById('logout-btn');
    logout.addEventListener('click', () => {
        console.log('Cerrar sesión');
        sessionStorage.clear();
        window.location.href = './index.html';
    });
    displayBotones();
    
});
