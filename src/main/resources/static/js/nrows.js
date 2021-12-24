
var myArray;
var size_n=null;

function initN(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: '/api/ninarow/init?size='+size_n,
        success: function(data){
            document.getElementById("alert-warning").style.display="none";
            return true;
        },
        error: function(error){
            document.getElementById("alert-warning").style.display=initial;
            console.log(error);
        }
     });
}


function onclick2(index){
    if(initN()){
        var xhr=new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8082/api/ninarow/set?index="+index, true);
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
            updateNinarow(array);
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
}



function updateNinarow(tab){  
    if (size_n != null){
        for(var i=0; i<2*size_n-1; i++){
            for(var j=0; j<2*size_n-2; j++){
                if (tab[i][j]=="1"){
                    document.getElementById(i+""+j).innerHTML="X";
                }else if(tab[i][j]=="1"){
                    document.getElementById(i+""+j).innerHTML="0";
                }else{
                    document.getElementById(i+""+j).innerHTML="";
                }
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
        initN();
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
            for(let j=0; j<(2*size_n-1); j++){ 
                var td=document.createElement("td");
                var div=document.createElement("div");
                if(i<=2*size_n-2){
                    div.classList.add("case");
                    td.appendChild(div);
                    tr.appendChild(td);
                    tbody.appendChild(tr);
                }else{
                    var button=document.createElement("button");
                    var text=document.createTextNode("Play");
                    button.classList.add("play");
                    button.setAttribute("id", j);
                    button.onclick=onclick2();
                    button.appendChild(text);
                    td.appendChild(button);
                    tr.appendChild(td);
                    tbody.appendChild(tr);
                }
            }
        }
        tab.appendChild(tbody);
        game.appendChild(tab);
    }else{
        alert("Fatal error, reload the page");
    }

}
