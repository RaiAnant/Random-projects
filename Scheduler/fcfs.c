#include<fcntl.h>
#include<stdio.h>
#include<string.h>

struct process{
    char name[20];
    float BT;
    float AT;
    float IO_FREQ;
    float IO_TIME;
    float WT;
    float CT;
    float TAT;
    float priority;
    struct process *next;
    struct process *prev;
};


typedef struct process PROCESS;


void insert(PROCESS **start,PROCESS **end,PROCESS *pr){
    if(*start==NULL&&*end==NULL){
        *start = *end = pr;
        pr->prev = NULL;
    }else{
        (*end)->next =pr;
        pr->prev = *end;
        *end = pr;
    }
    pr->next = NULL;
}

int main(){
    int fd,n;
    char str[1000] ;
    strcpy(str,"P1 0 20.0 1.5 5.0 2\nP2 2 15.0 2.0 6.0 1\nP3 6 27.0 1.8 3.5 4\np4 4 36.0 2.1 2.6 3");
    fd = open("process_data3.txt",O_CREAT|O_WRONLY);
    n = write(fd,str,strlen(str));
    close(fd);
    fd = open("process_data3.txt",O_RDONLY);
    read(fd,str,n);
    char line[100][100];
    int index1=0,index2=0;
    PROCESS *start =NULL,*end=NULL;
    for(int i = 0 ; i <=strlen(str) ; i++){
        line[index1][index2] = str[i];
        index2++;
        if(str[i]=='\n'){
            index1++;
            index2=0;
        }
    }
    
    for(int i=0;i<index1+1;i++){
        PROCESS *p = (PROCESS*)malloc(sizeof(PROCESS)); 
        sscanf(line[i],"%s %f %f %f %f %f",p->name,&p->AT,&p->BT,&p->IO_FREQ,&p->IO_TIME,&p->priority);
        insert(&start,&end,p);
    }
    float sum = 0,total_io_time;
    PROCESS *p = start;
    while(p!=NULL){
        p->AT = p->AT + sum;
        total_io_time = ((int)(p->BT/p->IO_FREQ))*p->IO_TIME;
        if(p==start){
            p->WT = total_io_time;
            p->CT = p->BT + total_io_time;
            p->TAT = p->CT;
        }else{
            p->WT = p->prev->CT - p->AT + total_io_time;
            p->CT =  p->AT + p->WT + p->BT;
            p->TAT = p->CT - p->AT;
        }
        sum = p->AT;
        p=p->next;
    }
    p=start;
    while(p!=NULL){
        printf("%s %f %f %f %f\n",p->name,p->AT,p->WT,p->CT,p->TAT);
        p=p->next;
    }

}