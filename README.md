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
- Injecting the clock as a dependency to inject fixed clock while testing, to be able to move the time one unit as a time as needed
- The job containers are sorted according to the nearest due date, but can easily be modifed by changing ```long priorityScore()```
- Seperating the job from the container, allows for the same job to be scheduled with different frequencies and job names
- Scheduler has 2 data structures, a sorted set of jobs for rescheduling and a hash map which maps identifier to JobContainer to do the stop, resume, remove commands
- Frequency is the time between the end of previous run and the start of the current run

## Future improvements
- Use the expected time of run to optimize scheduler or to kill jobs which are running for too long
- Add more tests and have better error handling

## How to start
1. Run Main.java
2. Interact using standard IO

## Commands

### Start
Starts a job for rescheduling  
Syntax: ```start [class] [identifier] [expected time] [frequecy]```  
Example: ```start jobs.PrintJob printJob 10S 10S```

### Stop, Resume, Remove
Stops a jobs temporarily, resumes a stopped job, removes a job permenantly from the scheduler  
Syntax: ```[command] [identifier]```  
Example: ```stop printJob``` ```resume printJob``` ```remove printJob```  

### Exit
Terminate the scheduler  
Syntax: ```exit```  

