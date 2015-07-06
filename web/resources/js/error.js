//Recupero la lista degli errori
var errorMessagesList = document.getElementById("frmError:frmErrorMsgError");

//Nascondo la lista degli errori
errorMessagesList.style.visibility = "hidden";

//Se ho degli errori
if (errorMessagesList != null) {
	
	//Recupero gli elementi della lista
	var errorMessages = errorMessagesList.childNodes;
	
	var totErrorMessage = '';

	//Scorro la lista e creo un' unica stringa di errore
	for (i = 0; i < errorMessages.length; i++)
		totErrorMessage += (i + 1) + ')' + errorMessages.item(i).innerHTML
				+ '\n';
	
	//Se la lista contiene errori stampo l'alert
	if (errorMessages.length > 0)
		alert(totErrorMessage);
}
