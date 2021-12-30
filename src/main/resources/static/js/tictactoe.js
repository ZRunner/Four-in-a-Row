
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
        if (xhr.status === 200){
        	var jsonResponse = xhr.response;
            array=jsonResponse.grid;
        }else if(xhr.status==400){
        	return;
        }
        updateTictactoe(array);
        if (jsonResponse.winner != null){
            switch (jsonResponse.winner){
                case 0:
                    document.getElementById("result").innerHTML="Partie nulle !";
                    break;
                case 1:
                    document.getElementById("result").innerHTML="Félicitation, vous avez gagné !";
                    break;
                default:
                    document.getElementById("result").innerHTML="Dommage, vous avez perdu !";
                    break;
            }
            $('#modal').modal('show');
        }
    }
}





function updateTictactoe(tab){  
    if (tab.length == 9){
        for (var ind=0; ind<9; ind++){
            if (tab[ind]=="0"){
                document.getElementById(ind).innerHTML="";
            }else if(tab[ind]=="1"){
                document.getElementById(ind).innerHTML="<img src=\"img/cross.png\" style=\"width: 100%; height: 100%;\" alt=\"cross\">";
            }else{
                document.getElementById(ind).innerHTML="<img src=\"img/circle.png\" style=\"width: 100%; height: 100%;\" alt=\"cross\">";
            }
        }
    }else{
        alert("Erreur lors de la mise à jour du jeu, veuillez rafraichir la page")
    }

}


function reinit(){
    myArray=null;
    for(var i=0; i<9; i++){
        document.getElementById(i).innerHTML="";
    }
}