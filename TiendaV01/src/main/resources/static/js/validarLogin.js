
function validar(){
    var passw1 = document.getElementById("clave");
    var passw2 = document.getElementById("floatingPassword2");

    if(passw1.value === passw2.value){
		return true;
    }else{
		alert("las contraseñas no son iguales");
		        return false;

    }
}