# Akka chat
Assignment #3 [part 2] for the course of Concurrent and Distributed Programming, Unibo.

Actor-based distributed chat

Implement a chat distributed with actors, with a dynamic set of participants - that is, a user can add himself and remove himself dynamically. Every message sent by a user must be viewed by all other users. Messages sent in the chat must be viewed by all participants in the same order.


Optional:
Support for a "critical section" mode: a participant can request to enter the critical section by entering a default command (eg: ": enter-cs"). When a participant enters the critical section, only his messages can be displayed, without interrupting them to those of other users. Only one participant at a time can be in a critical section. To exit the critical section you can expect another default command (eg: ": exit-cs"). A user can remain in critical section for a certain maximum time Tmax - after which the output is forced. During a critical section, messages sent by other participants must be rejected.
