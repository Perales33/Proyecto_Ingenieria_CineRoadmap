document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector('form');

    form.addEventListener('submit', function(event) {
        event.preventDefault(); // Evitar que se envíe el formulario por defecto

        // Expresiones regulares para validar los campos
        const usuarioExpreg = /^[a-zA-Z0-9._-]+$/;
        const nickExpreg = /^[a-zA-Z0-9._-]+$/;
        const emailExpreg = /^[^\s@ñ]+@[^\s@ñ]+\.[^\s@ñ]+$/;
        const telefonoExpreg = /^\d{6,12}$/;
        const passwordExpreg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d!"·$%&/()=?'¿¡*+^`Çªº]+$/;

        // Obtener los valores de los campos
        const usuarioField = form.querySelector('input[placeholder="Usuario"]');
        const nickField = form.querySelector('input[placeholder="Nick"]');
        const emailField = form.querySelector('input[placeholder="Correo electrónico"]');
        const telefonoField = form.querySelector('input[placeholder="Teléfono"]');
        const passwordField = form.querySelector('input[placeholder="Contraseña"]');
        const confirmPasswordField = form.querySelector('input[placeholder="Repita la contraseña"]');

        const usuario = usuarioField.value.trim();
        const nick = nickField.value.trim();
        const email = emailField.value.trim();
        const telefono = telefonoField.value.trim();
        const password = passwordField.value.trim();
        const confirmPassword = confirmPasswordField.value.trim();

        // Validar los campos y aplicar estilos si es necesario
        let valid = true;
        if (!usuarioExpreg.test(usuario)) {
            valid = false;
            usuarioField.style.borderColor = 'red';
        } else {
            usuarioField.style.borderColor = '';
        }
        if (!nickExpreg.test(nick) || nick === usuario) {
            valid = false;
            nickField.style.borderColor = 'red';
        } else {
            nickField.style.borderColor = '';
        }
        if (!emailExpreg.test(email)) {
            valid = false;
            emailField.style.borderColor = 'red';
        } else {
            emailField.style.borderColor = '';
        }
        if (!telefonoExpreg.test(telefono)) {
            valid = false;
            telefonoField.style.borderColor = 'red';
        } else {
            telefonoField.style.borderColor = '';
        }
        if (!passwordExpreg.test(password)) {
            valid = false;
            passwordField.style.borderColor = 'red';
        } else {
            passwordField.style.borderColor = '';
        }
        if (password !== confirmPassword || confirmPassword === '' || !passwordExpreg.test(confirmPassword)) {
            valid = false;
            confirmPasswordField.style.borderColor = 'red';
        } else {
            confirmPasswordField.style.borderColor = '';
        }

        // Mostrar alerta si hay campos inválidos
        if (!valid) {
            let errorMessage = 'Por favor, corrige los siguientes campos incorrectos:\n\n';
            if (!usuarioExpreg.test(usuario)) {
                errorMessage += '- Usuario: solo letras y números\n';
            }
            if (!nickExpreg.test(nick) || nick === usuario) {
                errorMessage += '- Nick: igual que el usuario, pero  permite los caracteres ".", "_" y "-" \n';
            }
            if (!emailExpreg.test(email)) {
                errorMessage += '- Correo electrónico: formato válido (xxx@xxx.xxx).\n';
            }
            if (!telefonoExpreg.test(telefono)) {
                errorMessage += '- Teléfono: entre 6 y 12 dígitos.\n';
            }
            if (!passwordExpreg.test(password)) {
                errorMessage += '- Contraseña: al menos una mayúscula, una minúscula y un número.\n';
            }
            if (password !== confirmPassword || confirmPassword === '' || !passwordExpreg.test(confirmPassword)) {
                errorMessage += '- Repita la contraseña: debe coincidir exactamente con la contraseña y cumplir con los mismos requisitos.\n';
            }
            alert(errorMessage);
        } else {
            alert('¡Formulario enviado correctamente!');
            // Aquí podrías enviar el formulario si todos los campos son válidos
            // form.submit();
        }
    });
});



