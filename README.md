# CNU_study-project_Portfolio
충남대학교 대학생활하며 진행한 프로젝트 모음

--------

# 1. TermProject_TravelDiary
## 객체지향설계 TermProject(2017-가을학기)
객체지향 설계 자유주제 Term Project

여행을 가서 일정을 기록하는 다이어리 안드로이드 어플리케이션

----------
# 2. SystemPrograming_Lab
## 시스템 프로그래밍 Lab(2017-가을학기)

## Shell Lab

Signal을 사용하여, 리눅스의 Shell을 구현.

카네기멜론 대학의 shlab

## Malloc Lab

Malloc Lab
for System Programing HomeWork

use next-fit & First-fit

--------

# 3. WebPrograming_Project
## 웹프로그래밍(2018년-봄학기)
## Self-introduction
- HTML, CSS  
- 자기소개 웹페이지

## TodoList_1
- HTML, CSS, java script
- 주간계획을 삽입, 수정, 삭제할 수 있는 TodoList

## TodoList_JSP
- JSP, CSS, java script, html
- TodoList를 jsp로 구현

## FootBall_Matching_System
- JSP, CSS, java script, html
- 충남대학교 풋살매칭 웹페이지 개발

--------

## 4. 2018년도  프로그래밍언어개론
### Recursion_Fibonacci_Ant
- 재귀함수 구현
- 피보나치
- 개미수열(11223 -> 122231 (입력값을 읽음, 1이 2개 2가 2개 3이 1개)

### Recognizing Tokens 
- 다양한 형태의 토큰을 입력받아 토큰의 요소 인식

### Cute14_Scanner
- Cute14문법에 따라 작성된 program을 입력받아, 모든 token을 인식하여 token과 lexeme을 모두 출력

### Node_Max_Sum
- 문자열을 Node로 return하는 JAR파일 활용
- 노드의 종류를 List와 Int만 있다고 가정을 하고, 최대값과 총합을 구하는 메소드 작성

### Cute18_Parser
- Cute18 문법에 따라 작성된 program을 입력받아, 프로그램의 syntax tree를 생성하는 parsing을 수행
- syntaxtree를 root로부터 pre-order traverse로 하여 원래 입력된 프로그램과 구조가 동일한 프로그램을 출력

### Cute18_Parser2
- Cute18 문법 파싱 추가

### Cute18_built_in_Function
- Cute18의 내장되어있는 함수를 구현
- ex) List연산 : car,cdr 등

### Cute18_interprete_1,2,3
- Cute18문법에 따른 프로그램을 입력하면 결과를 출력하는 인터프리터 구현
- 1 : Interpretation환경 구현(Read-Eval-Print Loop)
- 2 : 변수 바인딩처리
- 3 : 함수의 바인딩처리


## 주요 프로젝트
#### Cute18_interprete_1,2,3
--------------------------------------

# 5. TermProject_StoreSystem

## 모바일 프로그래밍 및 설계 TermProject(2018-가을학기)
판매업체와 기업의 재고를 관리하는데 도움을 주는 어플리케이션

### 제품 소개
판매업체와 판매업체에 물품을 공급하는 물품 공급 기업의 재고관리에 도움을 주는 어플리케이션이다.  
판매업체는 판매 제품의 재고를 효율적으로 관리가 가능하도록 어플리케이션으로 판매하는 물품에 대한 정보를 보여준다.  
판매업체는 재고가 일정수준 떨어지게 되면 자동으로 발주할 수 있는 기능이 있어서, 재고의 관리의 효율성을 높일 수 있다.  
### 기능 소개
#### 기업
- 판매업체에게 판매할 물품을 등록, 및 수정  
- 판매업체가 요청한 발주에 대한 처리기능  

#### 판매업체
- 기업에서 등록한 물품을 추가하여 판매 관리 대상으로 등록
- 기업에게 발주 요청 가능
- 자동으로 발주할 수 있도록 설정 가능
- 판매업체가 물품을 판매하면, 포스에서 자동으로 물품량 

## 6. 2018년도 가을학기 컴파일러개론
### Hoo_Compiler
- Hoo 파일을 입력받아, C코드를 출력하는 프로그램
~~~~
/*Hoo코드 예시*/
[abc]: print    // abc 를 출력 
[abc]: ignore   // 아무 것도 안 출력 
[abc]: (3) print    // abcabcabc 를 출력 
[]:print    // 빈 줄 출력 (newline) 
 
[abc]: 
[def]: 
[ghi]: print    // abcdefghi 를 출력 
 
[abcb][e/b]:print   // aece를 출력 (모든 b대신 e로 바꿈) 
[abcb][U]: print    // ABCB를 출력 (모두 대문자) 
[aBCB][L]: print   // abcb를 출력 (모두 소문자) 
 
[abc]: 
[def][e/f]:
[ghi][h/i]: (2) print  // abcdeeghhabcdeeghh를 출력 
~~~~
- Hoo 파일은 문법상의 오류가 없는것으로 가정하고 구현
###  MiniGoPrintListener
 - 'ANTLR'를 활용해 주어진 문법에 대하여 go파일을 pretty print하는 프로그램
 - pretty print 조건
 1. 블록이나 nesting 되어 들어갈 때는 4칸 들여쓰되 ‘.’을 찍음 
 1. If 등의 특수 절이나 함수 시작은 괄호를 함수 옆에 표시한다. 
 1. 2진 연산자와 피연산 사이에는 빈칸을 1칸 둔다. Ex) x+y -> x + y 
 1. 전위 연산자와 피연산자 사이에는 빈칸을 두지 않는다. Ex) ++x  
 1. 일반 괄호는 expression에 붙여 적는다. Ex) (x + y) 

