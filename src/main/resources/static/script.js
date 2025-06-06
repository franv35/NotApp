//OBTENER Y MOSTRAR NOTAS EXISTENTES

document.addEventListener('DOMContentLoaded', function () {
  // Llama a la función al cargar la página
  fetchAndDisplayNotes();
});

function fetchAndDisplayNotes() {
  fetch('/notes')
    .then(response => {
      if (!response.ok) {
		if(response.status==505){
			throw new Error('No hay notas guardadas');
		};
        throw new Error('Error al obtener las notas');
      }
      return response.json();
    })
    .then(notes => {
      const container = document.querySelector('.container');
      container.innerHTML = ''; // Limpiar contenido previo

      notes.forEach(note => {
        const noteElement = document.createElement('div'); //Creo un div por cada nota
        noteElement.classList.add('note'); //Agrego la clase note por cada div creado
        noteElement.setAttribute('data-id', note.id);

        noteElement.innerHTML = `
          <div class="notetitle">${note.title}</div>
          <div class="notecontent">${note.contenido}</div>
          <div class="notebottom">
            <div class="notetaggs">etiquetas:</div>
            <div class="noteoptions">
              <a href="#" class="option"><img src="./img/edit.svg" alt="Editar"></a>
              <a href="#" class="option delete-btn"><img src="./img/delete.svg" alt="Eliminar"></a>
              <a href="#" class="option"><img src="./img/check.svg" alt="Confirmar"></a>
            </div>
          </div>
        `;
        // Agregar listener al botón de eliminar
        const deleteBtn = noteElement.querySelector('.delete-btn');
                if (deleteBtn) {
                  deleteBtn.addEventListener('click', function (e) {
                    e.preventDefault();
                    const noteId = noteElement.getAttribute('data-id');
                    if (confirm('¿Estás seguro de que querés eliminar esta nota?')) {
                      fetch(`/notes/borrar/${noteId}`, {
                        method: 'DELETE'
                      })
                        .then(response => {
                          if (!response.ok) {
                            throw new Error('Error al eliminar la nota');
                          }
                          return response.text();
                        })
                        .then(data => {
                          alert('✅ Nota eliminada');
                          fetchAndDisplayNotes();
                        })
                        .catch(error => {
                          console.error('Error al eliminar:', error);
                          alert('❌ No se pudo eliminar la nota');
                        });
                      }
                    });
                  }
                  container.appendChild(noteElement);
                });
        })
    .catch(error => {
      console.error('Error:', error);
      alert('❌ No se pudieron cargar las notas');
    });
}


// APARICION DE VENTANA EMERGENTE 

// Esperamos a que todo el HTML esté cargado antes de acceder al DOM
document.addEventListener('DOMContentLoaded', function () {

  // Obtenemos referencias a los elementos del DOM
  const modal = document.getElementById('noteModal');
  const openBtn = document.getElementById('openModal');
  const closeBtn = document.getElementById('closeModal');
  const saveBtn = document.getElementById('saveNoteBtn');

  // Al hacer clic en el botón principal, mostramos el modal
  openBtn.addEventListener('click', function () {
    modal.style.display = 'block';
  });

  // Al hacer clic en la "X", cerramos el modal
  closeBtn.addEventListener('click', function () {
    modal.style.display = 'none';
  });

  // Si el usuario hace clic fuera del contenido del modal, también se cierra
  window.addEventListener('click', function (event) {
    if (event.target === modal) {
      modal.style.display = 'none';
    }
  });

  // Función que se ejecuta al hacer clic en "Guardar"
  saveBtn.addEventListener('click', function () {
    // Obtenemos el contenido de los campos
    const title = document.getElementById('modalNoteTitle').value;
    const content = document.getElementById('modalNoteContent').value;

    // Mostramos los datos por consola (acá podrías usar fetch() para enviarlos al servidor)
    console.log('Nota guardada desde modal:', title, content);

    // Limpiamos los campos
    document.getElementById('modalNoteTitle').value = '';
    document.getElementById('modalNoteContent').value = '';

    // Cerramos el modal
    modal.style.display = 'none';
  });

});

//GUARDADO DE NOTAS

function saveModalNote() {
  // Obtenemos los valores de los campos del modal
  const titulo = document.getElementById('modalNoteTitle').value;
  const content = document.getElementById('modalNoteContent').value;

  // Creamos el objeto que vamos a enviar al backend
  const noteData = {
    title: titulo,
    contenido: content
  };

  // Enviamos los datos al backend usando fetch y método POST
  fetch('/notes/crear', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(noteData)
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Error al guardar la nota');
    }
    return response.text();
  })
  .then(data => {
    console.log('Nota creada correctamente:', data);

    // Limpiamos los campos del modal
    document.getElementById('modalNoteTitle').value = '';
    document.getElementById('modalNoteContent').value = '';

    // Cerramos el modal
    document.getElementById('noteModal').style.display = 'none';

    // Mostrar mensaje al usuario
    alert('✅ Nota guardada con éxito');
    //Recarga página
    location.reload();
  })
  .catch(error => {
    console.error('Error al guardar la nota:', error);
    alert('❌ Hubo un problema al guardar la nota. Intenta de nuevo.');
  });
}



//ELIMINAR NOTAS