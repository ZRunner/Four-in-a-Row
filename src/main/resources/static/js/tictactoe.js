
var myArray;

function onclick1(index){
    var response;
    var object;
    var xhr=new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8082/api/tictactoe/set?index="+index, true);
    xhr.responseType='json';
    xhr.send();
    xhr.onreadystatechange=function(){
    	if (xhr.readyState != 4){
    		return;
    	}
    	console.log(xhr);
        if (xhr.status === 200){
        	var jsonResponse = xhr.response;
            array=jsonResponse.grid;
        }else if(xhr.status==400){
        	alert("T'es nul et moche, cheh !");
        	return;
        }
        updateTictactoe(array);
        if (jsonResponse.winner != null){
        	document.getElementById("result").innerHTML= jsonResponse.winner+" a gagné !";
        }
    }
}





function updateTictactoe(tab){  
    if (tab.length == 9){
        for ( var ind=0; ind<9; ind++){
            if (tab[ind]=="0"){
                document.getElementById(ind).innerHTML="";
            }else if(tab[ind]=="1"){
                document.getElementById(ind).innerHTML="X";
            }else{
                document.getElementById(ind).innerHTML="O";
            }
        }
    }else{
        alert("Erreur lors de la mise à jour du jeu, veuillez rafraichir la page")
    }

}


