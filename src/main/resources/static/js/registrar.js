$(document).ready(function(){ /*on ready*/ });

const registrarUsuario = async () => {
let datos = {};
datos.nombre = document.querySelector('#txtNombre').value;
datos.apellido = document.querySelector('#txtApellido').value;
datos.email = document.querySelector('#txtEmail').value;
datos.password = document.querySelector('#txtPassword').value;
datos.telefono = document.querySelector('#txtTelefono').value;
const repPassword = document.querySelector('#txtRepeatPassword').value;

if(datos.password !== repPassword){
alert('Las contraseñas no coinciden');
return;
}

  const request = await fetch('api/usuarios', {
    method: 'POST',
    headers: getHeaders()
    ,body: JSON.stringify(datos)
  });
  const usuarios = await request.json();

  alert('Usuario ' + datos.nombre + ' registrado')

  window.location.href = 'login.html';

}

const getHeaders = () => {
return {
             'Accept': 'application/json',
             'Content-Type': 'application/json',
             'Authorization': localStorage.token
           }
}


