
/*사용할 바둑돌을 가져오기 위해 사용한 변수 !! 실행전에 무조건 세팅*/
int OmokNum = 0; //오목돌을 몇번째 두는가에 대한 정보
int useX = 104;//X초기값
int useY = -149; //Y초기값
int useDimensionX = 4; //X의 차원
int useDimensionY = 2; //Y의 차원
int use_length = 47; //사용하는 바둑집의 길이

/*착수점을 찾기 위한 변수(바둑판의 변수) !!실행전에 무조건 세팅해주어야함*/
int omok_demention = 9; //바둑판 크기 a*a이면 omok_demention = a
int omok_length = 25; //바둑판 착점의 한칸의 크기
int firstX = 131; //바둑판 최우측하단의 X좌표
int firstY = -95; //바둑판 최우측하단의 Y좌표

/*그 외 기타 변수*/
unsigned long times;
int inByte = 0;

/*사용하는 메소드*/
unsigned char get_openmv_data(); //openmv에서 데이터 받아옴
void getStorn(); //사용할 바둑돌을 가져오는 메소드
void PutOmok(int x,int y); //바둑판의 (x,y)좌표에 찾아가는 메소드

/*움직임의 동작이 수행되기를 기다리는 메소드*/
void wait_for_finish_moving()
{
  inByte=0;//clear the buffer
  while(inByte!='@'){
     if (Serial2.available() > 0) {
        inByte = Serial2.read();
     }
  }
}

/*초기 설정*/
void setup() {
  pinMode(5,INPUT);//button
  pinMode(A3,OUTPUT);//orange led
  digitalWrite(A3,LOW);
  Serial.begin(115200);//usb or xbee
  Serial1.begin(115200);//openmv
  Serial2.begin(115200);//uarm

  Serial2.write("G0 X200 Y0 Z160 F10000\n");
  while(digitalRead(5)==HIGH);
  digitalWrite(A3,HIGH);
  
  Serial.write("V2 START!\n");
  Serial2.write("M2400 S0\n");
  delay(4000);
  Serial2.write("M2400 S0\n");
  Serial2.write("M2122 V1\n");
  times = millis();
}

void loop() {
  /*test*/
  for(int i = 0; i < omok_demention; i++){
    for(int j=0; j < omok_demention; j++){
      PutOmok(j,i);
    }
  }
  
}

void getStorn(){
  int len = useDimensionX*useDimensionY;
  int YIndex = 0;
  int XIndex = OmokNum%len;
  while(XIndex >= useDimensionX){ 
    YIndex++;
    XIndex = XIndex - useDimensionX;
  }
  int x = useX + (XIndex*use_length);
  int y = useY - (YIndex*use_length);
  String commands="G0 X"; 
  commands.concat(x);
  commands+=" Y";
  commands.concat(y);
  commands+=" Z100 F10000\n";
  Serial2.print(commands);
  Serial.print(commands);
  wait_for_finish_moving();
  Serial2.write("G0 Z-30 F10000\n");
  wait_for_finish_moving();
  Serial2.write("M2231 V1\n");
  delay(800);
  Serial2.write("G0 Z100 F10000\n");
  wait_for_finish_moving();
  OmokNum++;
}

void PutOmok(int x,int y){
  getStorn();
  int Xposition = firstX + (omok_demention-x-1)*omok_length;
  int Yposition = firstY + (omok_demention-y-1)*omok_length;

  String commands="G0 X"; 
  commands.concat(Xposition);
  commands+=" Y";
  commands.concat(Yposition);
  commands+=" Z100 F10000\n";
  Serial2.print(commands);
  Serial.print(commands);
  wait_for_finish_moving();
  Serial2.write("G0 Z3` F10000\n");
  wait_for_finish_moving();
  Serial2.write("M2231 V0\n");
  Serial2.write("G0 Z100 F10000\n");
  wait_for_finish_moving();
}
