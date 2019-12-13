// sum:1+2+3+4+5+6+7+8+9+10=55
// function set:(5)
#include<stdio.h>
int sum(int a, int b){
    return a + b;
}
void print(int a){
    printf("%d", a);
}
int main(){
    int i, answer = 0;
    for(i = 1; i < 10 ; i+=2){
        answer += sum(i, i + 1);
    }
    print(answer);
    return 0;
}
