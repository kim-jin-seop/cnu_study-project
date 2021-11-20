//변경을 할 때 마다 html을 저장하여두고, 그 html중 제일 마지막 정보를 보여준다.
function updata(){
	if(localStorage.length != 0){
		document.documentElement.innerHTML = localStorage.getItem("index"+(localStorage.length-1))
	}
	closetheAdd();
}

//Add버튼을 누르면 add를 할 때 정보를 입력할 창이 나온다.
function opentheAdd(){
	document.getElementsByTagName('dialog')[0].style.display = 'block';

}

//Add버튼을 누른 창이 꺼지도록 만들어준다.
function closetheAdd(){
	document.getElementsByTagName('dialog')[0].style.display = 'none';
	document.getElementById('dayselect').value = "Mon";
	document.getElementById('inputTitle').value = null;
	document.getElementById('inputContents').value = "";
}

//최근 수정하였던 시간을 출력하여준다.
function getTime(){
	var d = new Date();
	document.getElementById('time').innerHTML = d;
}

//drag할 때 실행되는 이벤트로 드래그 대상의 정보를 가져다 넣어준다.
function drag(ev){
	ev.dataTransfer.setData("text", ev.target.id);
}

function allowDrop(ev) {
	ev.preventDefault();
}

function emptydrop(ev){
	ev.preventDefault();
	if(ev.target.childNodes[1].childNodes.length < 1){
		var data = ev.dataTransfer.getData("text"); // title + num
		var cdata = document.getElementById(data); // 드래그 되는 대상
		var daylist = ev.target.childNodes[1].id; // day list
		
		if(cdata.parentNode.childNodes.length == 1){
			$(cdata.parentNode.parentNode).removeAttr("ondrop");
			$(cdata.parentNode.parentNode).attr("ondrop", "emptydrop(event)");
		}
		$(ev.target).removeAttr("ondrop");
		$(ev.target.childNodes[1]).append(cdata);
		$(ev.target).attr("ondrop", "nodrop()");
		$.ajax({
			type : "POST",
			url : "./daychange.jsp",
			data : {data : data, day: daylist.substring(0,3) },
			success: function(data){	
				getTime();
			}
		})
	}
	localStorage.setItem("index"+localStorage.length, document.documentElement.innerHTML);
}

function nodrop(){
}

/*drop할 때 수행되는 것으로, drop을 하게되며 원하는 위치의 데이터 그 앞에 모두 붙여넣게된다.
 * 예를 들면, 월요일 목록에 있는 데이터 4개가 있는데, 그 중 두번째 위치에 화요일 데이터 1개를 옮기고자 하면,
 * 월요일 목록 2번째 위치에 있는데이터로 화요일 데이터 1개를 드래그 하고 그 위치에 드랍하면 2 번째 위치에 데이터가 들어가고 한칸씩 밀려나게된다.*/
function drop(ev) {
	ev.preventDefault();
	var data = ev.dataTransfer.getData("text");
	var cdata = document.getElementById(data);
	$(ev.target.parentNode).before(cdata);
	if(cdata.parentNode.childNodes.length == 1){
		$(cdata.parentNode.parentNode).removeAttr("ondrop");
		$(cdata.parentNode.parentNode).attr("ondrop", "emptydrop(event)");
	}
	$.ajax({
		type : "POST",
		url : "./daychange.jsp",
		data : {data : cdata.id, day: ev.target.parentNode.parentNode.id.substring(0,3) },
		success: function(data){	
			getTime();
		}
	})
	localStorage.setItem("index"+localStorage.length, document.documentElement.innerHTML);
}

/*데이터 추가
 * 서버에 데이터를 저장하기 위하여, ajax를 이용하여 데이터를 서버에 txt파일형태로 생성하여 만들어준다.*/
function add(){
	var f = document.myForm;
	//예외처리 내용이 없을 때
	if(!f.Title.value){
		alert("제목이 없습니다.")
		f.Title.focus();
		return;
	}
	if(!f.content.value){
		alert("입력이 없습니	다.")
		f.content.focus();
		return;
	}

	$.ajax({
		type : "POST",
		url :"./Add.jsp",
		data : { num : localStorage.length ,Day : f.day.value, Title : f.Title.value, Content : f.content.value},
		dataType : "html",
		success: function(data){
			var input = f.day.value + "List";
			var inputDiv = document.getElementById(input);
			inputDiv.innerHTML += data;
			localStorage.setItem("index"+localStorage.length, document.documentElement.innerHTML);
			closetheAdd();
			var inputlist = inputDiv.parentNode
			$(inputlist).removeAttr("ondrop");
			$(inputlist).attr("ondrop", "nodrop()");
		},
		error: function(){
		}
	})
	getTime();
}

/*선택하고 삭제하기, ajax를 이용하여 데이터를 서버에서 삭제*/
function selectRemove(){
	$( "input[name='dataList']:checked" ).each (function (){
		var removedata = document.getElementById($(this).val());
		$.ajax({
			type : "POST",
			url : "./Remove.jsp",
			data : {filename :$(this).val()},
			success: function(data){
				removedata.remove();
				localStorage.setItem("index"+localStorage.length, document.documentElement.innerHTML);
				getTime();
			}
		})
	});
}

