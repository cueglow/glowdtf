# Client-Server API

The APIs between client (webui) and server are divided
into two parts for future extensibility: 

- Transport (e.g. WebSocket, HTTP)
- Message Format (e.g. JSON API, GDTF)

# JSON API

The JSON API assumes a transport in which

- the client can open a connection to the server 
- string messages can be exchanged between server and client
- message ordering is guaranteed for each direction
- both client and server can close the connection

## Pub/Sub and Topics

The JSON API can be used to sync state between the server and the clients. The
state is divided into blocks called topics. Clients can subscribe to a topic.
They will then receive the initial state for this topic, followed by
differential updates to keep their state in sync with the server-side state. 

The currently defined topics are:

- `patch`

## Topic: `patch`

The underlying state of the `patch` topic is defined as two collections: One
contains all fixtures, the other one the associated fixture types. 

### Fixture

A fixture is an object with the following fields:

- `uuid`: UUID v4 encoded as string, uniquely identifies the fixture
- `fid`: Stands for Fixture ID, a 32-bit signed integer
- `name`: String
- `fixtureTypeId`: UUID v4 encoded as string, uniquely identifies the associated fixture type
- `dmxMode`: String, encoding the DMX mode in the fixture type
- `universe`: Integer from 0 to 32767, encoding an Art-Net Address
- `address`: Integer from 1 to 512, encoding a DMX (ANSI E1.11) address

Both `universe` and `address` can be set to `-1` to encode a missing value. Such
a fixture is unpatched and does not produce any DMX output. 

### Fixture Type

A fixture type is an object with the following fields:

- `fixtureTypeId`: UUIDv4 encoded as String, uniquely identifies the fixture type
- `manufacturer`: String
- `name`: String
- `modes`: Array of DMX Modes

A DMX mode is an object with the following fields: 

- `name`: String
- `channelCount`: non-negative integer, number of channels in the mode (summed
  over all breaks)
- `channelLayout`: An Array where each element represents a DMX Break. Each DMX
  Break is again an Array of Channel names (String) or empty channels (null). 

### `patch` Events

The events that are associated with the `patch` topic are:


- `subscribe` (sent by client)
- `patchInitialState` (sent by server)
- `unsubscribe` (sent by client)
- `addFixtures` (sent by client/server)
- `updateFixtures` (sent by client/server)
- `removeFixtures` (sent by client/server)
- `addFixtureTypes` (sent by server)
- `removeFixtureTypes` (sent by client/server)
- `error` (sent by server)

### Subscription Lifecycle 

The client sends a `subscribe` to the server and specifies the topic in the
`data` field: 


```json
{
    "event": "subscribe",
    "data": "patch"
}
```

If the server notes the client is already subscribed, the server should behave
as if the client had first sent an unsubscribe and then a subscribe. The server
should also log the event. 

The server answers to a subscribe with the initial state of the topic: 

```json
{
    "event": "patchInitialState",
    "data": {
        "fixtures": [
            {
                "uuid": "d8224c6e-b9ff-4416-be7a-969142e90460",
                "fid": 1,
                "name": "Lamp 1",
                "fixtureTypeId": "e43e6b9d-ba96-4f9b-a391-a68e283a85ad",
                "dmxMode": "Extended",
                "universe": 1,
                "address": 1
            },
            {
                "uuid": "2aaad63c-2298-44c6-bbba-356a44a92d5f",
                "fid": 2,
                "name": "Lamp 2",
                "fixtureTypeId": "2d379f85-1f1c-49a8-bbd8-dcda5e893ffc",
                "dmxMode": "8-ch",
                "universe": 1,
                "address": 35
            }
        ],
        "fixtureTypes": [
            {
                "fixtureTypeId": "e43e6b9d-ba96-4f9b-a391-a68e283a85ad",
                "manufacturer": "GLP",
                "name": "impression SpotOne",
                "modes": [
                    {
                      "name": "Standard",
                      "channelCount": 21,
                      "channelLayout": [["yoke_Pan", "head_Tilt", "head_Dimmer", "and so on ..."]]
                    },
                    {
                      "name": "Extended",
                      "channelCount": 34,
                      "channelLayout": [["yoke_Pan (1/2)", "yoke_Pan (2/2)", "head_Tilt (1/2)", "and so on ..."]]
                    }
                ]
            },
            {
                "fixtureTypeId": "2d379f85-1f1c-49a8-bbd8-dcda5e893ffc",
                "manufacturer": "JB Lighting",
                "name": "Sparx A7",
                "modes": [
                    {
                      "name": "8-ch",
                      "channelCount": 8,
                      "channelLayout": [["yoke_Pan", "head_Tilt", "head_Dimmer", "and so on ..."]]
                    },
                    {
                      "name": "24-ch",
                      "channelCount": 24,
                      "channelLayout": [["yoke_Pan (1/2)", "yoke_Pan (2/2)", "head_Tilt (1/2)", "and so on ..."]]
                    }
                ]
            }
        ]
    }
}
```

