

// Call the dataTables jQuery plugin
$(document).ready(function(){ init(); });

const init = ()=>{
                 //selecciona la tabla y hace que la tabla sea para paginar y otras cosas ..
                 cargarUsuarios();
                 setEmailUserOnView ();
                 setTimeout(function(){
                   $('#usuarios').DataTable();
                   changeWords();
                 },200)

                 }

const setEmailUserOnView = () => {
document.getElementById('emailNavbar').innerText = localStorage.email;
}

const cargarUsuarios= async ()=>{

  const request = await fetch('api/usuarios', {
    method: 'GET',
    headers: getHeaders()
    //,body: JSON.stringify({a: 1, b: 'Textual content'})
  });
  const usuarios = await request.json();
let tbody = document.querySelector('#usuarios tbody');
//let btnDelete = '<a href="/api/usuarios/"'++ 'class="btn btn-danger btn-circle">';
tbody.innerHTML='';
usuarios.forEach(user=>{
let btnDelete = '<a href="#" onclick ="eliminarUsuario('+user.id+')" class="btn btn-danger btn-circle">';
tbody.innerHTML += `<tr>
                    <td>${user.id}</td>
                    <td>${user.nombre} ${user.apellido}</td>
                    <td>${user.email}</td>
                    <td>${user.telefono}</td>
                    <td>
                    <button class="btn btn-success">Editar</button>
                    ${btnDelete}
                    <i class="fas fa-trash"></i>
                    </a></td>
                    </tr>`;

});
 // console.log(userTable);

}

const eliminarUsuario = async (id)=>{
console.log(id);
let c = confirm('Eliminar usuario con id '+id+'?');
if(!c){return;}
const request = await fetch('api/usuarios/'+id, {
    method: 'DELETE',
    headers: getHeaders()
    //,body: JSON.stringify({a: 1, b: 'Textual content'})
  });
 // const response = await request.json();
  //console.log(request);
 // init();
 location.reload();
 changeWords();
}

const getHeaders = () => {
return {
             'Accept': 'application/json',
             'Content-Type': 'application/json',
             'Authorization': localStorage.token
           }
}

const changeWords = ()=>{
let lengthS = document.querySelector('#usuarios_length label')
        .innerHTML.replace('Show', 'Mostrar')
                  .replace('entries','registros');
document.querySelector('#usuarios_length label').innerHTML = lengthS;

let filterS = document.querySelector('#usuarios_filter label')
                    .innerHTML.replace('Search', 'Buscar');
            document.querySelector('#usuarios_filter label').innerHTML = filterS;


 let infoS = document.querySelector('#usuarios_info')
                     .innerHTML.replace('Showing', 'Mostrando de')
                     .replace('of', 'de')
                     .replace('to', 'a')
                     .replace('entries', 'registros');
             document.querySelector('#usuarios_info').innerHTML = infoS;
}