// JavaScript Document
function check(){
	var key1 = document.getElementById("new").value;
	var key2 = document.getElementById("confirm").value;
	var html = document.getElementById("alert");
	if(key1 == key2){
		alert("a");
		document.modform.submit();
	}else{
		alert("b");
		html.innerHTML = "    Not cohenrent! Please input again!";
	}
}