The client must clear his existing patch data and replace it with the
received initial state. 

### Fixture Updates

The server sends incremental updates to keep all subscribed clients in sync with
the patch data. For example if a third fixture is added, the server would send:

```json
{
    "event": "addFixtures",
    "data": [
        {
            "uuid": "202d6959-f975-4a1c-9b8c-3f62c6d3b7c4",
            "fid": 3,
            "name": "Lamp 3",
            "fixtureTypeId": "e43e6b9d-ba96-4f9b-a391-a68e283a85ad",
            "dmxMode": "Extended",
            "universe": 1,
            "address": 69
        }
    ]
}
```

`addFixtures` must contain all fields of the fixture as shown here. The
exception are `universe` and `address` which are null or undefined/missing when
they are not set. 

If the fixture collection of the client already contains a fixture with the same
`uuid` or the `fixtureTypeId` is unknown, the client should re-subscribe to the
topic, as there is likely a synchronization issue. 

If an existing fixture is updated, the server sends an `updateFixtures`. For
example, if Lamp 3 is renamed to Lamp 4 and moved to another universe: 

```json
{
    "event": "updateFixtures",
    "data": [
        {
            "uuid": "202d6959-f975-4a1c-9b8c-3f62c6d3b7c4",
            "fid": 4,
            "name": "Lamp 4",
            "universe": 2,
            "address": 1
        }
    ]
}
```

The fields `fixtureTypeId` and `dmxMode` are never contained in an
`updateFixtures` because they are immutable at the moment. The field `uuid` must
be contained. The other fields can be null or undefined/absent, meaning no
change to this field. 

Generally, the JSON format treats fields being null or absent/undefined exactly
the same. This is because the server-side JVM cannot easily differentiate
between undefined and null. 

When universe and address don't change their value they should be null or undefined.
If they change to being null/unpatched, their value must be `-1`. 

If the `uuid` of the updated fixture(s) is not present client-side, the client
should re-subscribe. 

If a fixture is removed, the server sends a `removeFixtures`. For example, if 
Lamp 4 is removed:

```json
{
    "event": "removeFixtures",
    "data": [
        "202d6959-f975-4a1c-9b8c-3f62c6d3b7c4"
    ]
}
```

If the removed fixture are not present client-side, the client should re-subscribe. 

### Fixture Type Updates

When another fixture type is added to the patch (e.g. via the HTTP Upload API),
the server sends an `addFixtureTypes`: 

```json
{
    "event": "addFixtureTypes",
    "data": [
        {
            "fixtureTypeId": "f51707ea-f041-4e6f-905c-dee594825e37",
            "manufacturer": "Another manufacturer",
            "name": "Another Fixture Type Name",
            "modes": [
                {
                    "name": "mode1",
                    "channelCount": 1,
                    "channelLayout": [["head_Dimmer"]]
                }
            ]
        }
    ]
}
```

If a fixture type with the same `fixtureTypeId` already exists client-side, the
client should re-subscribe. 

Fixture types are currently immutable, so there is no `updateFixtureTypes`. 

If a fixture type is deleted, the server sends a `removeFixtureTypes`:

```json
{
    "event": "removeFixtureTypes",
    "data": [
        "f51707ea-f041-4e6f-905c-dee594825e37"
    ]
}
```

Again, if the client does not know about this fixtureTypeId, he should
re-subscribe. 

### Unsubscribe

To unsubscribe from a topic, the client sends an `unsubscribe` and specifies the
topic in the `data` field:

```json
{
    "event": "unsubscribe",
    "data": "patch"
}
```

If the server receives an unsubscribe from a client that is not subscribed to
the specified topic, it should log the incident. 

### Client-Driven Events

Clients can request changes to the state, even without subscribing to
the corresponding topic.

If the client wants to add some fixtures, he sends an `addFixtures`. This is
almost the same message format as the server sends for topic updates, but the
client includes a `messageId`:

```json
{
    "event": "addFixtures",
    "data": [
        {
            "uuid": "91faaa61-624b-477a-a6c2-de00c717b3e6",
            "fid": 10, 
            "name": "Lamp 10",
            "fixtureTypeId": "e43e6b9d-ba96-4f9b-a391-a68e283a85ad",
            "dmxMode": "Extended",
            "universe": 1,
            "address": 69
        }
    ],
    "messageId": 872
}
```

The purpose of the `messageId` is to associate request and response with each
other. Every time the client sends a message to the server that requires a
different `messageId`, the client must increase it by 1 compared to the last
value. The value must wrap around to a minimum value once a maximum value is
reached. It is recommended to start at 0 with a maximum value of 65535 and a
minimum value of 0. This corresponds to a 16-bit unsigned integer. 

