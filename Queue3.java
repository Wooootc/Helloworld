import java.util.*;

public class Queue3 implements Queue{
	int wl[]=new int[5];
	int close;
    Random generator =	 new Random();

//create a array of linkedlist which represent 5 waitting line
	LinkedList<Customer>[] waitline = (LinkedList[]) new LinkedList[5];
	 {waitline[0] = new LinkedList<Customer>();
	  waitline[1] = new LinkedList<Customer>();
	  waitline[2] = new LinkedList<Customer>();
	  waitline[3] = new LinkedList<Customer>();
	  waitline[4] = new LinkedList<Customer>();
	 }// Initialize each waitline

	public LinkedList<Server> serverlist=new LinkedList<Server> ();

   
	
	public void addServer(Server server){
    	serverlist.addLast(server);  	
    }
    	

    /**
     * Called by customer threads immediately before they
     * wait for service.
     */
    public synchronized void enterQueue(Customer customer){
    	int temp=wl[0];
    	int line=0;
    	//find the line with least waiting people return the line index
    	for(int i=0;i<5;i++){
    		if(temp>wl[i]){
    			temp=wl[i];
    			line=i;
    		}
    	}
    	//System.out.print(line);
    	//System.out.print(wl[line]);
    	//System.out.printf("\n");

    	waitline[line].addLast(customer);	
    	wl[line]++;
    	if(wl[line]==1){
    		this.notifyAll();
    		
    	}
    }

    /**
     * Called by servers to request another customer to serve.
     * If no customer is available for the specified server,
     * the calling thread should wait until one becomes available
     * or until the Queue is closed. If the queue has been closed,
     * this function returns null.
     */
    public synchronized Customer nextCustomer(Server server) throws InterruptedException{
    	Customer temp;
    	int m;
    	m=serverlist.indexOf(Thread.currentThread());//return which server is it for current threads
    	if(close==0){
    		while(wl[m]==0){//if this line has no people then keep waiting,and only the server has people get to be notified 
    			this.wait();
    			}
    		temp=waitline[m].getFirst();
    		waitline[m].removeFirst();
    		wl[m]--;
    		return temp;
    	}
    	else return null;
    }

    /**
     * Closes the queue. After being called, nextCustomer should
     * return null, and any servers still waiting for customers
     * should be notified.
     */
    public void close(){
    	close=1;
    	
    }
}