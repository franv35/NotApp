document.addEventListener('DOMContentLoaded', function () {
  const loginBtn = document.getElementById('loginBtn');

  loginBtn.addEventListener('click', function () {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!username || !password) {
      alert('❗Por favor completá usuario y contraseña.');
      return;
    }

    const loginData = { username, password };

    fetch('/usuarios/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(loginData)
    })
      .then(response => {
        if (!response.ok) throw new Error('Credenciales inválidas');
        return response.json();
      })
      .then(usuario => {
        localStorage.setItem('usuarioLogeado', JSON.stringify(usuario));
        alert(`✅ Bienvenido, ${usuario.nombreCompleto}`);
        window.location.href = 'index.html';
      })
      .catch(error => {
        console.error('Error en login:', error);
        alert('❌ Usuario o contraseña incorrectos.');
      });
  });
});
