// This is a manifest file that'll be compiled into application.js, which will include all the files
// listed below.
//
// Any JavaScript/Coffee file within this directory, lib/assets/javascripts, vendor/assets/javascripts,
// or any plugin's vendor/assets/javascripts directory can be referenced here using a relative path.
//
// It's not advisable to add code directly here, but if you do, it'll appear at the bottom of the
// compiled file.
//
// Read Sprockets README (https://github.com/sstephenson/sprockets#sprockets-directives) for details
// about supported directives.
//
//= require jquery
//= require jquery_ujs
//= require foundation
//= require turbolinks
//= require owl.carousel
//= require_tree .
//= require chosen-jquery

$(document).on("page:load ready", function(){
    $("input.datepicker").datepicker();
});
$(function() {
  initPage();
});
$(window).bind('page:change', function() {
  initPage();
});
function initPage() {
  	$("#deal_service_category_id").change(function(){
  		if($("#deal_service_category_id option:selected").text()=="Internet"){
			$("#telephone-attributes").css("display","none");
			$("#telephone-domestic-call-minutes").prop("required",false);

			$("#cable-attributes").css("display","none");
			$("#cable-free-channels").prop("required",false);

			$("#cellphone-attributes").css("display","none");
			$("#cellphone-telephone-domestic-call-minutes").prop("required",false);

			$("#bundle-attributes").css("display","none");
			$("#bundle-internet-download").prop("required",false);
			$("#bundle-telephone-domestic-call-minutes").prop("required",false);
			$("#bundle-cable-free-channels").prop("required",false);

			$("#internet-attributes").css("display","block");
			$("#internet-download").prop("required",true);
		}else if($("#deal_service_category_id option:selected").text()=="Telephone"){
			$("#internet-attributes").css("display","none");
			$("#internet-download").prop("required",false);

			$("#cable-attributes").css("display","none");
			$("#cable-free-channels").prop("required",false);

			$("#cellphone-attributes").css("display","none");
			$("#cellphone-telephone-domestic-call-minutes").prop("required",false);

			$("#bundle-attributes").css("display","none");
			$("#bundle-internet-download").prop("required",false);
			$("#bundle-telephone-domestic-call-minutes").prop("required",false);
			$("#bundle-cable-free-channels").prop("required",false);

			$("#telephone-attributes").css("display","block");
			$("#telephone-domestic-call-minutes").prop("required",true);
		}else if($("#deal_service_category_id option:selected").text()=="Cable"){
			$("#internet-attributes").css("display","none");
			$("#internet-download").prop("required",false);

			$("#telephone-attributes").css("display","none");
			$("#telephone-domestic-call-minutes").prop("required",false);

			$("#cellphone-attributes").css("display","none");
			$("#cellphone-telephone-domestic-call-minutes").prop("required",false);

			$("#bundle-attributes").css("display","none");
			$("#bundle-internet-download").prop("required",false);
			$("#bundle-telephone-domestic-call-minutes").prop("required",false);
			$("#bundle-cable-free-channels").prop("required",false);

			$("#cable-attributes").css("display","block");
			$("#cable-free-channels").prop("required",true);
		}else if($("#deal_service_category_id option:selected").text()=="Cellphone"){
			$("#internet-attributes").css("display","none");
			$("#internet-download").prop("required",false);

			$("#telephone-attributes").css("display","none");
			$("#telephone-domestic-call-minutes").prop("required",false);

			$("#bundle-attributes").css("display","none");
			$("#bundle-internet-download").prop("required",false);
			$("#bundle-telephone-domestic-call-minutes").prop("required",false);
			$("#bundle-cable-free-channels").prop("required",false);

			$("#cable-attributes").css("display","none");
			$("#cable-free-channels").prop("required",false);

			$("#cellphone-attributes").css("display","block");
			$("#cellphone-telephone-domestic-call-minutes").prop("required",true);

		}else if($("#deal_service_category_id option:selected").text()=="Bundle"){
			$("#internet-attributes").css("display","none");
			$("#internet-download").prop("required",false);

			$("#telephone-attributes").css("display","none");
			$("#telephone-domestic-call-minutes").prop("required",false);

			$("#cable-attributes").css("display","none");
			$("#cable-free-channels").prop("required",false);

			$("#cellphone-attributes").css("display","none");
			$("#cellphone-telephone-domestic-call-minutes").prop("required",false);

			$("#bundle-attributes").css("display","block");
			$("#bundle-internet-download").prop("required",true);
			$("#bundle-telephone-domestic-call-minutes").prop("required",true);
			$("#bundle-cable-free-channels").prop("required",true);
		}

	});

	$("#deal_bundle_deal_attributes_attributes_0_bundle_combo").change(function(){
		if($("#deal_bundle_deal_attributes_attributes_0_bundle_combo option:selected").text()=="Internet and Telephone"){
			$("#bundle-cable").css("display","none");
			$("#bundle-cable-free-channels").prop("required",false);

			$("#bundle-telephone").css("display","block");
			$("#bundle-telephone-domestic-call-minutes").prop("required",true);

		}else if($("#deal_bundle_deal_attributes_attributes_0_bundle_combo option:selected").text()=="Internet and Cable"){
			$("#bundle-telephone").css("display","none");
			$("#bundle-telephone-domestic-call-minutes").prop("required",false);

			$("#bundle-cable").css("display","block");
			$("#bundle-cable-free-channels").prop("required",true);

		}else if($("#deal_bundle_deal_attributes_attributes_0_bundle_combo option:selected").text()=="Internet,Telephone and Cable"){
			$("#bundle-telephone").css("display","block");
			$("#bundle-telephone-domestic-call-minutes").prop("required",true);

			$("#bundle-cable").css("display","block");
			$("#bundle-cable-free-channels").prop("required",true);
		}else if($("#deal_bundle_deal_attributes_attributes_0_bundle_combo option:selected").text()=="Telephone and Cable"){
			$("#bundle-internet").css("display","none");
			$("#bundle-internet-download").prop("required",false);

			$("#bundle-telephone").css("display","block");
			$("#bundle-telephone-domestic-call-minutes").prop("required",true);

			$("#bundle-cable").css("display","block");
			$("#bundle-cable-free-channels").prop("required",true);
		}

	});


	$("#domestic_unlimited").click(function(){
		if($(this).is(':checked')){
    		$("#telephone-domestic-call-minutes").val("Unlimited");
    		$("#telephone-domestic-call-minutes").prop("readonly",true);
    	}else{
    		$("#telephone-domestic-call-minutes").val("");
    		$("#telephone-domestic-call-minutes").prop("readonly",false);
    	}
	});

	$("#international_unlimited").click(function(){
		if($(this).is(':checked')){
    		$("#telephone-international-call-minutes").val("Unlimited");
    		$("#telephone-international-call-minutes").prop("readonly",true);
    	}else{
    		$("#telephone-international-call-minutes").val("");
    		$("#telephone-international-call-minutes").prop("readonly",false);
    	}
	});

	$("#bundle_domestic_unlimited").click(function(){
		if($(this).is(':checked')){
    		$("#bundle-telephone-domestic-call-minutes").val("Unlimited");
    		$("#bundle-telephone-domestic-call-minutes").prop("readonly",true);
    	}else{
    		$("#bundle-telephone-domestic-call-minutes").val("");
    		$("#bundle-telephone-domestic-call-minutes").prop("readonly",false);
    	}
	});

	$("#bundle_international_unlimited").click(function(){
		if($(this).is(':checked')){
    		$("#bundle-telephone-international-call-minutes").val("Unlimited");
    		$("#bundle-telephone-international-call-minutes").prop("readonly",true);
    	}else{
    		$("#bundle-telephone-international-call-minutes").val("");
    		$("#bundle-telephone-international-call-minutes").prop("readonly",false);
    	}
	});

	$("#cellphone_domestic_unlimited").click(function(){
		if($(this).is(':checked')){
    		$("#cellphone-domestic-call-minutes").val("Unlimited");
    		$("#cellphone-domestic-call-minutes").prop("readonly",true);
    	}else{
    		$("#cellphone-domestic-call-minutes").val("");
    		$("#cellphone-domestic-call-minutes").prop("readonly",false);
    	}
	});

	$("#cellphone_international_unlimited").click(function(){
		if($(this).is(':checked')){
    		$("#cellphone-international-call-minutes").val("Unlimited");
    		$("#cellphone-international-call-minutes").prop("readonly",true);
    	}else{
    		$("#cellphone-international-call-minutes").val("");
    		$("#cellphone-international-call-minutes").prop("readonly",false);
    	}
	});


	$("#order_activation_start_date").datepicker({ dateFormat: 'dd-mm-yy' });
	$("#deal_start_date").datepicker({ dateFormat: 'dd-mm-yy' });
    $("#deal_end_date").datepicker({ dateFormat: 'dd-mm-yy' });
    $("#additional-offer-start-date").datepicker({ dateFormat: 'dd-mm-yy' });
    $("#additional-offer-end-date").datepicker({ dateFormat: 'dd-mm-yy' });
    $("#advertisement_start_date").datepicker({ dateFormat: 'dd-mm-yy' });
    $("#advertisement_end_date").datepicker({ dateFormat: 'dd-mm-yy' });
    $("#service_preference_start_date").datepicker({ dateFormat: 'dd-mm-yy' });
    $("#service_preference_end_date").datepicker({ dateFormat: 'dd-mm-yy' });

    $("#deal-nationwide").click(function(){
		if($(this).is(':checked')){
    		$("#deal-include-zipcode").prop("disabled",true);
    		$("#deal-exclude-zipcode").prop("disabled",false);
    	}else{
    		$("#deal-include-zipcode").prop("disabled",false);
    		$("#deal-exclude-zipcode").prop("disabled",true);
    	}
	});

    $("#additional-offer-nationwide").click(function(){
		if($(this).is(':checked')){
    		$(".additional-offer-zipcode").prop("disabled",true);
    	}else{
    		$(".additional-offer-zipcode").prop("disabled",false);
    	}
	});

	// $("#is_contract").click(function(){
	// 	if($(this).is(':checked')){
 //    		$("#contract_period").prop("disabled",false);
 //    	}else{
 //    		$("#contract_period").prop("disabled",true);
 //    	}
	// });

    $("#deal-include-zipcode").prop("disabled",true);
	$(".additional-offer-zipcode").prop("disabled",true);
}

