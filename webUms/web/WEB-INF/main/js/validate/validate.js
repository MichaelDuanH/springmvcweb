$(document).ready(function() {
	$.extend($.fn.validatebox.defaults.rules,{
		validatePass: {
			validator:function(val) {
				if(val != pass) {
					return false;
				}
				return true;
			},
	        message:'密码错误',
		},
		equalTo:{
	    	validator:function(curVal,newPass) {
	    		newPass = $($.trim(newPass)).val();
	    		if(curVal == newPass) {
	    			return true;
	    		}
	    		return false;
	    	},
		  message:'新密码必须一致'
	   },
	   checkPass:{
		   validator:function(val) {
			   if(val) {
				   if(val.length<=6 && val.length>=15) {
					   return false;
				   }
				   
				   if(!val.match(/([a-z])+/) ||!val.match(/([0-9])+/)|| !val.match(/([A-Z])+/)||!val.match(/[^a-zA-Z0-9]+/)) {
					   return false;
				   }
				   return true;
			   }                        
		   },
		   message:'您输入的密码不符合要求'
	   }
	});
});