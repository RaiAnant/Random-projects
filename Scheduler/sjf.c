#include<stdio.h>
#include<fcntl.h>
#include<string.h>
#include<stdlib.h>

struct process{
     char name[10];
     float BT;
     float LEFT_BT;
     float REL_AT;
     float AT;
     float IO_FREQ;
     float IO_TIME;
     float PRIORITY;
     float IO_COMPLETION_TIME;
     int COMPLETED_IO_CYCLES;
     float CT;
     float WT;
     float TAT;
     struct process *next;
     struct process *prev;
};

typedef struct process PROCESS;


void insert_arr_q(PROCESS **start, PROCESS **end, PROCESS *process){
     if(*start==NULL&&*end==NULL){
          *end=*start=process;
          process->prev=NULL;
          process->AT = process->REL_AT;
     }else{
          (*end)->next=process;
          process->prev = (*end);
          *end = process;
          process->AT= process->prev->AT+process->REL_AT;
     }
     process->next = NULL;
     process->LEFT_BT = process->BT;
     process->COMPLETED_IO_CYCLES=0;
}

void insert_ready_q(PROCESS **start, PROCESS **end, PROCESS *process){
     if(*start==NULL&&*end==NULL){
          *end=*start=process;
          process->prev=NULL;
          process->next = NULL;
     }else{
          PROCESS *temp = *start;
          if(temp->LEFT_BT>process->LEFT_BT){
               process->next = *start;
               process->prev = NULL;
               (*start)->prev = process;
               *start = process;
          }else{
               while(temp->next!=NULL){
                    if(temp->next->LEFT_BT>process->LEFT_BT){
                         break;
                    }else if(temp->next->LEFT_BT==process->LEFT_BT){
                         if(temp->next->PRIORITY>process->PRIORITY){
                              break;
                         }
                    }
                    temp = temp->next;
               }
               process->next = temp->next;
               process->prev = temp;
               temp->next = process;
               if(process->next!=NULL){
                    process->next->prev=process;
               }else{
                    (*end) = process;
               }
          }
     }
}


void insert_io_q(PROCESS **start, PROCESS **end, PROCESS *process){
     if(*start==NULL&&*end==NULL){
          *end=*start=process;
          process->prev=NULL;
          process->next =NULL;
     }else{
          PROCESS *temp = *start;
          if(temp->IO_COMPLETION_TIME>process->IO_COMPLETION_TIME){
               process->next = *start;
               process->prev = NULL;
               (*start)->prev = process;
               *start = process;
          }else{
               while(temp->next!=NULL){
                    if(temp->next->IO_COMPLETION_TIME>process->IO_COMPLETION_TIME){
                         break;
                    }
                    temp = temp->next;
               }
               process->next = temp->next;
               process->prev = temp;
               temp->next = process;
               if(process->next!=NULL){
                    process->next->prev=process;
               }else{
                    (*end) = process;
               }
          }
     }
}

void insert_comp_q(PROCESS **start,PROCESS **end,PROCESS *process){
     if(*start==NULL&&*end==NULL){
          *end=*start=process;
          process->prev=NULL;
     }else{
          (*end)->next=process;
          process->prev = (*end);
          *end = process;
     }
     process->next = NULL;
}

PROCESS *get_process(PROCESS **start,PROCESS **end){
     PROCESS *process;
     if(*start==NULL){
          return NULL;
     }else if(*start==*end){
          process = *start;
          *start = *end = NULL;
     }else{
          process = *start;
          (*start)=(*start)->next;
          (*start)->prev = NULL;
     }
     process->next = NULL;
     return process;
}

