# GlowDTF Server Code Documentation

## Network Handler/Receiver/State Structure

To allow adding different APIs in the future and provide clean separation of
concerns, there are three layers to how incoming events are handled: 

- Network Handlers: These are registered e.g. in the server code to handle incoming
  messages. They are responsible for the "transport" part of an API. They get
  passed an instance of an object implementing a Receiver interface such as
  AsyncStringReceiver or SyncGdtfReceiver. 
- Receivers: These implement a Receiver interface. During construction, they get
  passed some sort of state object they need to mutate. Once their receive
  method gets called, they will parse the message and dispatch to the approriate
  state methods for modification. 
- State: The state or state objects provide an internal API that the Receivers
  call into. Every state object is responsible for validating inputs to its own
  API. Failures are communicated to the calling Receivers with Result types and
  GlowErrors. 

Currently, there are two network handler packages:
- `rest`
- `websocket`

There are also two receiver packages: 
- `gdtf`
- `json`

If for example a new transport layer for WebSocket is to be included, its
network handlers should call into the existing JsonHandler in the json package.

If a different format should be sent over WebSocket, the network handlers get
passed a different receiver that also implements AsyncStringReceiver.  


## Possible Future SubscriptionHandler Design

// streams: List<GlowStream>
// TODO
//            stream0: PatchListStream extends GlowStream
//                subscriptions: List<StreamSubscription>
//                    StreamSubscription: StreamSubscribtion
//                        client: GlowClient / WebsocketCtx
//                        streamUpdateId: Int
//            stream1: GlowStream
//                subscriptions: List<StreamSubscription>
//                    StreamSubscription: StreamSubscribtion
//                        client: GlowClient / WebsocketCtx
//                        streamUpdateId: Int