function populate_service_provider(obj)
{
    if (obj.value != '')
	{
		var handleResponse = function (status, response) {
		   	var data=JSON.parse(response);
		   	var selectList = document.getElementById("deal_service_provider_id");
		   	selectList.options.length = 0;
		   	for(var i=0;i<data.length;i++){
				var option = document.createElement("option");
   				option.value = data[i].id;
    			option.text = data[i].name;
    			selectList.appendChild(option);
			}

		}
		var handleStateChange = function () {
		   switch (xmlhttp.readyState) {
		      case 0 : // UNINITIALIZED
		      case 1 : // LOADING
		      case 2 : // LOADED
		      case 3 : // INTERACTIVE
		      break;
		      case 4 : // COMPLETED
		      handleResponse(xmlhttp.status, xmlhttp.responseText);
		      break;
		      default: alert("error");
		   }
		}
		var xmlhttp=new XMLHttpRequest();
		xmlhttp.onreadystatechange=handleStateChange;
		xmlhttp.open("GET", "/deals/get_service_providers/?category="+obj.value,true);
		xmlhttp.send(null);

	}

}
function populate_checklist_service_provider(obj)
{
    if (obj.value != '')
	{
		var handleResponse = function (status, response) {
		   	var data=JSON.parse(response);
		   	var selectList = document.getElementById("service_provider_checklist_service_provider_id");
		   		debugger
		   	selectList.options.length = 0;
		   	for(var i=0;i<data.length;i++){
				var option = document.createElement("option");
   				option.value = data[i].id;
    			option.text = data[i].name;
    			selectList.appendChild(option);
			}

		}
		var handleStateChange = function () {
		   switch (xmlhttp.readyState) {
		      case 0 : // UNINITIALIZED
		      case 1 : // LOADING
		      case 2 : // LOADED
		      case 3 : // INTERACTIVE
		      break;
		      case 4 : // COMPLETED
		      handleResponse(xmlhttp.status, xmlhttp.responseText);
		      break;
		      default: alert("error");
		   }
		}
		var xmlhttp=new XMLHttpRequest();
		xmlhttp.onreadystatechange=handleStateChange;
		xmlhttp.open("GET", "/deals/get_service_providers/?category="+obj.value,true);
		xmlhttp.send(null);

	}

}

//$(function(){ $(document).foundation(); });
