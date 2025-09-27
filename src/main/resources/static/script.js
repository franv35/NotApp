// OBTENER Y MOSTRAR NOTAS EXISTENTES

document.addEventListener('DOMContentLoaded', function () {
  fetchAndDisplayNotes();
  fetchAndDisplayFinishedNotes();
});


document.addEventListener('DOMContentLoaded', function () {
  const usuario = JSON.parse(localStorage.getItem('usuarioLogeado'));
  const nombreHeader = document.getElementById('nombreUsuarioHeader');

  if (usuario && nombreHeader) {
    nombreHeader.textContent = usuario.nombreCompleto;
  }

  // Ya existente:
  fetchAndDisplayNotes();
  fetchAndDisplayFinishedNotes();
});


function fetchAndDisplayNotes() {
  fetch('/notes')
    .then(async response => {
      if (!response.ok) {
        if (response.status === 404 || response.status === 505) {
          return []; // sin notas, no es error
        }
        throw new Error('Error al obtener las notas');
      }

      try {
        return await response.json();
      } catch (e) {
        return []; // si no hay JSON válido, asumimos lista vacía
      }
    })
    .then(notes => {
      const container = document.querySelector('.container');
      container.innerHTML = '';

      if (!notes || notes.length === 0) {
        container.innerHTML = `<p class="no-notes-msg">No hay notas activas</p>`;
        return;
      }

      notes.forEach(note => {
        const noteElement = createNoteElement(note);
        container.appendChild(noteElement);
      });
    })
    .catch(error => {
      console.error('Error al cargar notas:', error);
      // Solo mostrar alert si el error no fue por lista vacía
      if (error.message !== 'Error al obtener las notas') return;
      alert('❌ Ocurrió un problema al cargar las notas. Intenta recargar la página.');
    });
}



function fetchAndDisplayFinishedNotes() {
  fetch('/notes/obtenernotasterminadas')
    .then(response => response.json())
    .then(notes => {
      const container = document.querySelector('.container.terminadas');
      container.innerHTML = '';

      if (!notes || notes.length === 0) {
        container.innerHTML = `<p class="no-notes-msg">No hay notas terminadas</p>`;
        return;
      }

      notes.forEach(note => {
        const noteElement = createNoteElement(note, true);
        container.appendChild(noteElement);
      });
    })
    .catch(error => {
      console.error('Error al cargar notas terminadas:', error);
      alert('❌ No se pudieron cargar las notas terminadas. Intenta recargar la página.');
    });
}


