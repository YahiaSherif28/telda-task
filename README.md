# telda-task  
Solution of this task https://github.com/teldabank/coding-challenges
## Description  
The scheduler has 4 Main components, Controller, Scheduler, JobContainer and Job Interface  
### Controller
The controller is responsible for interacting with the use from the standard IO and initializing the scheduler  
### Scheduler
Main working component, it runs on a Thread seperate from the Controller and it is responsible of keeping track of the currently running jobs  
and which jobs are due to be rescheduled.
### Job Container
Stores the meta data of the currently running job, including its frequency and its due time which is updated reqgularly by the Scheduler
### Job interface
Can be implemented by any class having a ```void execute()``` method, and this defines the Job which can be added to the scheduler.

## Some ideas
- Running the scheduler and controller on seperate threads allows to seperate the logic of the CLI from the scheduling logic.
- Injecting the clock as a dependecy to inject fixed clock while testing, to be able to move the time one unit as a time as needed
- The job containers are sorted according to the nearest due date, but can easily be modifed by changing ```long priorityScore()```
- Seperating the job from the container, allows for the same job to be scheduled with different frequencies and job names

## How to start
1. Run Main.java
2. Interact using standard IO

## 
