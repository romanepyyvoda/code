/*=========================
        HIDE CONTENT
=========================*/
var hiddenContentBuffer;//global variable 
var hiddenContentBBuffer;//global variable 
var beerBuffer;//global variable 

function showNhide(beerID, hiddenContentID, hiddenContentIDb){
            
            var hiddenContent = document.getElementById(hiddenContentID);
			var hiddenContentB = document.getElementById(hiddenContentIDb);
			var beer=document.getElementById(beerID)
			
			if(hiddenContentBuffer == null){
				hiddenContentBuffer = document.getElementById('hiddenContent1');
				hiddenContentBBuffer = document.getElementById('hiddenContent1B');
			}            
			if(hiddenContent.style.display == "none" && hiddenContentBuffer.style.display == "block"){
				hiddenContentBuffer.style.display = "none";
				hiddenContentBBuffer.style.display = "block";
				beerBuffer.style.transform="rotate(0deg)";
			}
            if(hiddenContent.style.display == "none"){
                hiddenContent.style.display = "block";
				hiddenContentB.style.display = "none";
				beer.style.transform="rotate(135deg)";
				
				hiddenContentBuffer = hiddenContent;
				hiddenContentBBuffer = hiddenContentB;
				beerBuffer = beer;
			}else {
                hiddenContent.style.display = "none";
				hiddenContentB.style.display = "block";
				beer.style.transform="rotate(0deg)";
			}
}


/*=========================
        OCTOPUS
=========================*/
window.onscroll = function() {rotateOctopus()};

    function rotateOctopus() {
      var winScroll = document.body.scrollTop || document.documentElement.scrollTop;
      var height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
      var scrolled = (winScroll / height)*150;
	  
      document.getElementById("rotateOctopus").style.transform = "rotate("+ scrolled + "deg)";
    } 

	
	
/*=========================
        MODAL AGE CHECKER
=========================*/
function Submit(){
	
	var fmonth = document.form.birthday_month.value,
		fday = document.form.birthday_day.value,
		fyear = document.form.birthday_year.value;
		modal=document.getElementById('bg-modal');
	
    var age = 21;
    var curdate = new Date();
   
	
	 if (fmonth == "") {
        document.form.birthday_month.focus();
		document.getElementById("errorBox").innerHTML = "Please, select the birthday month";
        return false;
     }
      
	 if (fday == "") {
        document.form.birthday_day.focus();
		document.getElementById("errorBox").innerHTML = "Please, select the birthday day";
        return false;
     }
      
	 if (fyear == "") {
        document.form.birthday_year.focus();
		document.getElementById("errorBox").innerHTML = "Please, select the birthday year";
        return false;
     }
      
     if((curdate.getFullYear() - fyear) <= 21){
			document.getElementById("errorBox").innerHTML = "Sorry, only persons over the age of 21 may enter this site";
		
     }
      
     if((curdate.getFullYear() - fyear) >= 21){
			modal.style.display = 'none';
			document.getElementById('modal-open').style.overflow = 'scroll';
     }
  		  
}

/*=========================
        SCROLL RESET
=========================*/
window.onbeforeunload = function () {
  window.scrollTo(0, 0);
}