A confirmation that the action was successful is not sent, but if the client is
subscribed to the right topic, he will receive the corresponding update. 

The `uuid` field must be randomly generated by the client. If the UUID already
exists server-side, the incident will be logged and the server will respond with
an error: 


```json
{
    "event": "error",
    "data": {
        "name": "$nameOfTheError",
        "description": "$description"
    },
    "messageId": 872 // the message with this Id caused the error 
}
```

Other issues that occur server-side may also be reported to the client with such
an error message. 

The client may also send `updateFixtures`, `removeFixtures` or
`removeFixtureType` to trigger the corresponding events. In all cases the
messages are the same as received from the server (see above), but the client
adds a `messageId`. 

## Topic: `rigState`

The rig state is the state of all fixtures (the "rig"). 

### `rigState` Events

- `subscribe`/`unsubscribe`/`error`
- `rigState` (sent by server)
- `setChannel`

### Rig State Lifecycle


Client sends:
```json
{
    "event": "subscribe",
    "data": "rigState"
}
```

Server returns:
```json
{
    "event": "rigState",
    "data": { // map from fixture uuid to fixture state
        "1465f08b-4746-4c45-9f47-c9f3f3039bb7": { // first fixture
            "chValues": [
                0,
                255
            ],
            "chFDisabled": [
                null, // not disabled
                null,
                null,
                "Dimmer 1 must be 128-255" // reason for being disabled
            ]
        },
        "55394860-2e07-4633-b555-14e3699e92de": { // second fixture
            // ...
        }
    }
}
```

When the rig state changes, the server sends the whole `rigState` message again. 

When the client wants to update the value of a channel, it sends:
```json
{
    "event": "setChannel",
    "data": {
        "fixtureUuid": "1465f08b-4746-4c45-9f47-c9f3f3039bb7",
        "chInd": 0,
        "value": 255
    }
}
```

The server will update the rigSate and send out `rigState` if there are changes. 
If the update was invalid, the server discards the message with a warning log. 

To stop receiving updates, the client sends
```json
{
    "event": "unsubscribe",
    "data": "rigState"
}
```

## Simplified Ping API

To ensure the WebSocket connection does not time out, the client regularly sends
a ping message every minute:

```json
{
    "event": "ping"
}
```

## Shutdown

To shutdown the server process, the client can send:

```json
{
    "event": "shutdown"
}
```

## Old Ping API (NOT IMPLEMENTED)

To ensure a good user experience, the server and client require a reliable and
low latency connection and need to be able to react to incoming messages within
reasonable latency. The Ping mechanism uses an interaction similar to a
three-way handshake to monitor the response times of the other party. If the
response time exceeds a certain limit, the connection is closed and the
restarting procedures should take over. 

**Procedure**

After sending the last `pongAcknowledge`, the client waits for about `pingInterval`
before initializing another handshake with a ping:

```json
{
    "event": "ping",
    "messageId": 3292
}
```

The server answers with a pong:

```json
{
    "event": "pong",
    "messageId": 3292
}
```

The client responds to the pong with a pongAcknowledge: 

```json
{
    "event": "pongAcknowledge",
    "messageId": 3292
}
```

**Client Behavior**

If the server did not respond to the ping with a pong within `pingTimeout`, the
WebSocket connection must be closed. 

**Server Behavior**

If the client did not respond to the pong with a pongAcknowledge within
`pingTimeout`, the WebSocket connection must be closed. 

If the client sends another ping before sending a pongAcknowledge, the WebSocket connection must be closed. (This should
not happen because WebSocket runs over TCP which ensure message ordering -> even more of a reason to fail fast in this
case)

**Suggested Time Values**

`pingInterval` = 1 second

`pingTimeout` = 1 second

# WebSocket Transport

The GlowDTF WebSocket Transport uses WebSocket (RFC 6455). It
provides access to the JSON API by connecting to the URI `/webSocket`. Then, text
messages according to the JSON API are exchanged between client
and server. 

# HTTP API 

The HTTP API operates via simple REST endpoints and is used when the WebSocket
API is unsuitable or complicated (e.g. file upload). 

## GDTF Endpoint

The only current endpoint is

```
POST /api/fixturetype/
```

where a GDTF file according to GDTF 1.1 (DIN 15800:2020-07) must be sent in the
field `file`. 

If everything went well, the server responds with a status code 200 and a JSON
payload of the form: 

```json
{
    "event": "fixtureTypeAdded",
    "data": $fixtureTypeIdOfNewFixtureType
}
```

If the server encountered an error while processing the request, he will answer
with an appropriate HTTP error status code and a JSON `error` event (see JSON
API) as payload. 