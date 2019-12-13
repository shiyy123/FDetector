// sum:1+2+3+4+5+6+7+8+9+10=55
// function set:(2)
#include <stdio.h>
int sum(int n){
    if (n < 1)
        return 0;
    return n + sum(n - 1);
}

int main(void){
    printf("%d", sum(10));
    return 0;
}
