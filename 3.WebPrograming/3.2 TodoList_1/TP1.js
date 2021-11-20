/*201601989김진섭 1차 텀프로젝트 TodoList만들기 */

/*세마포어의 원리를 이용하여 구현(담장치기). 만약 검색을 이전에 하였다면
검색취소창을 생성하지 않도록 한다. */
var nowfind = 1;

//월화수목금 각 데이터의 index
/*index값은 각 데이터 A-월 B-화 C-수 D-목 E-금의 데이터를 삽입하는 위치
삽입할 때 직접참조는 하지 않지만, 데이터를 관리하기 위하여 만든 인덱스*/
var A_index = 0;
var B_index = 0;
var C_index = 0;
var D_index = 0;
var E_index = 0;

/*데이터들은 각각 객체로, 데이터를 관리하기 위한 리스트.객체의 배열로 구현*/
var A_Datalist = new Array();
var B_Datalist = new Array();
var C_Datalist = new Array();
var D_Datalist = new Array();
var E_Datalist = new Array();

/*수정시에, 이전 index와 day를 몰라서 수정을 못할 수 있으므로,
매번 수정하는 버튼을 누를 시에, 그 위치로 초기화 시켜준다.*/
var prev_index;
var prev_day;

// 1.AddTodo 버튼 눌렀을 때
/* dialog TagName을 가져와 display(보여주기) */
function opentheAdd(){
  document.getElementsByTagName('dialog')[0].style.display = 'block';
}

//dialog에서 이미지를 눌렀을 때
/* 추가하는 dialog를 닫기
close()를 사용하지않고, display를 사용해 열고닫기 때문에,
값을 추가하기 위해 작성하다 닫았을 때 남아있는 데이터를 없애주기 위해 밑에 초기화 과정 추가*/
function closetheAdd(){
  document.getElementsByTagName('dialog')[0].style.display = 'none';
  document.getElementById('dayselect').value = "Mon";
  document.getElementById('inputTitle').value = null;
  document.getElementById('inputContents').value = "";
}

//List에 Add 수행
/*제목이 입력되지않았거나, 내용이 없으면 오류처리, Day는 처음에 Monday로 지정
만약, 제목과 내용이 없다면 오류 메세지 보내준 뒤, 그 위치로 포커싱해서 작성하도록 도움
요일별로 데이터리스트를 따로 만들었으므로, 요일별로 처리. 만약 입력된 day값이 월요일이면
A_list에 MonList(보여지는 리스트를 담을 공간)의 id값을 저장한 뒤, innerHTML을 사용하여
<div>태그를 삽입하고, 버튼 두개(삭제버튼, 수정버튼)와 제목이름을 넣어 <div>태그를 완성하여 List를 보여준다.
이 때, <div> 태그의 열때와 삭제할 때의 일처리를 하기 위하여 매개변수를 넣어주는데,
삭제할때는 부모노드의 id, 현재 자신의 id, 현재 자신(this)를 매개변수로 보내줌->세개의 사용 이후에 설명
수정할때는 부모노드의 id를 보내준다.(여기서 부모노드의 id는 내가 지정하였고, day_index형태로 지정하여,
연산을 통해, index와 day를 구분할 수 있도록 지정. 여기서 부모노드는 <button>태그를 감싸는 <div>태그임 )
삭제하는 방향으로 보내주는 자신의 id는 Day를 구할 수 있도록 명시(굳이 보낼 필요는 없지만, 생각의 편의상 보냈음)
화,수,목,금 데이터 처리 전부 동일*/
function add(){
  var f = document.myForm;
  //예외처리 내용이 없을 때
  if(!f.Title.value){
    alert("제목이 없습니다.")
    f.Title.focus();
    return;
  }
  if(!f.content.value){
    alert("입력이 없습니다.")
    f.content.focus();
    return;
  }
  //요일별 데이터 TodoList 삽입
  if(f.day.value == "Mon"){
    A_list = document.getElementById("MonList");
    A_list.innerHTML += "<div class = 'list' id ='A_"+A_index+"'> <button onclick = 'opentheModify(this.parentNode.id)' class='modify'>수정</button><button id = 'A' onclick = 'removeButton(this.parentNode.id,this.id,this)' class='remove'>X</button><p id ='showdata'>"+f.Title.value+"</p></div>"
    var addelement = {'index' : A_index, 'day' : f.day.value, 'content' : f.content.value, 'title' : f.Title.value, 'targetId' : "A_"+A_index};
    A_Datalist.push(addelement);
    A_index ++;
  }
  else if(f.day.value == "Tue"){
    B_list = document.getElementById("TueList");
    B_list.innerHTML += "<div class = 'list' id ='B_"+B_index+"'> <button onclick = 'opentheModify(this.parentNode.id)' class='modify'>수정</button><button id = 'B' onclick = 'removeButton(this.parentNode.id,this.id,this)' class='remove'>X</button><p id ='showdata'>"+f.Title.value+"</p></div>"
    var addelement = {'index' : B_index, 'day' : f.day.value, 'content' : f.content.value, 'title' : f.Title.value, 'targetId' : "B_"+B_index};
    B_Datalist.push(addelement);
    B_index ++;
  }
  else if(f.day.value == "Wed"){
    C_list = document.getElementById("WedList");
    C_list.innerHTML += "<div class = 'list' id ='C_"+C_index+"'> <button onclick = 'opentheModify(this.parentNode.id)' class='modify'>수정</button><button id = 'C' onclick = 'removeButton(this.parentNode.id,this.id,this)' class='remove'>X</button><p id ='showdata'>"+f.Title.value+"</p></div>"
    var addelement = {'index' : C_index, 'day' : f.day.value, 'content' : f.content.value, 'title' : f.Title.value, 'targetId' : "C_"+C_index};
    C_Datalist.push(addelement);
    C_index ++;
  }
  else if(f.day.value == "Thu"){
    D_list = document.getElementById("ThuList");
    D_list.innerHTML += "<div class = 'list' id ='D_"+D_index+"'> <button onclick = 'opentheModify(this.parentNode.id)' class='modify'>수정</button><button id = 'D' onclick = 'removeButton(this.parentNode.id,this.id,this)' class='remove'>X</button><p id ='showdata'>"+f.Title.value+"</p></div>"
    var addelement = {'index' : D_index, 'day' : f.day.value, 'content' : f.content.value, 'title' : f.Title.value, 'targetId' : "D_"+D_index};
    D_Datalist.push(addelement);
    D_index ++;
  }
  else if(f.day.value == "Fri"){
    E_list = document.getElementById("FriList");
    E_list.innerHTML += "<div class = 'list' id ='E_"+E_index+"'> <button onclick = 'opentheModify(this.parentNode.id)' class='modify'>수정</button><button id = 'E' onclick = 'removeButton(this.parentNode.id,this.id,this)' class='remove'>X</button><p id ='showdata'>"+f.Title.value+"</p></div>"
    var addelement = {'index' : E_index, 'day' : f.day.value, 'content' : f.content.value, 'title' : f.Title.value, 'targetId' : "E_"+E_index};
    E_Datalist.push(addelement);
    E_index ++;
  }
  closetheAdd();
}

