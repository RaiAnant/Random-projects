#include<stdio.h>
#include<math.h>

int main(){

float r;

scanf("%f",&r);
const float s_angle = 0.0314;

float total_angle = 0;

float totalArea = 0;

while (total_angle< 3.14){
    totalArea = totalArea + 1/2*r*r*sin(s_angle);

    total_angle = total_angle + s_angle;
}

printf("%f",totalArea);

return 0;
}
