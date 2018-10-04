# ACK

[Multiprocessing vs Multi threading](https://www.youtube.com/watch?v=wPgsqatFXBo) a  real world example to understand 

[Process vs Thread](https://www.callicoder.com/java-multithreading-thread-and-runnable-tutorial/)

This link contains the basic idea on [ThreadPoolExecutor](https://www.callicoder.com/java-executor-service-and-thread-pool-tutorial/)

To know detail on [WeakReference](https://medium.com/google-developer-experts/finally-understanding-how-references-work-in-android-and-java-26a0d9c92f83)

![Multitasking](https://github.com/anjandebnath/ThreadPoolExecutorAJ/blob/master/img/multitasking.png)

![MultiThreading](https://github.com/anjandebnath/ThreadPoolExecutorAJ/blob/master/img/multithreading.png)


### Executor
Executor is an interface used to decouple task submission from task execution.


### ExecutorService
ExecutorService is an interface which implements Executor interface and provides additional methods which allow you 

- to shutdown the service and 
- track progress and 
- cancel submitted tasks.

        ExecutorService mExecutorService = 
                        new ThreadPoolExecutor(NUMBER_OF_CORES,
                                               NUMBER_OF_CORES*2,
                                               KEEP_ALIVE_TIME,
                                               KEEP_ALIVE_TIME_UNIT,
                                               mTaskQueue,
                                               new BackgroundThreadFactory());


### ThreadPoolExecutor
ThreadPoolExecutor maintains task queue and thread pool. It picks tasks from queue and executes it using a free thread from the thread pools it maintains. 

Task producer submits tasks to task queue.

        ThreadPoolExecutor downloadThreadPool = 
                            new ThreadPoolExecutor(NUMBER_OF_CORES,
                                                    NUMBER_OF_CORES*2,
                                                    KEEP_ALIVE_TIME,
                                                    KEEP_ALIVE_TIME_UNIT,
                                                    mTaskQueue,
                                                    new BackgroundThreadFactory());
                                                    

### Diagram

![image](https://github.com/anjandebnath/ThreadPoolExecutorAJ/blob/master/img/TPE.PNG)


### Output
![image](https://github.com/anjandebnath/ThreadPoolExecutorAJ/blob/master/img/output.png)

- package (uploader)

Here it is a demo file upload task. Actually we don't upload any file but create an environment that will upload 4 file parallely.

Using ThreadPool Manager, RxJava, Callable and Future to make a complete package.