//todolist 제거버튼을 누를 경우
/*제거버튼을 누를 경우, 부모노드의 id,자신의 id 그리고 자신을 매개변수로 받는다.
부모노드의 id의 특징은 위에서 설명을 한것이고, index를 구하여주기 위해 가져왔다.
자신의 id는 크게 필요는 없지만, 계산의 편의상 day를 계산하기 위해 가져왔다. 위 부모노드 id의 [0]값이랑 동일
자신을 매개변수로 가져온 이유는, remove()를 수행해 주기 위해 가져왔다.
우선, 수학적으로 A-이외의 index 즉 2부터 부모 id의 length까지의 데이터를 가져와 연산을 해 index를 구해준다.
그리고 요일별로 나누어 생각을 해준다. 대표적으로 월요일을 생각해주면, 우선, datalist의 data들을 한칸씩
땡겨 주어야 하므로 for문을 이용하여 index위치부분부터 덮어써주기를 한다. 이 때, Datalist에 있는 내용중
index부분은 바꾸어 주어야 하므로, 객체의 index에 접근하여 i로 데이터를 변경하여준다. 그리고, targetId를
넣어두었는데(<div>태그의 id)그 값도 바꾸어준다. 여기서 끝나면, 문제가 발생한다.
왜냐하면, div태그의 id값이 안바뀌어있기 때문에, 이후에 index를 찾을 때, 이상한 위치를 찾을 수 있기 때문이다.
따라서, div의 id값을 바꾸어준다. 그리고 해당 index를 1 줄여준 뒤, remove()를 이용하여 부모로부터 지워지게 한다.
추가 예외처리. 만약 지울 데이터가 하나라면, 아래 방법으로는 지워지지 않으므로(for문 안들어감), shift()연산을 통해 삭제*/

