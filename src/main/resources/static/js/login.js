$(document).ready(function(){ /*on ready*/ });

const login = async () => {
let datos = {};
datos.email = document.querySelector('#txtEmail').value;
datos.password = document.querySelector('#txtPassword').value;

  const request = await fetch('api/login', {
    method: 'POST',
    headers: getHeaders()
    ,body: JSON.stringify(datos)
  });
  const response = await request.text();
  console.log('Response '+response);
  if("FAIL" !== response){
  localStorage.token = response;
  localStorage.email = datos.email;
  //vaya tip para redirigir a otra página :o
  window.location.href = 'usuarios.html';
  }else{
  alert('Credenciales incorrectas. Intente nuevamente');
  }
}

const getHeaders = () => {
return {
             'Accept': 'application/json',
             'Content-Type': 'application/json',
             'Authorization': localStorage.token
           }
}