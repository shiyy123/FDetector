// sum:1+2+3+4+5+6+7+8+9+10=55
// function set:(1)
#include<stdio.h>
int main(){
    int i;
    int answer = 0;
    int m = 10;
    for(i = 1; i <= m; i++){
        answer += i;
    }
    printf("%d",answer);
    return 0;
}