/*수정 후 -> -> 위처럼 구현을 for문을 이용해 직접 수정을 하였지만, 배열을 이용하면 splice라는 메소드를 사용하면 간단히 삭제가 된다
우선, index를 찾는 과정은 동일하게 수행하여주고, 그 뒤 splice를 사용하여 List에 값을 지워준다. splice(a,b)를 사용하면, List의 index a위치 부터
b개의 값이 지워지고, 그만큼 값이 당겨 올라간다. 그리고, 이후에, 태그의 id값을 정의해준 부분, 데이터에 들어있는 index 그리고 targetId을 올려주는 for문을 사용한다. */
function removeButton(removeid,daycase,target){
  var index = "";

  for(var j = 2; j < removeid.length; j++){
    index += removeid[j];
  }
  index = index*1;
  if(daycase =="A"){
    A_Datalist.splice(index,1);
    A_index --;
    for(var i = index; i < A_index; i++){
      document.getElementById("A_"+(i+1)).id = "A_"+i;
      A_Datalist[i].index = i;
      A_Datalist[i].targetId = "A_"+i;
    }
  }
  else if(daycase == "B"){
    B_Datalist.splice(index,1);
    B_index --;
    for(var i = index; i < B_index; i++){
      document.getElementById("B_"+(i+1)).id = "B_"+i;
      B_Datalist[i].index = i;
      B_Datalist[i].targetId = "B_"+i;
    }
  }
  else if(daycase == "C"){
    C_Datalist.splice(index,1);
    C_index --;
    for(var i = index; i < C_index; i++){
      document.getElementById("C_"+(i+1)).id = "C_"+i;
      C_Datalist[i].index = i;
      C_Datalist[i].targetId = "C_"+i;
    }
  }
  else if(daycase == "D"){
    D_Datalist.splice(index,1);
    D_index --;
    for(var i = index; i < D_index; i++){
      document.getElementById("D_"+(i+1)).id = "D_"+i;
      D_Datalist[i].index = i;
      D_Datalist[i].targetId = "D_"+i;
    }
  }
  else if(daycase == "E"){
    E_Datalist.splice(index,1);
    E_index --;
    for(var i = index; i < E_index; i++){
      document.getElementById("E_"+(i+1)).id = "E_"+i;
      E_Datalist[i].index = i;
      E_Datalist[i].targetId = "E_"+i;
    }
  }
  target.parentNode.remove();
}

//수정버튼을 눌렀을 때
/*수정 버튼을 눌렀을 때, 수정dialog가 나오도록 하여주는 메소드이다.
우선, 수정을 하기 위한 다이어로그롤 쓸 때, 위 추가 다이어로그와 동일하게 display를 가지고 확인하기 때문에,
초기화를 시켜주어야하는데, 그렇게 되기 위해서, innerHTML을 사용해 변경하는 부분만 초기화 시켜준다.
그 뒤, block으로 설정하여 보이게 만들어 준 뒤, index를 구해주고 어떤 day인지 구해주어, 그 day에 맡게 처리한다.
datalist에 있는 정보를 가져와 각각의 데이터로 설정하여주어, 보여지게 만들어준다.
여기서 index는 우선순위이다.
*/
function opentheModify(dataid){
  document.getElementById("modifyContents").innerHTML = "";
  document.getElementsByTagName('dialog')[1].style.display = 'block';
  var index = "";
  for(var j = 2; j < dataid.length; j++){
    index += dataid[j];
  }
  index = index*1;
  var day = dataid[0];

  prev_index = index;
  prev_day = day;

  if(day == "A"){
    document.getElementById("modify_Day").value = "Mon";
    document.getElementById("modifyIndexNumber").value = index+1;
    document.getElementById("modifyTitle").value = A_Datalist[index].title;
    document.getElementById("modifyContents").innerHTML += A_Datalist[index].content;
  }
  else if(day =="B"){
    document.getElementById("modify_Day").value = "Tue";
    document.getElementById("modifyIndexNumber").value = index+1;
    document.getElementById("modifyTitle").value = B_Datalist[index].title;
    document.getElementById("modifyContents").innerHTML += B_Datalist[index].content;
  }
  else if(day =="C"){
    document.getElementById("modify_Day").value = "Wed";
    document.getElementById("modifyIndexNumber").value = index+1;
    document.getElementById("modifyTitle").value = C_Datalist[index].title;
    document.getElementById("modifyContents").innerHTML += C_Datalist[index].content;
  }
  else if(day =="D"){
    document.getElementById("modify_Day").value = "Thu";
    document.getElementById("modifyIndexNumber").value = index+1;
    document.getElementById("modifyTitle").value = D_Datalist[index].title;
    document.getElementById("modifyContents").innerHTML += D_Datalist[index].content;
  }
  else if(day =="E"){
    document.getElementById("modify_Day").value = "Fri";
    document.getElementById("modifyIndexNumber").value = index+1;
    document.getElementById("modifyTitle").value = E_Datalist[index].title;
    document.getElementById("modifyContents").innerHTML += E_Datalist[index].content;
  }
}

//수정하는 다이아로그에서 closs img를 눌렀을 때
/*getElemntByTagName을 이용해 dialog 태그를 받고, display를 none하여 안보이게 한다.*/
function closethemodify(){
    document.getElementsByTagName('dialog')[1].style.display = 'none';
}

