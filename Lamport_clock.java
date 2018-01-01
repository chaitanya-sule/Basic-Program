import java.util.Scanner;
public class Lamport_clock {
    public static void main(String a[]){
        int event[]=new int[100];//no of event per process
        int countevt=0;//count of total event
        int admatrixevt[][]=new int[100][100];//adenecy matrix of event[e*e]
       int cost[]=new int[100];//cost of each event total 
       int next[]=new int[100];//storing those who are exception case for ex if any event is called by its next event
        System.out.println("enter the no. of process");
        Scanner sc=new Scanner(System.in);//intialize scanner
        int p=sc.nextInt();//total no of process
        for(int i=0;i<p;i++){
            System.out.println("enter the no of event for"+i+" process");
            event[i]=sc.nextInt();//input no. of event per process
            countevt+=event[i];//count total no. of event
        }
//the below for loop input adency matrix
        for(int i=0;i<countevt;i++){
            System.out.println("enter the  event for"+i+" event");
            for(int j=0;j<countevt;j++){
                admatrixevt[i][j]=sc.nextInt();
            }
        }
        //calculating cost matrix
        for(int i=0;i<countevt;i++){
           for(int j=0;j<countevt;j++){
               System.out.println("j="+j);
                
               if(i==j){//check if it has return to its own event in the matrix 
                   cost[i]+=1;//if it is then IR1 will be implemented ie clock delay will be assign
                   for(int k=i;k<countevt;k++){//this loop check whether any process event call it after it cost is claculated
                       if(admatrixevt[i][k]==1){
                           next[i]=+1;
                           cost[i]=999;//intialize the cost to 999 
                       }
                   }
                   break;
               }else if(i>j && admatrixevt[i][j]==1){//check which of the event have call this event
                   if(cost[i]<cost[j]){//if call then checking whether the particular event cost is max
                       cost[i]=cost[j];//if yes then IR2 is applied 
                   }
                   
               }
           }
            }
        //the below loop is for those event who have later call
        for(int k=0;k<countevt;k++){
            if(next[k]>0){//check if there next value is greater than 1 
                cost[k]=0;//intialize cost
                for(int i=k+1;k<countevt;k++){
                    if(admatrixevt[k][i]==1 && cost[k]<=cost[i]){//agian apply IR2
                        cost[k]=cost[i];
                        cost[k]++;//then apply IR1
                         
            }
        }
            }
          
        }
            //this below loop only work if any event cost is affected due to its previous event
        for(int i=0;i<countevt;i++){
            if(cost[i]>=999){
                cost[i]=0;//if affected intialize
                //again calculate its cost
           for(int j=0;j<countevt;j++){
               if(i==j){
                   cost[i]+=1;
                   break;
               }else if(i>j && admatrixevt[i][j]==1){
                   if(cost[i]<cost[j]){
                       cost[i]=cost[j];
                   }
                   
               }
           }
                 
            }
        }
        //print the cost of each process
        for(int i=0;i<countevt;i++){
            System.out.println("cost["+i+"]="+cost[i]);
        }
    }
}