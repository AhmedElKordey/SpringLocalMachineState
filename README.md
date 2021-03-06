This is demo app for wrokflow interview 

I follow in this solution pattern from below URL as i not use state machine before ; but in another hand i like this pattern =D 
	- https://dzone.com/articles/a-simple-state-machine-for-spring-boot-projects
But it can handeled through diff approachs like 
	- KAFKA
	- Spring events
	- Spring state machine


to run this code 
	1- java -jar workflowDemo.jar
	2- Go to http://localhost:8070/swagger-ui.html#/employee-controller
	3- u will find 3 end points 
		3.1 - /add           :  Add employee basic data and return auto generated id .
		3.2 - /changeStatus  : to update status and convert employee from state to another 
		3.3 - /allStatus     : return all employee ids and it current status 