//title과 Content를 변경하는 함수
/*title과 content데이터를 변경해준다..*/
function changeTitleandContent(title, content,day,index){
  if(day == "Mon"){
    A_Datalist[index].title = title;
    A_Datalist[index].content = content;
  }
  else if(day =="Tue"){
    B_Datalist[index].title = title;
    B_Datalist[index].content = content;
  }
  else if(day =="Wed"){
    C_Datalist[index].title = title;
    C_Datalist[index].content = content;
  }
  else if(day =="Thu"){
    D_Datalist[index].title = title;
    D_Datalist[index].content = content;
  }
  else if(day =="Fri"){
    E_Datalist[index].title = title;
    E_Datalist[index].content = content;
  }
}


//요일 바꿔주기위한 index의 마지막 부분 찾기
/*우선순위의 초과를 막아주기 위한 값을 찾기 위해 구현 index를 return하여준다.*/
function daychangelastindex(want_change_day){
  if(want_change_day == "Mon"){
    return A_index;
  }
  else if(want_change_day == "Tue"){
    return B_index;
  }
  else if(want_change_day == "Wed"){
    return C_index;
  }
  else if(want_change_day == "Thu"){
    return D_index;
  }
  else if(want_change_day == "Fri"){
    return E_index;
  }
}

//title에 따라 보여지는 Todolist 정리
/*데이터들을 변경하면, Todolist의 값들이 전체적으로 변경되어야하는데, 매번 변경을 생각해주기 힘들어서,
따로 함수를 정의하여, 매번생각을 안하고, 마지막에 한꺼번에 정리하도록 설정하였다. div 태그의 id를 가져와
그것의 children[2]인 p태그의 innerHTML에 데이터를 넣도록 한다. Datalist에 저장된 값대로 보여지도록 설정한다.*/
function titlenameset(){
  if(A_index != 0){
    for(var i = 0; i < A_index; i ++){
      var setid = document.getElementById(A_Datalist[i].targetId);
      setid.children[2].innerHTML = A_Datalist[i].title;
    }
  }
  if(B_index != 0){
    for(var i = 0; i < B_index; i ++){
      var setid = document.getElementById(B_Datalist[i].targetId);
      setid.children[2].innerHTML = B_Datalist[i].title;
    }
  }
  if(C_index != 0){
    for(var i = 0; i < C_index; i ++){
      var setid = document.getElementById(C_Datalist[i].targetId);
      setid.children[2].innerHTML = C_Datalist[i].title;
    }
  }
  if(D_index != 0){
    for(var i = 0; i < D_index; i ++){
      var setid = document.getElementById(D_Datalist[i].targetId);
      setid.children[2].innerHTML = D_Datalist[i].title;
    }
  }
  if(E_index != 0){
    for(var i = 0; i < E_index; i ++){
      var setid = document.getElementById(E_Datalist[i].targetId);
      setid.children[2].innerHTML = E_Datalist[i].title;
    }
  }
}

//우선순위만 변경하는 메소드
/*prev_day는 이전에 수정을 눌렀던 위치이다. 바뀌는 데이터를 저장을 하고 for문을 돌려 우선순위를 뒤로 미루는 작업(만약 우선순위를 앞으로미루는 경우)
or 앞으로 당기는 작업(우선순위를 뒤로 미루는 경우)을 수행하여준다. 그리고 titlenameset()메소드를 불러서 테이블을 전체적으로 바꾼내용을 불러온다*/
function changeonlypriority(day,index,content,title){
  if(prev_day == "A"){
    var changeData = {'index': index, 'day' : day, 'content' : content, 'title' : title, 'targetId' : "A_"+index};
    for(var i = prev_index; i < index; i++ ){
      A_Datalist[i] = A_Datalist[i+1];
      A_Datalist[i].index = i;
      A_Datalist[i].targetId = "A_"+i;
    }
    for(var j = prev_index; j > index; j--){
      A_Datalist[j] = A_Datalist[j-1];
      A_Datalist[j].index = j;
      A_Datalist[j].targetId = "A_"+j;
    }
    A_Datalist[index] = changeData;
  }
  else if(prev_day == "B"){
    var changeData = {'index': index, 'day' : day, 'content' : content, 'title' : title, 'targetId' : "B_"+index};
    for(var i = prev_index; i < index; i++ ){
      B_Datalist[i] = B_Datalist[i+1];
      B_Datalist[i].index = i;
      B_Datalist[i].targetId = "B_"+i;
    }
    for(var j = prev_index; j > index; j--){
      B_Datalist[j] = B_Datalist[j-1];
      B_Datalist[j].index = j;
      B_Datalist[j].targetId = "B_"+j;
    }
    B_Datalist[index] = changeData;
  }
  else if(prev_day == "C"){
    var changeData = {'index': index, 'day' : day, 'content' : content, 'title' : title, 'targetId' : "C_"+index};
    for(var i = prev_index; i < index; i++ ){
      C_Datalist[i] = C_Datalist[i+1];
      C_Datalist[i].index = i;
      C_Datalist[i].targetId = "C_"+i;
    }
    for(var j = prev_index; j > index; j--){
      C_Datalist[j] = C_Datalist[j-1];
      C_Datalist[j].index = j;
      C_Datalist[j].targetId = "C_"+j;
    }
    C_Datalist[index] = changeData;
  }
  else if(prev_day == "D"){
    var changeData = {'index': index, 'day' : day, 'content' : content, 'title' : title, 'targetId' : "D_"+index};
    for(var i = prev_index; i < index; i++ ){
      D_Datalist[i] = D_Datalist[i+1];
      D_Datalist[i].index = i;
      D_Datalist[i].targetId = "D_"+i;
    }
    for(var j = prev_index; j > index; j--){
      D_Datalist[j] = D_Datalist[j-1];
      D_Datalist[j].index = j;
      D_Datalist[j].targetId = "D_"+j;
    }
    D_Datalist[index] = changeData;
  }
  else if(prev_day == "E"){
    var changeData = {'index': index, 'day' : day, 'content' : content, 'title' : title, 'targetId' : "E_"+index};
    for(var i = prev_index; i < index; i++ ){
      E_Datalist[i] = E_Datalist[i+1];
      E_Datalist[i].index = i;
      E_Datalist[i].targetId = "E_"+i;
    }
    for(var j = prev_index; j > index; j--){
      E_Datalist[j] = E_Datalist[j-1];
      E_Datalist[j].index = j;
      E_Datalist[j].targetId = "E_"+j;
    }
    E_Datalist[index] = changeData;
  }
  titlenameset();
}

