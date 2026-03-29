/* 1. INTERACTIVIDAD VISUAL: EFECTO FLIP */
// Nota: Asegúrate de que en tu PHP las cards tengan la clase "card-pelicula"
document.querySelectorAll(".card-pelicula").forEach(function (elemento) {
    elemento.addEventListener('click', function () {
        this.classList.toggle("animacionFlip");
    });
});

/* 2. BÚSQUEDA INTELIGENTE (Auto-submit) */
document.addEventListener("DOMContentLoaded", function() {
    const formulario = document.querySelector(".filtros-form");
    const inputFiltro = document.querySelector(".input-busqueda");

    if (inputFiltro && formulario) {
        let timeout = null;
        inputFiltro.addEventListener('keyup', function() {
            clearTimeout(timeout);
            timeout = setTimeout(() => {
                if (this.value.length >= 3 || this.value.length === 0) {
                    formulario.submit();
                }
            }, 800); 
        });
    }
});

/* 3. VALORACIONES POR AJAX (Sin recargar página) */
document.querySelectorAll('.btnEstrella').forEach(btn => {
    btn.addEventListener('click', function(e) {
        e.preventDefault();
        let form = this.closest('form');
        let formData = new FormData(form);
        formData.append('rating', this.value);

        fetch('guardarValoracion.php', {
            method: 'POST',
            body: formData
        })
        .then(response => response.text())
        .then(data => {
            alert('¡Gracias por tu valoración!');
            // Aquí podrías añadir lógica para iluminar las estrellas fijas
        });
    });
});


/* 4. Perfil de Usuario: Edición en Modal (Ejemplo básico) */
document.addEventListener('DOMContentLoaded', () => {
    // 1. Inicializar Gráfica si existen los datos en el objeto global
    if (window.datosGrafica) {
        new Chart(document.getElementById('graficaVistas'), {
            type: 'line',
            data: {
                labels: window.datosGrafica.meses,
                datasets: [{ 
                    data: window.datosGrafica.cantidades, 
                    borderColor: '#e2b616', 
                    tension: 0.4, 
                    fill: true, 
                    backgroundColor: 'rgba(226, 182, 22, 0.1)' 
                }]
            },
            options: { 
                responsive: true, 
                maintainAspectRatio: false, 
                plugins: { legend: { display: false } } 
            }
        });
    }

    // 2. Lógica de Logros
    const LOGROS_POR_PAGINA = 4;
    let paginaActual = 1;
    let logrosFiltrados = [...window.todosLosLogros];

    const contenedor = document.getElementById('contenedorLogros');
    const paginador = document.getElementById('paginador');
    const buscador = document.getElementById('busquedaLogro');

    function mostrarLogros() {
        contenedor.innerHTML = "";
        if (logrosFiltrados.length === 0) {
            contenedor.innerHTML = "<p style='grid-column: 1/-1; text-align: center; color: #666;'>No se encontraron resultados.</p>";
            paginador.innerHTML = "";
            return;
        }

        const inicio = (paginaActual - 1) * LOGROS_POR_PAGINA;
        const sublista = logrosFiltrados.slice(inicio, inicio + LOGROS_POR_PAGINA);

        sublista.forEach(logro => {
            contenedor.innerHTML += `
                <div class="logro-card">
                    <img src="${logro.img}" class="logro-img" draggable="false">
                    <div class="logro-txt">
                        <h3>${logro.nombre}</h3>
                        <p>${logro.desc}</p>
                        <div style="margin-top: 5px; color: #e2b616; font-size: 0.7rem;">✓ COMPLETADO</div>
                    </div>
                </div>`;
        });
        crearPaginador();
    }

    function crearPaginador() {
        const numPaginas = Math.ceil(logrosFiltrados.length / LOGROS_POR_PAGINA);
        paginador.innerHTML = "";
        if (numPaginas <= 1) return;

        for (let i = 1; i <= numPaginas; i++) {
            const btn = document.createElement('button');
            btn.innerText = i;
            btn.className = `btn-pag ${i === paginaActual ? 'active' : ''}`;
            btn.onclick = () => { 
                paginaActual = i; 
                mostrarLogros(); 
                window.scrollTo({top: contenedor.offsetTop - 100, behavior: 'smooth'}); 
            };
            paginador.appendChild(btn);
        }
    }

    window.iniciarFiltrado = () => {
        const busqueda = buscador.value.toLowerCase();
        logrosFiltrados = window.todosLosLogros.filter(l => 
            l.nombre.toLowerCase().includes(busqueda) || 
            l.desc.toLowerCase().includes(busqueda)
        );
        paginaActual = 1;
        mostrarLogros();
    };

    mostrarLogros();
});