/*데이터 변경을 위해 데이터 띄우기, ajax 사용하여 서버에 저장된 파일 읽어오기.*/
function modify(ptag){
	document.getElementsByTagName('dialog')[1].style.display = 'block';
	var path = ptag.parentNode.getAttribute("id");
	$.ajax({
		type : "POST",
		url : "./getItem.jsp",
		data : {filename : path},
		dataType :"html",
		success: function(data){
			$("#m").html(data);
		}
	})
}

/*수정하기 위해 띄운창 내리기*/
function closethemodify(){
	document.getElementsByTagName('dialog')[1].style.display = 'none';
}

/*데이터 값 수정하기, ajax를 이용하여 수정된 데이터 저장*/
function change(findpath){
	var path = findpath.id;
	var p = findpath.parentNode;
	$.ajax({
		type : "POST",
		url : "./modify.jsp",
		data : {filename : path, Title : p.T.value, content : p.C.value ,num : localStorage.length, dst : p.T.id},
		dataType : "html",
		success : function(){
			var changeId =  p.T.value + localStorage.length;
			var lastId = p.id.substring(0,p.id.length-1);
			var wantchangedata = document.getElementById(lastId);
			var tit_ = "";
			var a = p.T.value.split("_");
			for(var i = 0; i < a.length; i ++){
				tit_ += a[i]
			}
			wantchangedata.id = changeId;
			wantchangedata.innerHTML = "<input type='checkbox' name = 'dataList' value='"+ p.T.value + localStorage.length+"'> <p ondrop='drop(event)' onclick = modify(this) id ='showdata'>"+tit_+"</p>";
			localStorage.setItem("index"+localStorage.length, document.documentElement.innerHTML);
			document.getElementsByTagName('dialog')[1].style.display = 'none';
		}
	})
	getTime();
}

function onEnter(){
	if(event.keyCode == 13){
		var data = document.getElementById('Find_Data').value;
		var day = document.getElementById('Find_Day');
		var MonList = document.getElementById('MonList');
		var TueList = document.getElementById('TueList');
		var WedList = document.getElementById('WedList');
		var ThuList = document.getElementById('ThuList');
		var FriList = document.getElementById('FriList');

		for(var i = 1 ; i < MonList.childNodes.length; i = i + 2){
			if(MonList.childNodes[i].childNodes[3].innerHTML.match(data) != data)
				MonList.childNodes[i].style.display = 'none';
			else MonList.childNodes[i].style.display = 'block'
		}

		for(var i = 1 ; i < TueList.childNodes.length; i = i + 2){
			if(TueList.childNodes[i].childNodes[3].innerHTML.match(data) != data)
				TueList.childNodes[i].style.display = 'none';
			else TueList.childNodes[i].style.display = 'block'
		}

		for(var i = 1 ; i < WedList.childNodes.length; i = i + 2){
			if(WedList.childNodes[i].childNodes[3].innerHTML.match(data) != data)
				WedList.childNodes[i].style.display = 'none';
			else WedList.childNodes[i].style.display = 'block'
		}

		for(var i = 1 ; i < ThuList.childNodes.length; i = i + 2){
			if(ThuList.childNodes[i].childNodes[3].innerHTML.match(data) != data)
				ThuList.childNodes[i].style.display = 'none';
			else ThuList.childNodes[i].style.display = 'block'
		}


		for(var i = 1 ; i < FriList.childNodes.length; i = i + 2){
			if(FriList.childNodes[i].childNodes[3].innerHTML.match(data) != data)
				FriList.childNodes[i].style.display = 'none';
			else FriList.childNodes[i].style.display = 'block'
		}
	
		if (day.value == "Mon"){
			for(var i = 1 ; i < TueList.childNodes.length; i = i + 2){
				TueList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < WedList.childNodes.length; i = i + 2){
				WedList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < ThuList.childNodes.length; i = i + 2){
				ThuList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < FriList.childNodes.length; i = i + 2){
				FriList.childNodes[i].style.display = 'none';
			}	  
		}
		else if(day.value == "Tue"){
			for(var i = 1 ; i < MonList.childNodes.length; i = i + 2){
				MonList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < WedList.childNodes.length; i = i + 2){
				WedList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < ThuList.childNodes.length; i = i + 2){
				ThuList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < FriList.childNodes.length; i = i + 2){
				FriList.childNodes[i].style.display = 'none';
			}	  
		}
		else if(day.value == "Wed"){
			for(var i = 1 ; i < MonList.childNodes.length; i = i + 2){
				MonList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < TueList.childNodes.length; i = i + 2){
				TueList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < ThuList.childNodes.length; i = i + 2){
				ThuList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < FriList.childNodes.length; i = i + 2){
				FriList.childNodes[i].style.display = 'none';
			}	  
		}
		else if(day.value == "Thu"){
			for(var i = 1 ; i < MonList.childNodes.length; i = i + 2){
				MonList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < TueList.childNodes.length; i = i + 2){
				TueList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < WedList.childNodes.length; i = i + 2){
				WedList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < FriList.childNodes.length; i = i + 2){
				FriList.childNodes[i].style.display = 'none';
			}	  
		}
		else if(day.value == "Fri"){
			for(var i = 1 ; i < MonList.childNodes.length; i = i + 2){
				MonList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < TueList.childNodes.length; i = i + 2){
				TueList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < WedList.childNodes.length; i = i + 2){
				WedList.childNodes[i].style.display = 'none';
			}
			for(var i = 1 ; i < ThuList.childNodes.length; i = i + 2){
				ThuList.childNodes[i].style.display = 'none';
			}	  
		}
	}
}

function findTitle(day,findName){

	if(day == "Day"){	 

	}
}