//요일을 바꿀때
/*요일을 바꿔주는 경우이다. 우선 원래 위치의 데이터를 지워주어 안보이게 만들어준다. removeButton메소드를 사용한다.
그리고나서 innerHTML을 이용해 데이터를 새로 만들고, 맨 뒤에 넣어준 뒤 그 맨 뒤 위치의 값을 changeonlypriority()를 이용해 원하는
우선순위로 바꾸어준다.*/
function daychange(title, content,day,index){
  if(prev_day == "A"){
    var target = A_Datalist[prev_index].targetId;
    removeButton(target,"A",document.getElementById(A_Datalist[prev_index].targetId).children[1]);
  }
  else if(prev_day == "B"){
    var target = B_Datalist[prev_index].targetId;
    removeButton(target,"B",document.getElementById(B_Datalist[prev_index].targetId).children[1]);
  }
  else if(prev_day == "C"){
    var target = C_Datalist[prev_index].targetId;
    removeButton(target,"C",document.getElementById(C_Datalist[prev_index].targetId).children[1]);
  }
  else if(prev_day == "D"){
    var target = D_Datalist[prev_index].targetId;
    removeButton(target,"D",document.getElementById(D_Datalist[prev_index].targetId).children[1]);
  }
  else if(prev_day == "E"){
    var target = E_Datalist[prev_index].targetId;
    removeButton(target,"E",document.getElementById(E_Datalist[prev_index].targetId).children[1]);
  }

  if(day == "Mon"){
    A_list = document.getElementById("MonList");
    A_list.innerHTML += "<div class = 'list' id ='A_"+A_index+"'> <button onclick = 'opentheModify(this.parentNode.id)' class='modify'>수정</button><button id = 'A' onclick = 'removeButton(this.parentNode.id,this.id,this)' class='remove'>X</button><p id ='showdata'>"+title+"</p></div>"
    var addelement = {'index' : A_index, 'day' : day, 'content' : content, 'title' : title, 'targetId' : "A_"+A_index};
    A_Datalist.push(addelement);
    A_index ++;
    if(index != A_index-1){
      prev_index = A_index-1;
      prev_day = "A";
      changeonlypriority(day,index,content,title);
    }
  }
  else if(day == "Tue"){
    B_list = document.getElementById("TueList");
    B_list.innerHTML += "<div class = 'list' id ='B_"+B_index+"'> <button onclick = 'opentheModify(this.parentNode.id)' class='modify'>수정</button><button id = 'B' onclick = 'removeButton(this.parentNode.id,this.id,this)' class='remove'>X</button><p id ='showdata'>"+title+"</p></div>"
    var addelement = {'index' : B_index, 'day' : day, 'content' : content, 'title' : title, 'targetId' : "B_"+B_index};
    B_Datalist.push(addelement);
    B_index ++;
    if(index != B_index-1){
      prev_index = B_index-1;
      prev_day = "B";
      changeonlypriority(day,index,content,title);
    }
  }
  else if(day == "Wed"){
    C_list = document.getElementById("WedList");
    C_list.innerHTML += "<div class = 'list' id ='C_"+C_index+"'> <button onclick = 'opentheModify(this.parentNode.id)' class='modify'>수정</button><button id = 'C' onclick = 'removeButton(this.parentNode.id,this.id,this)' class='remove'>X</button><p id ='showdata'>"+title+"</p></div>"
    var addelement = {'index' : C_index, 'day' : day, 'content' : content, 'title' : title, 'targetId' : "C_"+C_index};
    C_Datalist.push(addelement);
    C_index ++;
    if(index != C_index-1){
      prev_index = C_index-1;
      prev_day = "C";
      changeonlypriority(day,index,content,title);
    }
  }
  else if(day == "Thu"){
    D_list = document.getElementById("ThuList");
    D_list.innerHTML += "<div class = 'list' id ='D_"+D_index+"'> <button onclick = 'opentheModify(this.parentNode.id)' class='modify'>수정</button><button id = 'D' onclick = 'removeButton(this.parentNode.id,this.id,this)' class='remove'>X</button><p id ='showdata'>"+title+"</p></div>"
    var addelement = {'index' : D_index, 'day' : day, 'content' : content, 'title' : title, 'targetId' : "D_"+D_index};
    D_Datalist.push(addelement);
    D_index ++;
    if(index != D_index-1){
      prev_index = D_index-1;
      prev_day = "D";
      changeonlypriority(day,index,content,title);
    }
  }
  else if(day == "Fri"){
    E_list = document.getElementById("FriList");
    E_list.innerHTML += "<div class = 'list' id ='E_"+E_index+"'> <button onclick = 'opentheModify(this.parentNode.id)' class='modify'>수정</button><button id = 'E' onclick = 'removeButton(this.parentNode.id,this.id,this)' class='remove'>X</button><p id ='showdata'>"+title+"</p></div>"
    var addelement = {'index' : E_index, 'day' : day, 'content' : content, 'title' : title, 'targetId' : "E_"+E_index};
    E_Datalist.push(addelement);
    E_index ++;
    if(index != E_index-1){
      prev_index = E_index-1;
      prev_day = "E";
      changeonlypriority(day,index,content,title);
    }
  }
}

