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
