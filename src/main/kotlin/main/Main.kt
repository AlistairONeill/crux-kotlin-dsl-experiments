package main

import crux.api.CruxDocument
import crux.api.CruxK
import crux.api.tx.submitTx
import java.time.Duration


fun main() {
    CruxK.startNode{}.use { crux ->

        crux.submitTx {
            put(
                CruxDocument.build("hello") {
                    it.put("content", "Hello World")
                }
            )
        }.let {
            crux.awaitTx(it, Duration.ofSeconds(10))
        }

        crux.db().entity("hello")
            .get("content")
            .let(::println)
    }
}