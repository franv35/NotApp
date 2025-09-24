document.addEventListener('DOMContentLoaded', function () {
  const registerBtn = document.getElementById('registerBtn');

  registerBtn.addEventListener('click', function () {
    const username = document.getElementById('username').value.trim();
    const nombreCompleto = document.getElementById('nombreCompleto').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!username || !nombreCompleto || !password) {
      alert('❗Por favor completá todos los campos.');
      return;
    }

    const userData = { username, nombreCompleto, password };

    fetch('/usuarios/registro', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userData)
    })
      .then(response => {
        if (!response.ok) throw new Error('Error al registrar usuario');
        return response.text();
      })
      .then(data => {
        alert('✅ Registro exitoso');
        window.location.href = 'login.html';
      })
      .catch(error => {
        console.error('Error en registro:', error);
        alert('❌ No se pudo registrar el usuario. Verificá que el nombre de usuario no esté repetido.');
      });
  });
});
