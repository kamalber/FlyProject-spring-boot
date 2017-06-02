package com.weberfly.service.threads;

class TwitterThreadHelper implements Runnable {
	   public Thread t;
	   private String threadName;
	   private int id;
	   boolean suspended = false;

	   TwitterThreadHelper(String name,int id) {
		   this.id=id;
	      threadName = name;
	      System.out.println("Creating " +  threadName +",id :"+this.id );
	   }
	   
	   public void run() {
	      System.out.println("Running " +  threadName );
	      try {
	         for(int i = 10; i > 0; i--) {
	            System.out.println("Thread: " + threadName + ", " + i);
	            // Let the thread sleep for a while.
	            Thread.sleep(300);
	            synchronized(this) {
	               while(suspended) {
	                  wait();
	               }
	            }
	         }
	      }catch (InterruptedException e) {
	         System.out.println("Thread " +  threadName + " interrupted.");
	      }
	      System.out.println("Thread " +  threadName + " exiting.");
	   }

	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
	   
	  public void suspend() {
	      suspended = true;
	   }
	   
	  public synchronized void resume() {
	      suspended = false;
	      notify();
	   }
	  public void stop(){
		  t.stop();
	  }
	}

	 class TestThread {

	   public static void main(String args[]) {

		  TwitterThreadHelper R1 = new TwitterThreadHelper( "Thread-1",1);
	      R1.start();

	      TwitterThreadHelper R2 = new TwitterThreadHelper( "Thread-2",2);
	      R2.start();

	      try {
	         Thread.sleep(1000);
	         R1.suspend();
	         System.out.println("Suspending First Thread");
	         Thread.sleep(1000);
	         R1.resume();
	         System.out.println("Resuming First Thread");
	         
	         R2.suspend();
	         System.out.println("Suspending thread Two");
	         Thread.sleep(1000);
	         R2.resume();
	         System.out.println("Resuming thread Two");
	      }catch (InterruptedException e) {
	         System.out.println("Main thread Interrupted");
	      }try {
	         System.out.println("Waiting for threads to finish.");
	         R1.t.join();
	         R2.t.join();
	      }catch (InterruptedException e) {
	         System.out.println("Main thread Interrupted");
	      }
	      System.out.println("Main thread exiting.");
	   }
	}
