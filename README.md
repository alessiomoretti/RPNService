# RPNService
Simple server to perform Reverse Polish Notation computation as a service.

### The task ###
The task was to create a simple TCP server that was able to compute out result from arbitrary RPN-formatted-strings. 
For RPN we are referring to the Reverse Polish Notation (for a complete reference look [here](https://en.wikipedia.org/wiki/Reverse_Polish_notation)).

By task's assumptions, non-negative integer values would be submitted and the only operators
would be the multiplication, sum, division and subtraction operators (respectively '*', '+', '/', '-'). The string
are ASCII encoded and the task is terminated by a linefeed character before being feed as an input to the
RPN computation service.

### The server structure
The server accepts on a given port (default port is `1234`, you can pass it to the main as a command line parameter using `--port=<port number`) incoming requests, starting a `ServerThread` for 
each of them. The thread will wait for a linefeed character to be sent and then send the result string to an object
called `RPNParser` that will explode the string into elements to be used for the RPN computation. Before submitting
the input string, the worker will use one the parser static helper utilities to check for anomalies in the string format.
 
### The computation ###
An object called `RPNCalculator` will use the same principle adopted by HP calculators to compute the result. When parsing 
the string, after checking for its format to be correct to represent a RPN-legal task, every numeric element
is pushed in a `Stack` (as given by the Java standard library). When an operator is retrieved from the input sequence, it will
retrieve (pop) from the stack the last two elements as operands and push the given result into the stack to be used by the next operator.
It the task was well formatted, the last element in the stack is the result.

### Example usage ###
Let we see the example usage of the server assuming it is running on a Linux distribution on port 1234. We can use telnet from multiple clients
to debug the concurrency and the RPN service:
```
$ telnet 127.0.0.1 1234
Trying 127.0.0.1...
Connected to localhost.
Escape character is '^]'.
5 1 2 + 4 * + 3 -
14
Connection closed by foreign host.
```
```
$ telnet 127.0.0.1 1234
Trying 127.0.0.1...
Connected to localhost.
Escape character is '^]'.
7 6 8 + 2 / 7
ERROR illegal task submitted
Connection closed by foreign host.
```
While on the server side debug info are sent to the system console:
```
Server running on port: 1234
5 1 2 + 4 * + 3 - => 14
7 6 8 + 2 / 7 => ERROR illegal task submitted
```

### Future improvements ###
The has been developed to be very modular, to improve each of its components in future releases of the software. A sketch of a roadmap 
for future improvements and general enhancements is provided (Pull requests and issues are welcom! Fork me!):
- using `CachedThreadPool` to reduce threads creation costs
- using more flexible format rules to adapt to a real world scenario
- porting the software to different platforms and to different programming languages and environments
- provide a unique standard API as a core to different implementations and scenarios
- use Java 1.8 streams


### Development ###
This software was developed on IntellijIDEA (by JetBrains) using Java 1.8 library, on macOS Sierra.

Latest update: May 25, 2017
