document.addEventListener('DOMContentLoaded', () => {
  const registerBtn = document.getElementById('registerBtn');

  registerBtn.addEventListener('click', async () => {
    const username = document.getElementById('regUsername').value.trim();
    const nombreCompleto = document.getElementById('regNombreCompleto').value.trim();
    const password = document.getElementById('regPassword').value.trim();
    const email = document.getElementById('regEmail').value.trim();

    // Validación simple
    if (!username || !nombreCompleto || !password || !email) {
      mostrarMensaje('❗Por favor completá todos los campos.', 'error');
      return;
    }

    registerBtn.disabled = true;
    registerBtn.textContent = 'Registrando...';

    const userData = { username, nombreCompleto, password, email };

    try {
      const response = await fetch('/usuarios/registro', { // ✅ endpoint correcto
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
