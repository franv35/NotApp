document.addEventListener('DOMContentLoaded', () => {
  const loginBtn = document.getElementById('loginBtn');

  loginBtn.addEventListener('click', async () => {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!username || !password) {
      mostrarMensaje('❗Por favor completá usuario y contraseña.', 'error');
      return;
    }

    // Desactivar botón mientras se procesa
    loginBtn.disabled = true;
    loginBtn.textContent = 'Ingresando...';

    try {
      const response = await fetch('/usuarios/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });

      if (!response.ok) {
        if (response.status === 401) {
          throw new Error('Usuario o contraseña incorrectos.');
        }
        throw new Error('Error en el servidor. Intentalo más tarde.');
      }

      const usuario = await response.json();

      // Guardar usuario en localStorage
      localStorage.setItem('usuarioLogeado', JSON.stringify(usuario));

      mostrarMensaje(`✅ Bienvenido, ${usuario.nombreCompleto}`, 'success');

      // Redirige después de un pequeño delay para mostrar el mensaje
      setTimeout(() => {
        window.location.href = 'index.html';
      }, 700);

    } catch (error) {
      console.error('Error en login:', error);
      mostrarMensaje(`❌ ${error.message}`, 'error');

    } finally {
      loginBtn.disabled = false;
      loginBtn.textContent = 'Ingresar';
    }
  });
});

// Función para mensajes lindos
function mostrarMensaje(texto, tipo) {
  let msg = document.getElementById('mensajeLogin');

  if (!msg) {
    msg = document.createElement('div');
    msg.id = 'mensajeLogin';
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

 