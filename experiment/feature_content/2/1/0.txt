// mul:1*2*3*4*5*6*7*8*9*10=3628800
#include<stdio.h>
int main(){
    int i = 1;
    int answer = 1;
    int m = 10;
    while(i++ < m){
        answer *= i;
    }
    printf("%d", answer);
    return 0;
}