### First_Follow
- first, follow 및 Recursive Descent Parser 구현
- 구문분석방법인 Top-Down방식에서 활용되는 First와 Follow 구현
- Recursive Descent Parser 구현

### UcodeGen_MiniGo
- MiniGo.g4파일에 대한 문법을 바탕으로 Ucode를 생성하는 Compiler 제작
- Go파일을 입력받고, Antlr가 파싱을하여 파싱 결과를 ucode로 변경해주는 Compiler
- 처음으로 만들어본 컴파
### UcodeGen_MiniGo2
- print 문법 추가
## 주요 프로젝트
#### UcodeGen_MiniGo2

------------------------------------------------------------------------

#  7.Algorithm Study
- 충남대학교 컴퓨터공학과 김진섭
- 2018년도 가을학기 알고리즘
---------------------------------------------------------------------  
# SORT 알고리즘
## Insert_Bubble_select_Sort_JAVA  
[알고리즘] 선택정렬, 버블정렬, 삽입정렬 구현  
- 시간복잡도 : O(n^2)
- 삽입정렬 : 모두 정렬되어있는 데이터면 O(n)

## Merge_Quick_Sort_JAVA
[알고리즘] 퀵정렬, 합병정렬 구현  
- 시간복잡도 : O(nlogn)
- Quick Sort : 정렬되어있는 경우 O(n^2)

## Heap_Counting_Sort_JAVA
[알고리즘] Heap Sort, Count Sort
- Heap Sort : O(nlogn)
- Count Sort : O(n) (단, n은 데이터의 값의 크기)
---------------------------------------------------------------------
# Tree 알고리즘  
## [AL]BST
[알고리즘] Binary Search Tree
- 데이터를 삽입할 때 왼쪽으로 작은값 오른쪽으로 큰 값으로 삽입  
- tree의 높이가 낮다면 O(logn)만에 데이터 찾기 가능

## Red_Black_Tree_JAVA  
[알고리즘] Red Black Tree
- BST인데 균형을 맞추기 위하여 노력을 한 Tree
---------------------------------------------------------------------
# Hash table 알고리즘
## Hash_Table_JAVA
[알고리즘] Hash Table
- Linear Hash Table
- Quadratic Hash Table
- Double Hash Table
---------------------------------------------------------------------
# 상호 배타적 집합
## Union_Find_JAVA
[알고리즘] Union Find
- Union : 집합을 결합
- Find : 집합에서 값을 찾음
---------------------------------------------------------------------
# 동적 프로그래밍
## LCS_JAVA
[알고리즘] Longest Common Subsequence

input File
-> data1 Length\ndata1\ndata2 Length\ndata2

output File
-> LCS

알고리즘 과제 수행(동적 프로그래밍)
-	과제 목표 : LCS
LCS는 최장 공통 부분 순서를 의미하며 두 문자열 중 공통 부분이 가장 긴 문자열을 뽑아내는 것이다. 이 때 문자열이 연속적일 필요는 없다.

-	해결 방법 : LCS(동적 프로그래밍)
LCS를 재귀적으로 생각하여 보면, X를 x1x2x3x4…로 이루어진 String이고(xa는 char) Y를 y1y2y3…라 한다고 했을 때, xi와 yj에 대하여 생각해보자. 우선 xi와 yj에서의 최장 길이에 대한 데이터 정보가 담긴 값으로 cij가 있다고 하자. 우리가 유의해 살펴보며 찾아야하는 값은 cij이다.
만약, I 또는 j가 0이있는 경우에는 두 문자열 중 하나가 문자가 아무것도 없는 경우이므로 0을 넣어준다.
만약 xi와 yj가 같은 경우라면 cij는 c(i-1)(j-1)에서 1을 더해준 값이 된다.
만약 두 값이 서로 다른 경우라면, c(i-1)j나 ci(j-1)중에서 큰 값이 들어가게 될 것이다.
위 방법대로 재귀적으로 사용을 하게 되면, 심한 중복 호출이 발생한다. 따라서, 이를 해결하기 위해서는 구해준 cij에 대한 정보를 0에서부터 차례대로 삽입하며 테이블을 형성해야한다.
---------------------------------------------------------------------
# 그래프 알고리즘
## DFS_BFS_JAVA

[알고리즘] 깊이우선탐색과 너비우선탐색 알고리즘 구현
- DFS  
깊이우선 탐색 알고리즘  
- BFS  
너비우선 탐색 알고리즘  

## Prim_Kruskal_JAVA
[알고리즘] 프림과 크루스칼 

## Dijkstra_Bellman-Ford_Java 

[알고리즘] 다익스트라와 벨만포드 알고리즘 구현
- 다익스트라
최단 경로를 찾아내는 알고리즘
음의 간선이 존재하는 경우 다익스트라가 원활히 수행이 안됨

- 벨만포드
최단 경로를 찾아내는 알고리즘
음의 사이클이 존재하는 경우 벨만포드가 원활히 수행이 안됨


