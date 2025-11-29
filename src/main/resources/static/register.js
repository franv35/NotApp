document.addEventListener('DOMContentLoaded', () => {
  const registerBtn = document.getElementById('registerBtn');

  registerBtn.addEventListener('click', async () => {
    const username = document.getElementById('username').value.trim();
    const nombreCompleto = document.getElementById('nombreCompleto').value.trim();
    const password = document.getElementById('password').value.trim();

    // Validación simple
    if (!username || !nombreCompleto || !password) {
      mostrarMensaje('❗Por favor completá todos los campos.', 'error');
      return;
    }

    // Desactivar botón mientras se procesa
    registerBtn.disabled = true;
    registerBtn.textContent = 'Registrando...';

    const userData = { username, nombreCompleto, password };

    try {
      const response = await fetch('/usuarios/registro', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userData)
      });

      if (!response.ok) {
        throw new Error('No se pudo registrar el usuario.');
      }

      mostrarMensaje('✅ Registro exitoso. Redirigiendo...', 'success');

      setTimeout(() => {
        window.location.href = 'login.html';
      }, 800);

    } catch (error) {
      console.error('Error en registro:', error);
      mostrarMensaje('❌ El usuario ya existe o ocurrió un error.', 'error');

    } finally {
      registerBtn.disabled = false;
      registerBtn.textContent = 'Registrarse';
    }
  });
});


function mostrarMensaje(texto, tipo) {
  let msg = document.getElementById('mensajeRegistro');

  // Crear contenedor si no existe
  if (!msg) {
    msg = document.createElement('div');
    msg.id = 'mensajeRegistro';
    msg.style.marginTop = '10px';
    msg.style.padding = '10px';
    msg.style.borderRadius = '5px';
    msg.style.fontWeight = 'bold';
    document.body.appendChild(msg);
  }

  msg.textContent = texto;

  if (tipo === 'error') {
    msg.style.background = '#ffdddd';
    msg.style.color = '#b30000';
    msg.style.border = '1px solid #b30000';
  } else {
    msg.style.background = '#ddffdd';
    msg.style.color = '#006600';
    msg.style.border = '1px solid #006600';
  }
}

