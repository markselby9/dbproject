// JavaScript Document
function check_account(name){
	if(name==""){
		return false;
	}else{
		return true;
	}
}
function check_key(key){
	if(key==""){
		return false;
	}else{
		return true;
	}
}
function login(){
	document.getElementById("button").style.backgroundImage="images/loginBackground2.png";
	var account = document.getElementById("account").value;
	var key = document.getElementById("key").value;
	if(!check_account(account)){
		alert("�������˺�");
	}else if(!check_key(key)){
		alert("����������");
	}
	else{
		document.logform.submit();
	}
}