void print_q(PROCESS *start){
     while(start!=NULL){
          printf("%s -> ",start->name);
          start=start->next;
     }
     printf("\n");
}
int no_process=0;
int main(){
     int fd1,n,index=0;
     fd1 = open("process.txt",O_CREAT|O_WRONLY);
     char str[1000] = "p1 0 20.0 1.5 5.0 2\np2 2 15.0 2.0 6.0 1\np3 6 37.0 1.8 3.5 4\np4 4 36.0 2.1 2.6 3",process_dat[30];
     n = write(fd1,str,strlen(str)+1);
     close(fd1);
     fd1 = open("process.txt",O_RDONLY);
     read(fd1,str,n);
     PROCESS *arrival_q_start=NULL,*arrival_q_end=NULL,*ready_q_start=NULL,*ready_q_end=NULL,*io_q_start=NULL,*io_q_end=NULL,*comp_q_start=NULL,*comp_q_end=NULL;
     for(int i=0;i<strlen(str);i++){
          process_dat[index]=str[i];
          index++;
          if(str[i]=='\n'||i==strlen(str)-1){
               index=0;
               PROCESS *process = (PROCESS*)malloc(sizeof(PROCESS));
               sscanf(process_dat,"%s %f %f %f %f %f",process->name,&process->REL_AT,&process->BT,&process->IO_FREQ,&process->IO_TIME,&process->PRIORITY);
               insert_arr_q(&arrival_q_start,&arrival_q_end,process);
               no_process++;
          }
     }

     PROCESS *temp = arrival_q_start;

     while(temp!=NULL){
          printf("%s %f %f %f %f %f\n",temp->name,temp->REL_AT,temp->BT,temp->IO_FREQ,temp->IO_TIME,temp->PRIORITY);
          temp = temp->next;
     }

     float curr_time=0,time_dur;
     int mode;
     PROCESS *cpu_process=get_process(&arrival_q_start,&arrival_q_end),*process;
     printf("about to start.....\n");
     int time_max=100;
     while(cpu_process!=NULL||arrival_q_start!=NULL||ready_q_start!=NULL||io_q_start!=NULL){
          time_dur=time_max;
          //printf("Scheduling........\n");
          if(cpu_process==NULL)
          printf("cpu sitting idel...");
          if(arrival_q_start!=NULL){
               time_dur = arrival_q_start->AT - curr_time;
               mode=1;
          }
          if(io_q_start!=NULL){
               if(time_dur>io_q_start->IO_COMPLETION_TIME - curr_time){
                    time_dur = io_q_start->IO_COMPLETION_TIME - curr_time;
                    mode = 2;
               }
          }if(cpu_process!=NULL){
               if(time_dur>cpu_process->LEFT_BT){
                    time_dur = cpu_process->LEFT_BT;
                    mode = 3;
               }
               if(time_dur>(cpu_process->COMPLETED_IO_CYCLES+1)*cpu_process->IO_FREQ-cpu_process->BT+cpu_process->LEFT_BT){
                    time_dur = (cpu_process->COMPLETED_IO_CYCLES+1)*cpu_process->IO_FREQ-cpu_process->BT+cpu_process->LEFT_BT;
                    mode = 4;
               }
               //printf("executing process %s ,case %d\n",cpu_process->name,mode);
               cpu_process->LEFT_BT = cpu_process->LEFT_BT - time_dur;
          }
          
          switch (mode){
               case 1:
                    process = get_process(&arrival_q_start,&arrival_q_end);
                    insert_ready_q(&ready_q_start,&ready_q_end,process);
                    //printf("inside case %d\n",mode);
                    break;
               case 2:
                    do{
                         process = get_process(&io_q_start,&io_q_end);

                         process->COMPLETED_IO_CYCLES++;
                         insert_ready_q(&ready_q_start,&ready_q_end,process);
                         if(io_q_start==NULL)
                         break;
                    }while(io_q_start->IO_COMPLETION_TIME==process->IO_COMPLETION_TIME);
                    //printf("inside case %d\n",mode);
                    break;
               case 3:
                    cpu_process->CT = curr_time+time_dur;
                    cpu_process->TAT =  cpu_process->CT - cpu_process->AT;
                    cpu_process->WT = cpu_process->TAT - cpu_process->BT;
                    insert_comp_q(&comp_q_start,&comp_q_end,cpu_process);
                    //printf("inside case %d\n",mode);
                    cpu_process = NULL; 
                    break;
               case 4:
                    cpu_process->IO_COMPLETION_TIME = curr_time+time_dur+cpu_process->IO_TIME;
                    insert_io_q(&io_q_start,&io_q_end,cpu_process);
                    cpu_process=NULL;
                    //printf("inside case %d\n",mode);
                    break;
          }

          curr_time = curr_time + time_dur;
          //printf("checking ready queue....\n");
          if(cpu_process!=NULL&&ready_q_start!=NULL){
               if(cpu_process->LEFT_BT>ready_q_start->LEFT_BT){
                    insert_ready_q(&ready_q_start,&ready_q_end,cpu_process);
                    cpu_process = get_process(&ready_q_start,&ready_q_end);
               }else if(cpu_process->LEFT_BT==ready_q_start->LEFT_BT&&cpu_process->PRIORITY>ready_q_start->PRIORITY){
                    insert_ready_q(&ready_q_start,&ready_q_start,cpu_process);
                    cpu_process = get_process(&ready_q_start,&ready_q_end);
               }
          }else if(cpu_process==NULL){
               cpu_process = get_process(&ready_q_start,&ready_q_end);
          }

          printf("currtime = %f\n",curr_time);
          printf("Arrival q : ");
          print_q(arrival_q_start);
          printf("Ready q : ");
          print_q(ready_q_start);
          printf("io q : ");
          print_q(io_q_start);
          printf("comp q : ");
          print_q(comp_q_start);
          
     }

     temp = comp_q_start;
     float total_wt,total_ct;
     while(temp!=NULL){
          printf("COMPLETION TIME:%f TURN AROUND TIME:%f WAITING TIME%f\n",temp->CT,temp->TAT,temp->WT);
          total_wt = temp->WT+total_wt;
          total_ct = temp->CT+total_ct;
          temp = temp->next;
     }

     printf("avg wait time: %f avg completion time: %f",total_wt/no_process,total_ct/no_process);
     
}