function createNoteElement(note, isFinished = false) {
  const noteElement = document.createElement('div');
  noteElement.classList.add('note');
  noteElement.setAttribute('data-id', note.id);

  noteElement.innerHTML = `
    <div class="notetitle">${note.title}</div>
    <div class="notecontent">${note.contenido}</div>
    <div class="notebottom">
     <div class="notetaggs">
       etiquetas:
       <span class="etiquetas-list">
         ${note.etiquetas?.map(e => `<span class="etiqueta-chip">${e.nombre}</span>`).join('') || ''}
       </span>
       ${!isFinished ? `<input type="text" class="etiqueta-input" placeholder="Agregar etiqueta">` : ''}
     </div>
      <div class="noteoptions">
        <a href="#" class="option edit-btn"><img src="./img/edit.svg" alt="Editar"></a>
        <a href="#" class="option delete-btn"><img src="./img/delete.svg" alt="Eliminar"></a>
        <a href="#" class="option finish-btn"><img src="./img/check.svg" alt="Confirmar"></a>
      </div>
    </div>
  `;


  // 🔧 Acciones para notas activas
  if (!isFinished) {
    const deleteBtn = noteElement.querySelector('.delete-btn');
    const etiquetaInput = noteElement.querySelector('.etiqueta-input');
    if (etiquetaInput) {
      etiquetaInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
          const etiqueta = etiquetaInput.value.trim();
          if (!etiqueta) return;

          const noteId = noteElement.getAttribute('data-id');
          fetch(`/notes/agregaretiqueta/${noteId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(etiqueta)
          })
            .then(res => {
              if (!res.ok) throw new Error('Error al agregar etiqueta');
              return res.json();
            })
            .then(updatedNote => {
              alert('✅ Etiqueta agregada');
              etiquetaInput.value = ''; // limpia el input
              fetchAndDisplayNotes();   // refresca la vista
            })
            .catch(error => {
              console.error('Error al agregar etiqueta:', error);
              alert('❌ No se pudo agregar la etiqueta');
            });
        }
      });
    }
    if (deleteBtn) {
      deleteBtn.addEventListener('click', function (e) {
        e.preventDefault();
        const noteId = noteElement.getAttribute('data-id');
        if (confirm('¿Estás seguro de que querés eliminar esta nota?')) {
          fetch(`/notes/borrar/${noteId}`, { method: 'DELETE' })
            .then(response => {
              if (!response.ok) throw new Error('Error al eliminar la nota');
              return response.text();
            })
            .then(() => {
              alert('✅ Nota eliminada');
              fetchAndDisplayNotes();
              fetchAndDisplayFinishedNotes();
            })
            .catch(error => {
              console.error('Error al eliminar:', error);
              alert('❌ No se pudo eliminar la nota');
            });
        }
      });
    }

    const finishBtn = noteElement.querySelector('.finish-btn');
    if (finishBtn) {
      finishBtn.addEventListener('click', function (e) {
        e.preventDefault();
        const noteId = noteElement.getAttribute('data-id');

        fetch(`/notes/guardarnotaterminada/${noteId}`, { method: 'POST' })
          .then(response => {
            if (!response.ok) throw new Error('Error al marcar como terminada');
            return response.text();
          })
          .then(() => {
            alert('✅ Nota marcada como terminada');

            // 🔄 Obtener la nota actualizada desde backend
            const terminadasContainer = document.querySelector('.container.terminadas');
            const nuevaNotaTerminada = createNoteElement(note, true);
            terminadasContainer.appendChild(nuevaNotaTerminada);
            noteElement.remove(); // elimina la nota activa del DOM
          })
          .catch(error => {
            console.error('Error al marcar como terminada:', error);
            alert('❌ No se pudo marcar la nota como terminada');
          });
      });
    }

    const editBtn = noteElement.querySelector('.edit-btn');
    if (editBtn) {
      editBtn.addEventListener('click', function (e) {
        e.preventDefault();
        const noteId = noteElement.getAttribute('data-id');
        const currentTitle = noteElement.querySelector('.notetitle').innerText;
        const currentContent = noteElement.querySelector('.notecontent').innerText;

        document.getElementById('modalNoteTitle').value = currentTitle;
        document.getElementById('modalNoteContent').value = currentContent;
        document.getElementById('noteModal').style.display = 'block';
        document.getElementById('saveNoteBtn').dataset.editing = noteId;
      });
    }
  }

  // 🔧 Acciones para notas terminadas
  if (isFinished) {
    const deleteBtn = noteElement.querySelector('.delete-terminada-btn');
    if (deleteBtn) {
      deleteBtn.addEventListener('click', function (e) {
        e.preventDefault();
        const noteId = noteElement.getAttribute('data-id');
        if (confirm('¿Eliminar esta nota terminada?')) {
          fetch(`/notes/borrarterminada/${noteId}`, { method: 'DELETE' })
            .then(response => {
              if (!response.ok) throw new Error('Error al eliminar nota terminada');
              return response.text();
            })
            .then(() => {
              alert('✅ Nota terminada eliminada');
              fetchAndDisplayFinishedNotes();
            })
            .catch(error => {
              console.error('Error al eliminar terminada:', error);
              alert('❌ No se pudo eliminar la nota terminada');
            });
        }
      });
    }
  }

  return noteElement;
}


// APARICIÓN DE VENTANA EMERGENTE (MODAL)

document.addEventListener('DOMContentLoaded', function () {
  const modal = document.getElementById('noteModal');
  const openBtn = document.getElementById('openModal');
  const closeBtn = document.getElementById('closeModal');
  const saveBtn = document.getElementById('saveNoteBtn');

  openBtn.addEventListener('click', function () {
    modal.style.display = 'flex'; // ahora sí aparece
    document.getElementById('modalNoteTitle').value = '';
    document.getElementById('modalNoteContent').value = '';
    delete saveBtn.dataset.editing;
  });


  closeBtn.addEventListener('click', function () {
    modal.style.display = 'none';
    delete saveBtn.dataset.editing;
  });

  window.addEventListener('click', function (event) {
    if (event.target === modal) {
      modal.style.display = 'none';
      delete saveBtn.dataset.editing;
    }
  });


  saveBtn.addEventListener('click', function () {
    const title = document.getElementById('modalNoteTitle').value;
    const content = document.getElementById('modalNoteContent').value;
    const noteId = saveBtn.dataset.editing;

    if (!title || !content) {
      alert('❗Por favor completá el título y contenido.');
      return;
    }

    if (noteId) {
      // Modo edición
      Promise.all([
        fetch('/notes/editartitle', {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ id: parseInt(noteId), title: title })
        }),
        fetch('/notes/editarcontenido', {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ id: parseInt(noteId), content: content })
        })
      ])
        .then(responses => {
          if (responses.some(res => !res.ok)) {
            throw new Error('Uno o más cambios no fueron aplicados');
          }
          alert('✅ Nota actualizada');
          modal.style.display = 'none';
          delete saveBtn.dataset.editing;
          fetchAndDisplayNotes();
        })
        .catch(error => {
          console.error('Error al editar nota:', error);
          alert('❌ No se pudo editar la nota');
        });

    } else {
      // Modo creación
      const noteData = { title, contenido: content };

      fetch('/notes/crear', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(noteData)
      })
        .then(response => {
          if (!response.ok) {
            throw new Error('Error al guardar la nota');
          }
          return response.text();
        })
        .then(data => {
          alert('✅ Nota guardada con éxito');
          modal.style.display = 'none';
          fetchAndDisplayNotes();
        })
        .catch(error => {
          console.error('Error al guardar la nota:', error);
          alert('❌ Hubo un problema al guardar la nota. Intenta de nuevo.');
        });
    }
  });
});