//수정버튼을 눌러  수정을 한 뒤 수정을 누를 경우
/*우선 수정을 누르면, 그 수정된 데이터에 접근할 var 그리고 만약 index를 옮길 때 수행되는 최대 index값을 계산한다.
  수정은 다소 복잡한 면이 있어서, 서브함수를 여러개로 두고 생각하였다.
  예외처리로 제목과 내용이 없을 경우를 수행하여준다. 그리고 만약 변경되는 요일이 같다면, 우선순위를 바꾸는 메소드를 실행시킨다.
  그리고, 요일이 다를경우에는 요일을 바꾸는 메소드를 수행해준다. 그리고, 데이터를 바꾸어주는 메소드를 실행해주고, dialog을 닫아준다. */
function modify(){
  var mf = document.modify_Form;
  var maxIndex = daychangelastindex(mf.modify_Day.value);
  if(!mf.modifyTitle.value){
    alert("제목을 입력하지 않았습니다.");
    return;
  }
  if(!mf.modifyContents.value){
    alert("내용이 없습니다.")
    return;
  }
  if(mf.modify_Day.value == "Mon" && prev_day == "A" && mf.modifyIndexNumber.value-1 != prev_index){
    if((maxIndex) < mf.modifyIndexNumber.value &&  mf.modifyIndexNumber.value > 0){
      alert("수정하고 싶은 위치의 우선순위가 범위를 벗어납니다. '1~"+maxIndex+"사이로 지정해 주세요.'");
      return;
    }
    changeonlypriority(mf.modify_Day.value,mf.modifyIndexNumber.value-1,mf.modifyContents.value,mf.modifyTitle.value);
  }
  else if(mf.modify_Day.value == "Tue" && prev_day == "B" && mf.modifyIndexNumber.value-1 != prev_index ){
    if((maxIndex) < mf.modifyIndexNumber.value &&  mf.modifyIndexNumber.value > 0){
      alert("수정하고 싶은 위치의 우선순위가 범위를 벗어납니다. '1~"+maxIndex+"사이로 지정해 주세요.'");
      return;
    }
    changeonlypriority(mf.modify_Day.value,mf.modifyIndexNumber.value-1,mf.modifyContents.value,mf.modifyTitle.value);
  }
  else if(mf.modify_Day == "Wed" && prev_day == "C" && mf.modifyIndexNumber.value-1 != prev_index ){
    if((maxIndex) < mf.modifyIndexNumber.value &&  mf.modifyIndexNumber.value > 0){
      alert("수정하고 싶은 위치의 우선순위가 범위를 벗어납니다. '1~"+maxIndex+"사이로 지정해 주세요.'");
      return;
    }
    changeonlypriority(mf.modify_Day.value,mf.modifyIndexNumber.value-1,mf.modifyContents.value,mf.modifyTitle.value);
  }
  else if(mf.modify_Day == "Thu" && prev_day == "D" && mf.modifyIndexNumber.value-1 != prev_index ){
    if((maxIndex) < mf.modifyIndexNumber.value &&  mf.modifyIndexNumber.value > 0){
      alert("수정하고 싶은 위치의 우선순위가 범위를 벗어납니다. '1~"+maxIndex+"사이로 지정해 주세요.'");
      return;
    }
    changeonlypriority(mf.modify_Day.value,mf.modifyIndexNumber.value-1,mf.modifyContents.value,mf.modifyTitle.value);
  }
  else if(mf.modify_Day == "Fri" && prev_day == "E"&& mf.modifyIndexNumber.value-1 != prev_index ){
    if((maxIndex) < mf.modifyIndexNumber.value &&  mf.modifyIndexNumber.value > 0){
      alert("수정하고 싶은 위치의 우선순위가 범위를 벗어납니다. '1~"+maxIndex+"사이로 지정해 주세요.'");
      return;
    }
    changeonlypriority(mf.modify_Day.value,mf.modifyIndexNumber.value-1,mf.modifyContents.value,mf.modifyTitle.value);
  }else{
    if((maxIndex+1) < mf.modifyIndexNumber.value &&  mf.modifyIndexNumber.value > 0){
      alert("수정하고 싶은 위치의 우선순위가 범위를 벗어납니다. '1~"+(maxIndex+1)+"사이로 지정해 주세요.'");
      return;
    }
    daychange(mf.modifyTitle.value, mf.modifyContents.value,mf.modify_Day.value,mf.modifyIndexNumber.value-1);
  }
  changeTitleandContent(mf.modifyTitle.value, mf.modifyContents.value,mf.modify_Day.value,mf.modifyIndexNumber.value-1);
  closethemodify();
}

