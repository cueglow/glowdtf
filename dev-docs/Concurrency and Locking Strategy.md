# Concurrency and Locking Strategy

The used server framework, Javalin, builds upon Jetty where each REST handler
and each WebSocket connection is executed concurrently in a thread pool, so the
structures that the handlers access need to be thread-safe. 

We ensure this by locking on `StateProvider` with `StateProvider.lock` in all
access methods. While holding this lock, the methods notify subscribers by
putting a `GlowMessage` into the queue provided by `OutEventHandler`. These
outgoing events are picked up on another thread and passed to the
`JsonSubscriptionHandler`. This Handler itself is locked during all access
methods, as it can be accessed  concurrently by either the
OutEventHandler-Thread or the Jetty Servlets. 

Some concurrent Tests are provided to test proper locking, but it must be noted
that the performance cost and cost of developing these concurrent tests is high
compared to their utility. 

## Locking Order

To reduce the likelihood of deadlock, if a thread at any time needs to hold more
than lock, it should acquire them in the given order:

1. JsonSubscriptionHandler.lock
2. StateProvider.lock