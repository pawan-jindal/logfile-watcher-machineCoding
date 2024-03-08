# README #
### What is this repository for? ###
* Log watching solution for Machine coding round.
* It will focus on the design of a log-watcher similar to the tail -f command in UNIX.

### About Code ###
* Implemented using core-Java, Spring-boot, web-socket, and Rest-API.
* Rest-API: 
  * FileController.java class handle/manage logic.
  * /initialData(Get): will get called from browser when tab/url get called
    * URL: http://localhost:8080/index.html
  * /appendData(Post): to append data in log-file.
* Web-socket:
  * used for notify browser for changes happened in the file:
  * src/main/resources/static/index.html: implement frontend log/data view logic. 

### Requirements of log-file watcher application ###
Let’s first understand the requirements.

### Mandatory Requirements ###
This problem requires you to implement a log watching solution (similar to the tail -f command in UNIX). However, in this case, the log file is hosted on a remote machine (same machine as your server code). The log file is in append-only mode.
* A server side program to monitor the given log file and capable of streaming updates that happen in it. This will run on the same machine as the log file. You may implement the server in any programming language.
* A web based client (accessible via URL like http://localhost/log) that prints the updates in the file as and when they happen and NOT upon page refresh. The page should be loaded once and it should keep getting updated in real-time. The user sees the last 10 lines in the file when he lands on the page.

### Problem Constraints ###
* The server should push updates to the clients as we have to be as real time as possible.
* Be aware that the log file may be several GB, retrieve the last 10 lines only on initial load.
* The server should not retransmit the entire file every time. It should only send the updates.
* The server should be able to handle multiple clients at the same time.
* The web page should not stay in loading state post the first load and it should not reload thereafter as well.
* You may not use off-the-shelf external libraries or tools to read the file or provide tail-like functionalities.