//찾기 과정에서 엔터를 눌러서 데이터를 검색할 때
/*제목을 이용하여 검색을 수행하는데, onkeypress를 사용하였다. evenet.keyCode를 이용하면, keyCode값을 가져올 수 있다.
그렇게 하여, 데이터가 13이면 enter가 입력된것이고, 그렇게 되면 onEnter()가 실행되도록한다. 그리고 검색하는 title과 day를 가지고 메소드를 실행한다.
이후, nowfind가 1 이라면, 그 값을 0으로 바꾸고 새롭게 검색 취소 버튼을 만들어준다. nowfind는 세마포어의 원리와 비슷하게 구현하는데
만약, 한번 검색을 했다면, nowfind는 0이 되므로 더이상 생기지 않도록 만들기 위함이다.*/
function onEnter(){
  if(event.keyCode == 13){
      var data = document.getElementById('Find_Data');
      var day = document.getElementById('Find_Day');

      findTitle(day.value, data.value);
      if(nowfind == 1){
        nowfind --;
        Find_Data.parentNode.innerHTML += "<button id = 'Cancel' class = 'findCancel' onclick = 'findCancel(this)'>검색취소</button>"
      }
  }
}

//제목을 이용해서 찾기
/*요일을 받아오는데, Day이면 전체검색, 그 외 요일은 각자 요일에 맞게 검색한다.
우선 for문을 이용하여 모든요일의 보여지는 일정값들의 display를 none으로 설정한다.
그리고, for문을 돌아가며 조건에 맞는 데이터를 찾아 보여지도록 설정한다.*/
function findTitle(day,findName){
  if(day == "Day"){
    for(var i = 0; i < A_index; i ++){
      document.getElementById('A_'+ i).style.display = 'none';
      if(A_Datalist[i].title.match(findName) == findName){
        document.getElementById('A_'+ i).style.display = 'block';
      }
    }
    for(var i = 0;i < B_index; i++){
      document.getElementById('B_'+ i).style.display = 'none';
      if(B_Datalist[i].title.match(findName) == findName){
        document.getElementById('B_'+ i).style.display = 'block';
      }
    }
    for(var i = 0; i < C_index; i++){
      document.getElementById('C_'+ i).style.display = 'none';
      if(C_Datalist[i].title.match(findName) == findName){
        document.getElementById('C_'+ i).style.display = 'block';
      }
    }
    for(var i = 0; i < D_index; i++){
      document.getElementById('D_'+ i).style.display = 'none';
      if(D_Datalist[i].title.match(findName) == findName){
        document.getElementById('D_'+ i).style.display = 'block';
      }
    }
    for(var i = 0; i < E_index; i++){
      document.getElementById('E_'+ i).style.display = 'none';
      if(E_Datalist[i].title.match(findName) == findName){
        document.getElementById('E_'+ i).style.display = 'block';
      }
    }
  }
  else if(day == "Mon"){
    for(var i = 0; i < A_index; i ++){
      document.getElementById('A_'+ i).style.display = 'none';
      if(A_Datalist[i].title.match(findName) == findName){
        document.getElementById('A_'+ i).style.display = 'block';
      }
    }
    for(var i = 0;i < B_index; i++){
      document.getElementById('B_'+ i).style.display = 'none';
    }
    for(var i = 0; i < C_index; i++){
      document.getElementById('C_'+ i).style.display = 'none';
    }
    for(var i = 0; i < D_index; i++){
      document.getElementById('D_'+ i).style.display = 'none';
    }
    for(var i = 0; i < E_index; i++){
      document.getElementById('E_'+ i).style.display = 'none';
    }
  }
  else if(day == "Tue"){
    for(var i = 0; i < A_index; i ++){
      document.getElementById('A_'+ i).style.display = 'none';
    }
    for(var i = 0;i < B_index; i++){
      document.getElementById('B_'+ i).style.display = 'none';
      if(B_Datalist[i].title.match(findName) == findName){
        document.getElementById('B_'+ i).style.display = 'block';
      }
    }
    for(var i = 0; i < C_index; i++){
      document.getElementById('C_'+ i).style.display = 'none';
    }
    for(var i = 0; i < D_index; i++){
      document.getElementById('D_'+ i).style.display = 'none';
    }
    for(var i = 0; i < E_index; i++){
      document.getElementById('E_'+ i).style.display = 'none';
    }
  }
  else if(day == "Wed"){
    for(var i = 0; i < A_index; i ++){
      document.getElementById('A_'+ i).style.display = 'none';
    }
    for(var i = 0;i < B_index; i++){
      document.getElementById('B_'+ i).style.display = 'none';
    }
    for(var i = 0; i < C_index; i++){
      document.getElementById('C_'+ i).style.display = 'none';
      if(C_Datalist[i].title.match(findName) == findName){
        document.getElementById('C_'+ i).style.display = 'block';
      }
    }
    for(var i = 0; i < D_index; i++){
      document.getElementById('D_'+ i).style.display = 'none';
    }
    for(var i = 0; i < E_index; i++){
      document.getElementById('E_'+ i).style.display = 'none';
    }
  }
  else if(day == "Thu"){
    for(var i = 0; i < A_index; i ++){
      document.getElementById('A_'+ i).style.display = 'none';
    }
    for(var i = 0;i < B_index; i++){
      document.getElementById('B_'+ i).style.display = 'none';
    }
    for(var i = 0; i < C_index; i++){
      document.getElementById('C_'+ i).style.display = 'none';
    }
    for(var i = 0; i < D_index; i++){
      document.getElementById('D_'+ i).style.display = 'none';
      if(D_Datalist[i].title.match(findName) == findName){
        document.getElementById('D_'+ i).style.display = 'block';
      }
    }
    for(var i = 0; i < E_index; i++){
      document.getElementById('E_'+ i).style.display = 'none';
    }
  }
  else if("Fri"){
    for(var i = 0; i < A_index; i ++){
      document.getElementById('A_'+ i).style.display = 'none';
    }
    for(var i = 0;i < B_index; i++){
      document.getElementById('B_'+ i).style.display = 'none';
    }
    for(var i = 0; i < C_index; i++){
      document.getElementById('C_'+ i).style.display = 'none';
    }
    for(var i = 0; i < D_index; i++){
      document.getElementById('D_'+ i).style.display = 'none';
    }
    for(var i = 0; i < E_index; i++){
      document.getElementById('E_'+ i).style.display = 'none';
      if(E_Datalist[i].title.match(findName) == findName){
        document.getElementById('E_'+ i).style.display = 'block';
      }
    }
  }
}

//검색 취소버튼을 누르면
/*검색취소 버튼을 누르면, 우선, 검색을 했던 모든 데이터들을 block으로 보여지도록 만들고
버튼을 지워주며 nowfind를 1 올려주어 검색취소버튼을 만들수 있도록한다.*/
function findCancel(but){
  for(var i = 0; i < A_index; i ++){
    document.getElementById('A_'+ i).style.display = 'block';
  }
  for(var i = 0;i < B_index; i++){
    document.getElementById('B_'+ i).style.display = 'block';
  }
  for(var i = 0; i < C_index; i++){
    document.getElementById('C_'+ i).style.display = 'block';
  }
  for(var i = 0; i < D_index; i++){
    document.getElementById('D_'+ i).style.display = 'block';
  }
  for(var i = 0; i < E_index; i++){
    document.getElementById('E_'+ i).style.display = 'block';
  }
  but.remove();
  nowfind ++;
}
//텀 프로젝트 완료 2018년 5월 5일 토요일 11시 29분

//과제에서 추가 구현
/*검색을 하고나서, 검색을 취소하는 버튼에 대한 구현을 추가하였습니다.
검색을 하고 돌아가는 기능이 TodoList에서는 필요할것 같아서 추가적으로 구현하였습니다.*/
