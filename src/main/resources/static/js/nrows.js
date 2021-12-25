
var myArray;
var size_n=null;

function initN(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: '/api/ninarow/init?size='+size_n,
        success: function(data){
            document.getElementById("alert-warning").style.display="none";
            return;
        },
        error: function(error){
            console.log(error);
        }
     });
}


function onclick2(index){
    var xhr=new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8082/api/ninarow/set?index="+index, true);
    xhr.responseType='json';
    xhr.send();
    xhr.onreadystatechange=function(){
        if (xhr.readyState != 4){
            return;
        }
        if (xhr.status === 200){
            document.getElementById("alert-warning").style.display="none";
            var jsonResponse = xhr.response;
            array=jsonResponse.grid;
        }
        updateNinarow(array);
        if (jsonResponse.winner != null){
            if (jsonResponse.winner === 1){
                document.getElementById("result").innerHTML= "<div class=\"modal fade\" id=\"exampleModal\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><h5 class=\"modal-title\" id=\"exampleModalLabel\">Modal title</h5><button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button></div><div class=\"modal-body\">\"Félicitation, vous avez gagné !\"</div><div class=\"modal-footer\"><button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button><button type=\"button\" class=\"btn btn-primary\">Save changes</button></div></div></div></div>";
            }else if (jsonResponse.winner === 2){
                document.getElementById("result").innerHTML= "<div class=\"modal fade\" id=\"exampleModal\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><h5 class=\"modal-title\" id=\"exampleModalLabel\">Modal title</h5><button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button></div><div class=\"modal-body\">\"Dommage, vous avez perdu !\"</div><div class=\"modal-footer\"><button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button><button type=\"button\" class=\"btn btn-primary\">Save changes</button></div></div></div></div>";}
        }else{
            document.getElementById("result").innerHTML= "";
        }
    }
}



function updateNinarow(tab){  
    if (size_n != null){
        for(var i=0; i<2*size_n-1; i++){
            for(var j=0; j<2*size_n-2; j++){
                if (tab[i][j]=="1"){
                    document.getElementById(i+""+j).innerHTML="<img src=\"img/cross.png\" style=\"width: 100%; height: 100%;\" alt=\"cross\">";
                }else if(tab[i][j]=="2"){
                    document.getElementById(i+""+j).innerHTML="<img src=\"img/circle.png\" style=\"width: 100%; height: 100%;\" alt=\"cross\">";
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
        createBoard(size_n);
        initN();
    }else{
        alert("Game already began");
    }
}

function createBoard(size_n){
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
                var width;
                if(i<=2*size_n-3){
                    if(size_n<5){
                        width=100;
                        div.setAttribute("id",j+""+i);
                        div.style.cssText="width:"+ width+"px; height:"+width+"px; border-style: solid; border-width: 1px;";
                        td.style.cssText="height:100%; width:"+width+"px;";
                        td.appendChild(div);
                        tr.appendChild(td);
                        tbody.appendChild(tr);
                    }else{
                        width=75;
                        div.classList.add("case");
                        div.setAttribute("id",j+""+i);
                        div.style.cssText="width:"+width+"px; height:"+width+"px; border-style: solid; border-width: 1px;";
                        td.style.cssText="height:100%; width:"+width+"px;";
                        td.appendChild(div);
                        tr.appendChild(td);
                        tbody.appendChild(tr);
                    }
                }else if(i==2*size_n-2){
                	var div=document.createElement("div");
                    var button=document.createElement("button");
                    var text=document.createTextNode("Play");
                    div.style.cssText="width: "+width+"px;";
                    button.classList.add("play");
                    button.style.cssText="width:"+width+"px; text-align; center; margin-top: 20px; margin-left: auto; margin-right: auto;";
                    button.appendChild(text);
                    button.onclick=function(){onclick2(j);};
                    div.appendChild(button);
                    td.appendChild(div);
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

