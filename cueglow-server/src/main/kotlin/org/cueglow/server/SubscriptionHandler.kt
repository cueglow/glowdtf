package org.cueglow.server

import org.cueglow.server.objects.GlowClient

object SubscriptionHandler {
//    //                          StreamName   GlowStream
    val subscriptionList = hashMapOf<String,List<GlowStream>>()
//
//    //                      allenClients
//    val clients = listOf<GlowClient>()
//
//    fun newStreamUpdate(stream: String, msg: String) {
//        streamList.get(stream)?.forEach(sendMessage(msg))
//    }
//    fun newStreamUpdate(client: GlowClient) {
//        client.newUpdate()
//    }

    // streams: List<GlowStream>
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


}