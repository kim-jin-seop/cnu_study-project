var test1 int

func main(){
UseIf(7)
}

func UseIf(a int){
	var b int
	b = a
	if(b == 7){
		UseFor()
	}
}

func UseFor(){
	var a int
	a = 3
	for(a < 10){
	a = 10
	}
}
