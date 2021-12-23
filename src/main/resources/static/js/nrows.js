
var myArray;
var size_n=null;

function onclick1(index){
    var xhr=new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8082/api/setTictactoe?index="+index, true);
    xhr.responseType='json';
    xhr.send();
    xhr.onreadystatechange=function(){
    	if (xhr.readyState != 4){
    		return;
    	}
    	console.log(xhr);
        if (xhr.status === 200){
        	document.getElementById("alert-warning").style.display="none";
        	var jsonResponse = xhr.response;
            array=jsonResponse.grid;
        }else if(xhr.status==400){
        	document.getElementById("alert-warning").style.display=initial;
        	return;
        }
        updateTictactoe(array);
        if (jsonResponse.winner != null){
        	if (jsonResponse.winner === 1){
        		document.getElementById("result").innerHTML= "Bravo, vous avez gagné !";
        	}else if (jsonResponse.winner === 2){
            	document.getElementById("result").innerHTML= "Dommage, l'ordinateur a gagné !";}
        }else{
        	document.getElementById("result").innerHTML= "";
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

function save(){
    if(size_n==null){
        size_n = document.getElementById("select").value;
        createBoard();
    }else{
        alert("Game already began");
    }
}

function createBoard(){
    const game=document.getElementById("game");
    const tab=document.createElement("table");
    tab.classList.add("test");
    const tbody=document.createElement("tbody");
    if (size_n>=3 && size_n<=11){
        for(let i=0; i<(2*size_n-1); i++){
            var tr=document.createElement("tr");
            for(let j=0; j<(2*size_n-2); j++){ 
                var td=document.createElement("td");
                var button=document.createElement("button");
                button.classList.add("game");
                td.appendChild(button);
                tr.appendChild(td);
                tbody.appendChild(tr);
            }
        }
        tab.appendChild(tbody);
        game.appendChild(tab);
    }

}
